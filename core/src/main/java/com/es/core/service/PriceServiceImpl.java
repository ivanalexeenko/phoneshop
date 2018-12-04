package com.es.core.service;

import com.es.core.dao.JdbcCartPriceDao;
import com.es.core.dao.PriceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PriceServiceImpl implements PriceService {
    private final PriceDao priceDao;

    @Autowired
    public PriceServiceImpl(PriceDao priceDao) {
        this.priceDao = priceDao;
    }

    @Override
    public BigDecimal getCartPrice() {
        return priceDao.getPrice();
    }
}
