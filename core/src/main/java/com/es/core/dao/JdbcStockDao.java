package com.es.core.dao;

import com.es.core.model.phone.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class JdbcStockDao implements StockDao {
    private static final String SELECT_STOCKS_WITH_PHONE_ID_QUERY = "select * from stocks where phoneId = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcStockDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Stock getStock(Long phoneId) {
        return jdbcTemplate.queryForObject(SELECT_STOCKS_WITH_PHONE_ID_QUERY, new Object[]{phoneId}, new BeanPropertyRowMapper<>(Stock.class));
    }
}
