package com.es.core.dao;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JdbcCartPriceDao implements PriceDao {
    private static final String GET_PRICE_BY_ID_QUERY = "select price from phones where id = ?";

    private final Cart cart;

    @Autowired
    public JdbcCartPriceDao(Cart cart, JdbcTemplate jdbcTemplate) {
        this.cart = cart;
        this.jdbcTemplate = jdbcTemplate;
    }

    private final JdbcTemplate jdbcTemplate;

    @Override
    public BigDecimal getPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(CartItem cartItem : cart.getCartItems()) {
            BigDecimal tempPrice  = jdbcTemplate.queryForObject(GET_PRICE_BY_ID_QUERY, BigDecimal.class, cartItem.getPhoneId());
            tempPrice = tempPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalPrice = totalPrice.add(tempPrice);
        }
        return totalPrice;
    }
}
