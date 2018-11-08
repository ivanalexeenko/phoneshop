package com.es.core.model.row_mapper;

import javafx.util.Pair;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColorRowMapper implements RowMapper<Long> {
    private static final String COLOR_ID_COLUMN_LABEL = "colorId";

    @Override
    public Long mapRow(ResultSet resultSet, int index) throws SQLException {
        return resultSet.getLong(COLOR_ID_COLUMN_LABEL);
    }
}
