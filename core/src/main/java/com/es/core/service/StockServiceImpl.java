package com.es.core.service;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Stock;
import com.es.core.dao.StockDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StockServiceImpl implements StockService {
    private final StockDao stockDao;

    @Autowired
    public StockServiceImpl(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    @Override
    public Stock getStock(Long phoneId) {
        return stockDao.getStock(phoneId);
    }

    @Override
    public void updateStock(Order order) {
        stockDao.updateStock(order);
    }

    @Override
    public void updateStockStatusBased(OrderStatus status, String orderId) {
        stockDao.updateStockStatusBased(status, orderId);
    }
}
