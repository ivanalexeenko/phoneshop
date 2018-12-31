package com.es.core.service;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {
    private final PhoneService phoneService;
    private final Cart cart;

    public PriceServiceImpl(PhoneService phoneService, Cart cart) {
        this.phoneService = phoneService;
        this.cart = cart;
    }

    @Override
    public BigDecimal getCartPrice() {
        return cart.getCartItems().stream().map(cartItem -> {
            Optional<Phone> optionalPhone = phoneService.get(cartItem.getPhoneId());
            if (optionalPhone.isPresent()) {
                return optionalPhone.get().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            }
            return BigDecimal.ZERO;
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
