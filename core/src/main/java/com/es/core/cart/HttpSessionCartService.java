package com.es.core.cart;

import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.PhoneDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
