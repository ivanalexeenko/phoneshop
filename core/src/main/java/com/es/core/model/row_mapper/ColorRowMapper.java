package com.es.core.model.row_mapper;

import javafx.util.Pair;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Phone2ColorMapper implements RowMapper<Pair<Long,Long>> {

    @Override
    public Pair<Long, Long> mapRow(ResultSet resultSet, int i) throws SQLException {
        Long phoneId = resultSet.getLong("phoneId");
        Long colorId = resultSet.getLong("colorId");
        return new Pair<>(phoneId,colorId);
    }
}
