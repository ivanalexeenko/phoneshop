package com.es.core.service;

import com.es.core.dao.OrderDao;
import com.es.core.dao.StockDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {
    private final StockDao stockDao;
    private final OrderDao orderDao;

    @Autowired
    public StockServiceImpl(StockDao stockDao, OrderDao orderDao) {
        this.stockDao = stockDao;
        this.orderDao = orderDao;
    }

    @Override
    public Stock getStock(Long phoneId) {
        return stockDao.getStock(phoneId);
    }

    @Override
    public void updateStocks(Order order) {
        List<Stock> newStocks = new ArrayList<>();
        List<Long> phoneIds = new ArrayList<>();
        order.getOrderItems().forEach(orderItem -> {
            Long phoneId = orderItem.getPhoneId();
            Long quantity = orderItem.getQuantity();
            Stock stock = stockDao.getStock(phoneId);
            Integer newStockValue = stock.getStock() - quantity.intValue();
            Integer newReservedValue = stock.getReserved() + quantity.intValue();
            Stock newStock = new Stock();
            newStock.setStock(newStockValue);
            newStock.setReserved(newReservedValue);
            phoneIds.add(phoneId);
            newStocks.add(newStock);
        });
        stockDao.updateStocks(newStocks, phoneIds);
    }

    @Override
    public void updateStockStatusBased(OrderStatus status, String orderId) {
        List<Long> phoneIds = new ArrayList<>();
        List<Stock> newStocks = new ArrayList<>();
        Optional<Order> optionalOrder = orderDao.getOrder(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOrderItems(orderDao.getOrderItems(orderId));
            order.getOrderItems().forEach(orderItem -> {
                Long phoneId = orderItem.getPhoneId();
                Stock stock = this.getStock(phoneId);
                Long quantity = orderItem.getQuantity();
                Stock newStock = calculateNewStock(order, stock, quantity, status);
                phoneIds.add(phoneId);
                newStocks.add(newStock);
            });
        }
        stockDao.updateStocks(newStocks, phoneIds);
    }

    private Stock calculateNewStock(Order order, Stock stock, Long quantity, OrderStatus status) {
        Integer newStockValue;
        Integer newReservedValue;
        if (order.getStatus().equals(OrderStatus.NEW) && status.equals(OrderStatus.DELIVERED)) {
            newStockValue = stock.getStock();
            newReservedValue = stock.getReserved() - quantity.intValue();
        } else if (order.getStatus().equals(OrderStatus.NEW) && status.equals(OrderStatus.REJECTED)) {
            newStockValue = stock.getStock() + quantity.intValue();
            newReservedValue = stock.getReserved() - quantity.intValue();
        } else {
            newStockValue = stock.getStock();
            newReservedValue = stock.getReserved();
        }
        Stock newStock = new Stock();
        newStock.setStock(newStockValue);
        newStock.setReserved(newReservedValue);
        return newStock;
    }
}