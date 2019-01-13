package com.es.core.dao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcOrderDao implements OrderDao {
    private static final String ORDER_ITEMS_TABLE_NAME = "orderItems";
    private static final String ORDERS_TABLE_NAME = "orders";
    private static final String[] orderItemFieldNames = {"orderId", "phoneId", "quantity"};
    private static final String[] orderFieldNames = {"id", "subtotal", "deliveryPrice", "totalPrice", "firstName",
            "lastName", "deliveryAddress", "contactPhoneNo", "description", "status"};
    private static final String SELECT_ORDER_ITEMS_WITH_ID_QUERY = "select * from orderItems where orderId = ?";
    private static final String SELECT_ORDER_WITH_ID_QUERY = "select * from orders where id = ?";
    private static final String SELECT_ORDERS_QUERY = "select * from orders";
    private static final String UPDATE_STATUS_QUERY = "update orders set status = ? where id = ?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcOrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void placeOrder(Order order) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName(ORDERS_TABLE_NAME);
        Object[] values = invokeOrderGetters(order);
        Map<String, Object> parameters = new HashMap<>();
        putOrderParameters(order, parameters, values);
        simpleJdbcInsert.execute(parameters);
        placeOrderItems(order.getOrderItems());
    }

    @Override
    public List<OrderItem> getOrderItems(String orderId) {
        return jdbcTemplate.query(SELECT_ORDER_ITEMS_WITH_ID_QUERY, new Object[]{orderId},
                new BeanPropertyRowMapper<>(OrderItem.class));
    }

    @Override
    public Optional<Order> getOrder(String id) {
        Order order;
        try {
            order = jdbcTemplate.queryForObject(SELECT_ORDER_WITH_ID_QUERY, new Object[]{id}, new BeanPropertyRowMapper<>(Order.class));
            return Optional.of(order);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<Order> getOrders() {
        return jdbcTemplate.query(SELECT_ORDERS_QUERY, new Object[]{}, new BeanPropertyRowMapper<>(Order.class));
    }

    @Override
    public void updateStatusWithId(OrderStatus status, String orderId) {
        jdbcTemplate.update(UPDATE_STATUS_QUERY, status.toString(), orderId);
    }

    private Object[] invokeOrderGetters(Order order) {
        Object[] values = new Object[orderFieldNames.length];
        values[0] = order.getId();
        values[1] = order.getSubtotal();
        values[2] = order.getDeliveryPrice();
        values[3] = order.getTotalPrice();
        values[4] = order.getFirstName();
        values[5] = order.getLastName();
        values[6] = order.getDeliveryAddress();
        values[7] = order.getContactPhoneNo();
        values[8] = order.getDescription();
        values[9] = order.getStatus().toString();
        return values;
    }

    private void invokeOrderItemGetters(OrderItem orderItem, Object[] values) {
        values[0] = orderItem.getId();
        values[1] = orderItem.getPhoneId();
        values[2] = orderItem.getQuantity();
    }

    private void putOrderParameters(Order order, Map<String, Object> parameters, Object[] values) {
        for (int i = 0; i < values.length; i++) {
            parameters.put(orderFieldNames[i], values[i]);
        }
    }

    private void putOrderItemParameters(Map<String, Object> parameters, Object[] values) {
        for (int i = 0; i < values.length; i++) {
            parameters.put(orderItemFieldNames[i], values[i]);
        }
    }

    private void placeOrderItems(List<OrderItem> itemList) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName(ORDER_ITEMS_TABLE_NAME);
        Object[] values = new Object[orderItemFieldNames.length];
        Map<String, Object> parameters = new HashMap<>();
        itemList.forEach(orderItem -> {
            parameters.clear();
            invokeOrderItemGetters(orderItem, values);
            putOrderItemParameters(parameters, values);
            simpleJdbcInsert.execute(parameters);
        });
    }
}
