package com.es.core.model.order;

public class OrderItem {
    private String id;
    private Long phoneId;
    private Long quantity;
    private String message;

    public String getId() {
        return id;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public String getMessage() {
        return message;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}