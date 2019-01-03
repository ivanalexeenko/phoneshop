package com.es.core.service;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final Cart cart;

    @Autowired
    public CartServiceImpl(Cart cart) {
        this.cart = cart;
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    @Override
    public Optional<CartItem> getCartItem(Long id) {
        return cart.getCartItems().stream().filter(item -> item.getPhoneId().equals(id)).findAny();
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        CartItem item = new CartItem(phoneId, quantity);
        Optional<CartItem> optional = this.getCartItem(phoneId);
        if (!optional.isPresent()) {
            cart.getCartItems().add(item);
        } else {
            CartItem newCartItem = optional.get();
            newCartItem.setQuantity(newCartItem.getQuantity() + item.getQuantity());
        }
    }

    @Override
    public void update(Map<Long, Long> items) {
        items.forEach((key, value) -> {
            Optional<CartItem> optionalCartItem = getCartItem(key);
            if (optionalCartItem.isPresent()) {
                CartItem item = optionalCartItem.get();
                item.setQuantity(value);
            }
        });
    }

    @Override
    public void remove(Long phoneId) {
        Optional<CartItem> optionalCartItem = this.getCartItem(phoneId);
        optionalCartItem.ifPresent(cartItem -> cart.getCartItems().remove(cartItem));
    }

    @Override
    public int getCartSize() {
        return cart.getCartItems().size();
    }
}
