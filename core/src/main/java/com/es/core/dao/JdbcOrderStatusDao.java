package com.es.core.dao;

import com.es.core.model.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcOrderStatusDao implements StatusDao {
    private static final String UPDATE_STATUS_QUERY = "update orders set status = ? where id = ?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcOrderStatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void updateStatusWithId(OrderStatus status, String orderId) {
        jdbcTemplate.update(UPDATE_STATUS_QUERY, status.toString(), orderId);
    }
}
