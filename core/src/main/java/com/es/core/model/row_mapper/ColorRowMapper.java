package com.es.core.model.row_mapper;

import javafx.util.Pair;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColorRowMapper implements RowMapper<Long> {

    @Override
    public Long mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getLong("colorId");
    }
}
