package com.es.core.model.service;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.service.CartService;
import com.es.core.service.CartServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CartServiceTest {
    private static Long[] cartItemIds = {1001L, 1002L, 1004L};
    private static Long[] cartItemQuantities = {10L, 1L, 77L};
    private static Long[] cartItemNewQuantities = {1012L, 133L, 31L};
    private List<CartItem> cartItems;
    private static Integer existingItemIndex = 0;
    private static Long nonExistingItemId = 231049L;
    private static Long addQuantity = 8L;
    private static Integer cartSize = 3;


    @Configuration
    static class CartServiceTestContextConfiguration {
        @Bean
        public Cart getCart() {
            return Mockito.mock(Cart.class);
        }

        @Bean
        public CartService getCartService() {
            return new CartServiceImpl(getCart());
        }
    }

    @Autowired
    private CartService cartService;

    @Mock
    private Cart cart;

    @Before
    public void init() {
        cartItems = new ArrayList<>();
        for (int i = 0; i < cartItemIds.length; i++) {
            CartItem cartItem = Mockito.mock(CartItem.class);
            Mockito.when(cartItem.getQuantity()).thenCallRealMethod();
            Mockito.doCallRealMethod().when(cartItem).setQuantity(Mockito.any(Long.class));
            Mockito.when(cartItem.getPhoneId()).thenCallRealMethod();
            Mockito.doCallRealMethod().when(cartItem).setPhoneId(Mockito.any(Long.class));
            cartItem.setQuantity(cartItemQuantities[i]);
            cartItem.setPhoneId(cartItemIds[i]);
            cartItems.add(cartItem);
        }
        Mockito.when(cart.getCartItems()).thenReturn(cartItems);

    }

    @Test
    @DirtiesContext
    public void shouldAssertCartReturnedSuccessfullyWhenGetCart() {
        Cart testCart = cartService.getCart();

        assertEquals(testCart, cart);
    }

    @Test
    @DirtiesContext
    public void shouldAssertCartItemFoundSuccessfullyWhenGetExistingId() {
        Optional optionalItem = cartService.get(cartItemIds[existingItemIndex]);

        assertTrue(optionalItem.isPresent());
        assertEquals(optionalItem.get(), cartItems.get(existingItemIndex));
    }

    @Test
    @DirtiesContext
    public void shouldAssertCartItemNotFoundWhenGetNonExistingId() {
        Optional optionalItem = cartService.get(nonExistingItemId);

        assertTrue(!optionalItem.isPresent());
    }

    @Test
    @DirtiesContext
    public void shouldAssertQuantityAddedAndSameCartSizeWhenAddPhoneExistingId() {
        cartService.addPhone(cartItemIds[existingItemIndex], addQuantity);

        Long newQuantity = cartItemQuantities[existingItemIndex] + addQuantity;
        Long actualQuantity = cartItems.get(existingItemIndex).getQuantity();
        Integer newSize = cartItems.size();

        assertEquals(newQuantity, actualQuantity);
        assertEquals(cartSize, newSize);
    }

    @Test
    @DirtiesContext
    public void shouldAssertNewPhoneAddedAndSizeChangedWhenAddPhoneNonExistingId() {
        cartService.addPhone(nonExistingItemId, addQuantity);

        Integer newSize = cartItems.size();
        Integer actualSize = cartSize + 1;

        assertEquals(cartItems.get(actualSize - 1).getQuantity(), addQuantity);
        assertEquals(actualSize, newSize);
    }

    @Test
    @DirtiesContext
    public void shouldAssertOldQuantitiesReplacedByNewWhenUpdate() {
        Map<Long, Long> updateMap = new HashMap<>();
        for (int i = 0; i < cartItems.size(); i++) {
            updateMap.put(cartItemIds[i], cartItemNewQuantities[i]);
        }

        cartService.update(updateMap);

        for (int i = 0; i < cartItems.size(); i++) {
            assertEquals(cartItems.get(i).getQuantity(), cartItemNewQuantities[i]);
        }
    }

    @Test
    @DirtiesContext
    public void shouldAssertCartSizeDoNotChangeWhenRemoveNonExistingId() {
        cartService.remove(nonExistingItemId);

        Integer actualSize = cartItems.size();

        assertEquals(cartSize, actualSize);
    }

    @Test
    @DirtiesContext
    public void shouldAssertCartSizeDecreasedWhenRemoveExistingId() {
        cartService.remove(cartItemIds[existingItemIndex]);

        Integer newSize = cartItems.size();
        Integer actualSize = cartSize - 1;

        assertEquals(newSize, actualSize);
    }

    @Test
    @DirtiesContext
    public void shouldAssertActualCartSizeReturnedWhenGetCartSize() {
        Integer size = cartService.getCartSize();

        assertEquals(cartSize, size);
    }
}
