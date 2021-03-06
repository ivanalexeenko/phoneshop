package com.es.core.cart;

public class StringCartItem {
    private String phoneIdString;
    private String quantityString;

    public StringCartItem() {
    }

    public StringCartItem(String phoneIdString, String quantityString) {
        this.phoneIdString = phoneIdString;
        this.quantityString = quantityString;
    }

    public void setPhoneIdString(String phoneIdString) {
        this.phoneIdString = phoneIdString;
    }

    public void setQuantityString(String quantityString) {
        this.quantityString = quantityString;
    }

    public String getPhoneIdString() {
        return phoneIdString;
    }

    public String getQuantityString() {
        return quantityString;
    }
}
