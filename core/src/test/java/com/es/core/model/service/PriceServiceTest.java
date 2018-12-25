package com.es.core.model.service;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.dao.PhoneDao;
import com.es.core.model.phone.Phone;
import com.es.core.service.PriceService;
import com.es.core.service.PriceServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PriceServiceTest {
    private static final Integer GET_CART_PRICE_CALL_TIMES_MAX = 1;
    private static final Integer INVOCATION_MULTIPLIER = 2;
    private static Long[][] cartItemsInfo = {{1001L, 12L}, {1231L, 1L}, {9131L, 4L}, {3113L, 3L}};
    private static Double[] phonePricesInfo = {199.99, 666.66, 400.0, 1000.0};
    private List<CartItem> cartItems;
    private List<Phone> phones;
    private BigDecimal actualTotalPrice;
    private BigDecimal totalPriceEmpty = BigDecimal.ZERO;

    @Configuration
    static class PriceServiceTestContextConfiguration {
        @Bean
        public Cart getCart() {
            return Mockito.mock(Cart.class);
        }

        @Bean
        public PhoneDao getPhoneDao() {
            return Mockito.mock(PhoneDao.class);
        }

        @Bean
        public PriceService getPriceService() {
            return new PriceServiceImpl(getCart(), getPhoneDao());
        }
    }

    @Autowired
    private PriceService priceService;

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private Cart cart;

    @Before
    public void init() {
        cartItems = new ArrayList<CartItem>();
        phones = new ArrayList<Phone>();
        mockLists();
        actualTotalPrice = BigDecimal.ZERO;
        countPrice();
        setMockBehaviour();
    }

    @Test
    @DirtiesContext
    public void shouldAssertTotalPriceCountedCorrectlyWhenGetCartPriceFullCart() {
        BigDecimal totalPrice = priceService.getCartPrice();

        assertEquals(totalPrice, actualTotalPrice);
    }

    @Test
    @DirtiesContext
    public void shouldAssertTotalPriceCountedCorrectlyWhenGetCartPriceEmptyCart() {
        cartItems.clear();

        BigDecimal totalPrice = priceService.getCartPrice();

        assertEquals(totalPrice, totalPriceEmpty);
    }

    @After
    public void verify() {
        Mockito.verify(phoneDao, VerificationModeFactory.times(cartItems.size() * INVOCATION_MULTIPLIER)).get(Mockito.anyLong());
        Mockito.reset(phoneDao, cart);
        for (Phone phone : phones) {
            Mockito.reset(phone);
        }
        for (CartItem cartItem : cartItems) {
            Mockito.reset(cartItem);
        }
    }


    private void countPrice() {
        int index = 0;
        for (Long[] cartItemInfo : cartItemsInfo) {
            BigDecimal amount = BigDecimal.valueOf(cartItemInfo[1]);
            BigDecimal price = BigDecimal.valueOf(phonePricesInfo[index]);
            actualTotalPrice = actualTotalPrice.add(price.multiply(amount));
            index++;
        }
    }

    private void setMockBehaviour() {
        int index = 0;
        for (CartItem cartItem : cartItems) {
            Mockito.when(cartItem.getPhoneId()).thenReturn(cartItemsInfo[index][0]);
            Mockito.when(cartItem.getQuantity()).thenReturn(cartItemsInfo[index][1]);
            index++;
        }
        Mockito.when(cart.getCartItems()).thenReturn(cartItems);
        index = 0;
        for (Phone phone : phones) {
            Mockito.when(phone.getPrice()).thenReturn(BigDecimal.valueOf(phonePricesInfo[index]));
            Mockito.when(phoneDao.get(cartItemsInfo[index][0])).thenReturn(Optional.of(phones.get(index)));
            index++;
        }
    }

    private void mockLists() {
        for (Long[] aCartItemsInfo : cartItemsInfo) {
            CartItem cartItem = new CartItem();
            cartItem = Mockito.mock(CartItem.class);
            Phone phone = new Phone();
            phone = Mockito.mock(Phone.class);
            cartItems.add(cartItem);
            phones.add(phone);
        }
    }
}
