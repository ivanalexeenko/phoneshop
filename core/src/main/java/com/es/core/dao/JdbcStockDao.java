package com.es.core.dao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.AbstractMap;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcStockDao implements StockDao {
    private static final String SELECT_STOCKS_WITH_PHONE_ID_QUERY = "select * from stocks where phoneId = ?";
    private static final String UPDATE_STOCK_WITH_ID = "update stocks set stock = ?, reserved = ? where phoneId = ?";

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
    public void updateStocks(List<Stock> newStocks, List<Long> phoneIds) {
        final Integer index[] = new Integer[1];
        index[0] = 0;
        newStocks.forEach(newStock -> {
            Long phoneId = phoneIds.get(index[0]);
            jdbcTemplate.update(UPDATE_STOCK_WITH_ID, newStock.getStock(), newStock.getReserved(), phoneId);
            index[0]++;
        });
    }
}