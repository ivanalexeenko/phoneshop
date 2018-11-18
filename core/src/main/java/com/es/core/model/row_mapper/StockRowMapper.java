package com.es.core.model.row_mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockRowMapper implements RowMapper<Long> {
    private static final String PHONE_ID_COLUMN_LABEL = "phoneId";

    @Override
    public Long mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getLong(PHONE_ID_COLUMN_LABEL);
    }
}
