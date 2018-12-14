package com.es.core.service;

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
}
