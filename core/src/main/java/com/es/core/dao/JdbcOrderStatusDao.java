package com.es.core.dao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcOrderStatusDao implements StatusDao {
    private static final String UPDATE_STATUS_QUERY = "update orders set status = ? where id = ?";
    private static final String UPDATE_STOCK_AND_RESERVED_QUERY = "update stocks set stock = ?, reserved = ? where phoneId = ?";

    private final JdbcTemplate jdbcTemplate;
    private final OrderDao orderDao;
    private final StockDao stockDao;

    @Autowired
    public JdbcOrderStatusDao(JdbcTemplate jdbcTemplate, OrderDao orderDao, StockDao stockDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderDao = orderDao;
        this.stockDao = stockDao;
    }

    @Override
    public void updateStatusWithId(OrderStatus status, String orderId) {
        jdbcTemplate.update(UPDATE_STATUS_QUERY, status.toString(), orderId);
    }

    @Override
    public void updateStockStatusBased(OrderStatus status, String orderId) {
        Optional<Order> optionalOrder = orderDao.getOrder(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOrderItems(orderDao.getOrderItems(orderId));
            order.getOrderItems().forEach(orderItem -> {
                Long phoneId = orderItem.getPhoneId();
                Stock stock = stockDao.getStock(phoneId);
                Long quantity = orderItem.getQuantity();
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
            });
        }
    }
}
