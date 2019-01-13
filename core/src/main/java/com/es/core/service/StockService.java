package com.es.core.service;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Stock;

public interface StockService {
    Stock getStock(Long phoneId);

    void updateStocks(Order order);

    void updateStockStatusBased(OrderStatus status, String orderId);
}
