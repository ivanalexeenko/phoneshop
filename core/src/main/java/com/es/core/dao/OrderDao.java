package com.es.core.dao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;

import java.util.List;

public interface OrderDao {
    void placeOrder(Order order);
}
