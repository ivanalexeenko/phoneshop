package com.es.core.dao;

import com.es.core.model.phone.Stock;

import java.util.List;
import java.util.Map;

public interface StockDao {
    Stock getStock(Long phoneId);

    void updateStocks(Map<Long, Stock> stocksAndIds);
}
