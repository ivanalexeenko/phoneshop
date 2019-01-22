package com.es.core.model.service;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.service.CartServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CartServiceTest {
    private static Long[] cartItemIds = {1001L, 1002L, 1004L};
    private static Long[] cartItemQuantities = {10L, 1L, 77L};
    private static Long[] cartItemNewQuantities = {1012L, 133L, 31L};
    private static Integer existingItemIndex = 0;
    private static Long nonExistingItemId = 231049L;
    private static Long addQuantity = 8L;
    private static Integer cartSize = 3;
    private List<CartItem> cartItems;

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private Cart cart;

    @Before
    public void init() {
        initMocks(this);
        cartItems = new ArrayList<>();
        for (int i = 0; i < cartItemIds.length; i++) {
            CartItem cartItem = Mockito.mock(CartItem.class);
            when(cartItem.getQuantity()).thenCallRealMethod();
            doCallRealMethod().when(cartItem).setQuantity(Mockito.any(Long.class));
            when(cartItem.getPhoneId()).thenCallRealMethod();
            doCallRealMethod().when(cartItem).setPhoneId(Mockito.any(Long.class));
            cartItem.setQuantity(cartItemQuantities[i]);
            cartItem.setPhoneId(cartItemIds[i]);
            cartItems.add(cartItem);
        }
        when(cart.getCartItems()).thenReturn(cartItems);
    }

    @Test
    public void shouldAssertCartReturnedSuccessfullyWhenGetCart() {
        Cart testCart = cartService.getCart();

        assertEquals(testCart, cart);
    }

    @Test
    public void shouldAssertCartItemFoundSuccessfullyWhenGetExistingId() {
        Optional optionalItem = cartService.getCartItem(cartItemIds[existingItemIndex]);

        assertTrue(optionalItem.isPresent());
        assertEquals(optionalItem.get(), cartItems.get(existingItemIndex));
    }

    @Test
    public void shouldAssertCartItemNotFoundWhenGetNonExistingId() {
        Optional optionalItem = cartService.getCartItem(nonExistingItemId);

        assertFalse(optionalItem.isPresent());
    }

    @Test
    public void shouldAssertQuantityAddedAndSameCartSizeWhenAddPhoneExistingId() {
        cartService.addPhone(cartItemIds[existingItemIndex], addQuantity);

        Long newQuantity = cartItemQuantities[existingItemIndex] + addQuantity;
        Long actualQuantity = cartItems.get(existingItemIndex).getQuantity();
        Integer newSize = cartItems.size();

        assertEquals(newQuantity, actualQuantity);
        assertEquals(cartSize, newSize);
    }

    @Test
    public void shouldAssertNewPhoneAddedAndSizeChangedWhenAddPhoneNonExistingId() {
        cartService.addPhone(nonExistingItemId, addQuantity);

        Integer newSize = cartItems.size();
        Integer actualSize = cartSize + 1;

        assertEquals(cartItems.get(actualSize - 1).getQuantity(), addQuantity);
        assertEquals(actualSize, newSize);
    }

    @Test
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
    public void shouldAssertCartSizeDoNotChangeWhenRemoveNonExistingId() {
        cartService.remove(nonExistingItemId);

        Integer actualSize = cartItems.size();

        assertEquals(cartSize, actualSize);
    }

    @Test
    public void shouldAssertCartSizeDecreasedWhenRemoveExistingId() {
        cartService.remove(cartItemIds[existingItemIndex]);

        Integer newSize = cartItems.size();
        Integer actualSize = cartSize - 1;

        assertEquals(newSize, actualSize);
    }

    @Test
    public void shouldAssertActualCartSizeReturnedWhenGetCartSize() {
        Integer size = cartService.getCartSize();

        assertEquals(cartSize, size);
    }
}