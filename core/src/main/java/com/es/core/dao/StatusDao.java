package com.es.core.dao;

import com.es.core.model.order.OrderStatus;

public interface StatusDao {
    void updateStatusWithId(OrderStatus status, String orderId);
}
