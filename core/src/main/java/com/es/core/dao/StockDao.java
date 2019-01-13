package com.es.core.dao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Stock;

public interface StockDao {
    Stock getStock(Long phoneId);
    void updateStock(Order order);
    void updateStockStatusBased(OrderStatus status, String orderId);
}
