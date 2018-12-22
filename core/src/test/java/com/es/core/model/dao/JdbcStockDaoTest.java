package com.es.core.model.dao;

import com.es.core.dao.StockDao;
import com.es.core.model.phone.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.validation.constraints.AssertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context/test-config.xml")
public class JdbcStockDaoTest {
    private static final String NOT_EQUAL_STOCKS_MESSAGE = "Stocks are not equal";
    private static final String NOT_EQUAL_RESERVED_MESSAGE = "Reserved are not equal";
    private Long existedPhoneId = 1001L;
    private Long notExistedPhoneId = 924141L;
    private Integer inStock = 11;
    private Integer reserved = 0;

    @Autowired
    private StockDao stockDao;

    @Test
    @DirtiesContext
    public void shouldAssertRightStockForPhoneWithIdFoundWhenGetStock() {
        Stock stock = stockDao.getStock(existedPhoneId);

        Assert.isTrue(stock.getStock().equals(inStock),NOT_EQUAL_STOCKS_MESSAGE);
        Assert.isTrue(stock.getReserved().equals(reserved),NOT_EQUAL_RESERVED_MESSAGE);
    }
}
