package com.es.core.service;

import com.es.core.model.order.OrderStatus;

public interface StatusService {
    void updateStatus(OrderStatus status, String orderId);
}
