package com.es.core.service;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(Cart cart);

    void placeOrder(Order order);

    BigDecimal getTotalOrderPrice(Order order);

    List<String> getOrderMessages(Order order);

    void setOrderItemsId(Order order, String id);

    List<OrderItem> getOrderItems(String orderId);

    Optional<Order> getOrder(String id);

    List<Order> getOrders();

    List<Phone> getPhonesByOrderId(String id);

    void updateStatus(OrderStatus status, String orderId);
}
