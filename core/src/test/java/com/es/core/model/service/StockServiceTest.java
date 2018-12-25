package com.es.core.model.service;

import com.es.core.dao.StockDao;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import com.es.core.service.StockService;
import com.es.core.service.StockServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class StockServiceTest {
    private static final Integer GET_STOCK_INVOCATION_TIMES = 1;
    private static final Long existedPhoneId = 1007L, notExistedPhoneId = 12312L;
    private static final Integer existedStock = 17;
    private static final Integer existedReserved = 6;
    private static final Long nullId = null;
    private Stock existedStockItem;
    private Phone existedPhone;

    @Configuration
    static class StockServiceTestContextConfiguration {
        @Bean
        public StockDao getStockDao() {
            return Mockito.mock(StockDao.class);
        }

        @Bean
        public StockService getStockService() {
            return new StockServiceImpl(getStockDao());
        }
    }

    @Autowired
    private StockService stockService;

    @Autowired
    private StockDao stockDao;

    @Before
    public void init() {
        initAndMock();
        setMockitoBehaviour();
    }

    @Test
    @DirtiesContext
    public void shouldAssertStockFoundSuccessfullyWhenGetStockWithExistedId() {
        Stock stock = stockService.getStock(existedPhoneId);

        assertEquals(stock.getPhone().getId(), existedStockItem.getPhone().getId());
        assertEquals(stock.getReserved(), existedStockItem.getReserved());
        assertEquals(stock.getStock(), existedStockItem.getStock());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @DirtiesContext
    public void shouldThrowEmptyResultDataAccessExceptionWhenGetStockWithNotExistedId() {
        stockService.getStock(notExistedPhoneId);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @DirtiesContext
    public void shouldThrowEmptyResultDataAccessExceptionWhenGetStockWithNullId() {
        stockService.getStock(nullId);
    }

    @After
    public void verify() {
        Mockito.verify(stockDao, VerificationModeFactory.times(GET_STOCK_INVOCATION_TIMES)).getStock(Mockito.any());
        Mockito.reset(stockDao, existedPhone, existedStockItem);
    }

    private void initAndMock() {
        existedStockItem = new Stock();
        existedPhone = new Phone();
        existedStockItem = Mockito.mock(Stock.class);
        existedPhone = Mockito.mock(Phone.class);
    }

    private void setMockitoBehaviour() {
        Mockito.when(existedStockItem.getPhone()).thenReturn(existedPhone);
        Mockito.when(existedPhone.getId()).thenReturn(existedPhoneId);
        Mockito.when(existedStockItem.getReserved()).thenReturn(existedReserved);
        Mockito.when(existedStockItem.getStock()).thenReturn(existedStock);
        Mockito.when(stockDao.getStock(existedPhoneId)).thenReturn(existedStockItem);
        Mockito.when(stockDao.getStock(notExistedPhoneId)).thenThrow(EmptyResultDataAccessException.class);
        Mockito.when(stockDao.getStock(nullId)).thenThrow(EmptyResultDataAccessException.class);
    }
}
