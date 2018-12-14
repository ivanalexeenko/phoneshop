package com.es.core.dao;

import com.es.core.model.phone.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcColorDao implements ColorDao {
    private static final String SELECT_COLORS_QUERY = "select * from colors";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcColorDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Color> getColors() {
        return jdbcTemplate.query(SELECT_COLORS_QUERY, new BeanPropertyRowMapper<Color>(Color.class));
    }
}
