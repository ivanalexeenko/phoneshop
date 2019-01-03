package com.es.core.model.service;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.service.PhoneService;
import com.es.core.service.PriceServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PriceServiceTest {
    private static Long[][] cartItemsInfo = {{1001L, 12L}, {1231L, 1L}, {9131L, 4L}, {3113L, 3L}};
    private static Double[] phonePricesInfo = {199.99, 666.66, 400.0, 1000.0};
    private List<CartItem> cartItems;
    private List<Phone> phones;
    private BigDecimal actualTotalPrice;
    private BigDecimal totalPriceEmpty = BigDecimal.ZERO;

    @InjectMocks
    private PriceServiceImpl priceService;

    @Mock
    private PhoneService phoneService;

    @Mock
    private Cart cart;

    @Before
    public void init() {
        initMocks(this);
        cartItems = new ArrayList<CartItem>();
        phones = new ArrayList<Phone>();
        mockLists();
        actualTotalPrice = BigDecimal.ZERO;
        countPrice();
        setMockBehaviour();
    }

    @Test
    public void shouldAssertTotalPriceCountedCorrectlyWhenGetCartPriceFullCart() {
        BigDecimal totalPrice = priceService.getCartPrice();

        assertEquals(totalPrice, actualTotalPrice);
    }

    @Test
    public void shouldAssertTotalPriceCountedCorrectlyWhenGetCartPriceEmptyCart() {
        cartItems.clear();

        BigDecimal totalPrice = priceService.getCartPrice();

        assertEquals(totalPrice, totalPriceEmpty);
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
            when(cartItem.getPhoneId()).thenReturn(cartItemsInfo[index][0]);
            when(cartItem.getQuantity()).thenReturn(cartItemsInfo[index][1]);
            index++;
        }
        when(cart.getCartItems()).thenReturn(cartItems);
        index = 0;
        for (Phone phone : phones) {
            when(phone.getPrice()).thenReturn(BigDecimal.valueOf(phonePricesInfo[index]));
            when(phoneService.get(cartItemsInfo[index][0])).thenReturn(Optional.of(phones.get(index)));
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
