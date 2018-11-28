package com.es.core.cart;

import java.util.Map;

public class CartItemJsonResponse {
    private static final String DELIMITER = " ";
    private static final String PHONE_SHARP_SUCCESS_MESSAGE_PART = "Phone #";
    private static final String ADDED_TO_CART_SUCCESS_MESSAGE_PART = "added to cart(amount =";
    private static final String LAST_SUCCESS_MESSAGE_PART = ")";

    private CartItem cartItem;
    private Boolean isValidated;
    private Map<String, String> errorMessages;
    private int cartSize;
    private String successMessage;

    public void setSuccessMessage() {
        this.successMessage = String.join(DELIMITER, PHONE_SHARP_SUCCESS_MESSAGE_PART,
                cartItem.getPhoneId().toString(), ADDED_TO_CART_SUCCESS_MESSAGE_PART,
                cartItem.getQuantity().toString(), LAST_SUCCESS_MESSAGE_PART);
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public void setErrorMessages(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public Boolean getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(Boolean validated) {
        isValidated = validated;
    }

    public void setCartSize(int cartSize) {
        this.cartSize = cartSize;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public int getCartSize() {
        return cartSize;
    }

    public Map<String, String> getErrorMessages() {
        return errorMessages;
    }
}
