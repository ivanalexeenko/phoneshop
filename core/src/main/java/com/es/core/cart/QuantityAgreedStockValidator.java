package com.es.core.cart;

import com.es.core.model.phone.Stock;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.validation.ConstraintValidator;

import javax.validation.ConstraintValidatorContext;

public class QuantityAgreedStockValidator implements ConstraintValidator<QuantityAgreedStock, Object> {
    private static final String STOCK_WITH_ID_QUERY = "select * from stocks where phoneId = ?";

    private String fieldId;
    private String fieldQuantity;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void initialize(QuantityAgreedStock quantityAgreedStock) {
        this.fieldId = quantityAgreedStock.fieldId();
        this.fieldQuantity = quantityAgreedStock.fieldQuantity();
    }


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Long id = (Long) new BeanWrapperImpl(value).getPropertyValue(fieldId);
        Long quantity = (Long) new BeanWrapperImpl(value).getPropertyValue(fieldQuantity);
        Stock stock = jdbcTemplate.queryForObject(STOCK_WITH_ID_QUERY, new Object[]{id}, new BeanPropertyRowMapper<>(Stock.class));
        if (stock == null) {
            return false;
        }

        return quantity <= stock.getStock();
    }
}
