package com.es.core.dao;

import com.es.core.model.phone.Stock;

import java.util.List;

public interface StockDao {
    Stock getStock(Long phoneId);

    void updateStocks(List<Stock> newStocks, List<Long> phoneIds);
}
