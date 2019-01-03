package com.es.core.service;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Order createOrder(Cart cart);
    void placeOrder(Order order);
    BigDecimal getTotalOrderPrice(Order order);
    List<String> getOrderMessages(Order order);
}
