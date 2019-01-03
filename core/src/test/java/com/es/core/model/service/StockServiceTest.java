package com.es.core.model.service;

import com.es.core.dao.StockDao;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import com.es.core.service.StockServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class StockServiceTest {
    private static final Integer GET_STOCK_INVOCATION_TIMES = 1;
    private static final Long existedPhoneId = 1007L, notExistedPhoneId = 12312L;
    private static final Integer existedStock = 17;
    private static final Integer existedReserved = 6;
    private static final Long nullId = null;
    private Stock existedStockItem;
    private Phone existedPhone;

    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private StockDao stockDao;

    @Before
    public void init() {
        initMocks(this);
        initAndMock();
        setMockitoBehaviour();
    }

    @Test
    public void shouldAssertStockFoundSuccessfullyWhenGetStockWithExistedId() {
        Stock stock = stockService.getStock(existedPhoneId);

        assertEquals(stock.getPhone().getId(), existedStockItem.getPhone().getId());
        assertEquals(stock.getReserved(), existedStockItem.getReserved());
        assertEquals(stock.getStock(), existedStockItem.getStock());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void shouldThrowEmptyResultDataAccessExceptionWhenGetStockWithNotExistedId() {
        stockService.getStock(notExistedPhoneId);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void shouldThrowEmptyResultDataAccessExceptionWhenGetStockWithNullId() {
        stockService.getStock(nullId);
    }

    @After
    public void verifyMock() {
        verify(stockDao, VerificationModeFactory.times(GET_STOCK_INVOCATION_TIMES)).getStock(Mockito.any());
        reset(stockDao, existedPhone, existedStockItem);
    }

    private void initAndMock() {
        existedStockItem = new Stock();
        existedPhone = new Phone();
        existedStockItem = Mockito.mock(Stock.class);
        existedPhone = Mockito.mock(Phone.class);
    }

    private void setMockitoBehaviour() {
        when(existedStockItem.getPhone()).thenReturn(existedPhone);
        when(existedPhone.getId()).thenReturn(existedPhoneId);
        when(existedStockItem.getReserved()).thenReturn(existedReserved);
        when(existedStockItem.getStock()).thenReturn(existedStock);
        when(stockDao.getStock(existedPhoneId)).thenReturn(existedStockItem);
        when(stockDao.getStock(notExistedPhoneId)).thenThrow(EmptyResultDataAccessException.class);
        when(stockDao.getStock(nullId)).thenThrow(EmptyResultDataAccessException.class);
    }
}
