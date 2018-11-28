package com.es.core.service;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HttpSessionCartService implements CartService {

    private Cart cart;

    @Override
    public Cart getCart() {
        if (cart == null) {
            cart = new Cart();
        }
        return cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        CartItem item = new CartItem();
        item.setPhoneId(phoneId);
        item.setQuantity(quantity);
        cart.getCartItems().add(item);
    }

    @Override
    public void update(Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void remove(Long phoneId) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public int getCartSize() {
        return cart.getCartItems().size();
    }
}
