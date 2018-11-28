package com.es.core.service;

import com.es.core.model.phone.Stock;
import com.es.core.dao.StockDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HttpSessionStockService implements StockService {
    @Resource
    private StockDao stockDao;

    @Override
    public Stock getStock(Long phoneId) {
        return stockDao.getStock(phoneId);
    }

}
