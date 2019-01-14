package com.es.core.dao;

import com.es.core.cart.CartItem;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    void placeOrder(Order order);
    List<OrderItem> getOrderItems(String orderId);
    Optional<Order> getOrder(String id);
    List<Order> getOrders();
    void updateStatusWithId(OrderStatus status, String orderId);
}
