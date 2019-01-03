package com.es.core.cart;

import java.math.BigDecimal;
import java.util.Map;

public class CartItemJsonResponse {
    private Boolean isValidated;
    private Map<String, String> errorMessages;
    private int cartSize;
    private String successMessage;
    private BigDecimal totalCartPrice;

    public void setTotalCartPrice(BigDecimal totalCartPrice) {
        this.totalCartPrice = totalCartPrice;
    }

    public BigDecimal getTotalCartPrice() {
        return totalCartPrice;
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
