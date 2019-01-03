package com.es.core.service;

import com.es.core.model.order.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface PriceService {
    BigDecimal getCartPrice();

    BigDecimal countOrderSubtotalPrice(List<OrderItem> orderItems);
}
