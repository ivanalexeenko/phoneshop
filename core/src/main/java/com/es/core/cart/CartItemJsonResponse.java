package com.es.core.cart;

import java.util.Map;

public class CartItemJsonResponse {
    private CartItem cartItem;
    private Boolean isValidated;
    private Map<String, String> errorMessages;
    private int cartSize;
    private String successMessage;

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public void setErrorMessages(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void setSuccessMessage(String message) {
        this.successMessage = message;
    }

    public String getSuccessMessage() {
        return successMessage;
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

    public int getCartSize() {
        return cartSize;
    }

    public Map<String, String> getErrorMessages() {
        return errorMessages;
    }
}
