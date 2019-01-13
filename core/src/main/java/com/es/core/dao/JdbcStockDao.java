package com.es.core.dao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcStockDao implements StockDao {
    private static final String SELECT_STOCKS_WITH_PHONE_ID_QUERY = "select * from stocks where phoneId = ?";
    private static final String UPDATE_STOCK_WITH_ID = "update stocks set stock = ?, reserved = ? where phoneId = ?";
    private static final String UPDATE_STOCK_AND_RESERVED_QUERY = "update stocks set stock = ?, reserved = ? where phoneId = ?";

    private final JdbcTemplate jdbcTemplate;
    private final OrderDao orderDao;

    @Autowired
    public JdbcStockDao(JdbcTemplate jdbcTemplate, OrderDao orderDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderDao = orderDao;
    }

    @Override
    public Stock getStock(Long phoneId) {
        return jdbcTemplate.queryForObject(SELECT_STOCKS_WITH_PHONE_ID_QUERY, new Object[]{phoneId}, new BeanPropertyRowMapper<>(Stock.class));
    }

    @Override
    public void updateStock(Order order) {
        order.getOrderItems().forEach(orderItem -> {
            Long phoneId = orderItem.getPhoneId();
            Long quantity = orderItem.getQuantity();
            Stock stock = this.getStock(phoneId);
            Integer newStockValue = stock.getStock() - quantity.intValue();
            Integer newReservedValue = stock.getReserved() + quantity.intValue();
            jdbcTemplate.update(UPDATE_STOCK_WITH_ID, newStockValue, newReservedValue, phoneId);
        });
    }

    @Override
    public void updateStockStatusBased(OrderStatus status, String orderId) {
        Optional<Order> optionalOrder = orderDao.getOrder(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOrderItems(orderDao.getOrderItems(orderId));
            order.getOrderItems().forEach(orderItem -> {
                Long phoneId = orderItem.getPhoneId();
                Stock stock = this.getStock(phoneId);
                Long quantity = orderItem.getQuantity();
                updateStocks(order, stock, quantity, status, phoneId);
            });
        }
    }

    private void updateStocks(Order order, Stock stock, Long quantity, OrderStatus status, Long phoneId) {
        Integer newStockValue;
        Integer newReservedValue;
        if (order.getStatus().equals(OrderStatus.NEW) && status.equals(OrderStatus.DELIVERED)) {
            newStockValue = stock.getStock();
            newReservedValue = stock.getReserved() - quantity.intValue();
        } else if (order.getStatus().equals(OrderStatus.NEW) && status.equals(OrderStatus.REJECTED)) {
            newStockValue = stock.getStock() + quantity.intValue();
            newReservedValue = stock.getReserved() - quantity.intValue();
        } else {
            newStockValue = stock.getStock();
            newReservedValue = stock.getReserved();
        }
        jdbcTemplate.update(UPDATE_STOCK_AND_RESERVED_QUERY, newStockValue, newReservedValue, phoneId);
    }
}
