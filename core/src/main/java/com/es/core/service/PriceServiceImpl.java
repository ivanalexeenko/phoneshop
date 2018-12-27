package com.es.core.service;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.dao.PhoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.function.Consumer;

@Service
public class PriceServiceImpl implements PriceService {
    private final PhoneDao phoneDao;
    private final Cart cart;

    @Autowired
    public PriceServiceImpl(Cart cart, PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
        this.cart = cart;
    }

    @Override
    public BigDecimal getCartPrice() {
        final BigDecimal[] totalPrice = new BigDecimal[1];
        totalPrice[0] = BigDecimal.ZERO;
        cart.getCartItems().forEach(cartItem -> {
            BigDecimal tempPrice = BigDecimal.ZERO;
            if (phoneDao.get(cartItem.getPhoneId()).isPresent()) {
                tempPrice = phoneDao.get(cartItem.getPhoneId()).get().getPrice();
            }
            tempPrice = tempPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalPrice[0] = totalPrice[0].add(tempPrice);
        });
        return totalPrice[0];
    }
}
