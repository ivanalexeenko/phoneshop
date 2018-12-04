package com.es.core.service;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public interface CartService {
    Cart getCart();

    Optional<CartItem> get(Long id);

    void addPhone(Long phoneId, Long quantity);

    void update(Map<Long, Long> items);

    void remove(Long phoneId);

    int getCartSize();
}
