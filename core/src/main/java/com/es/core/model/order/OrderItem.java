package com.es.core.model.order;

import com.es.core.model.phone.Phone;

public class OrderItem {
    private String id;
    private Phone phone;
    private Long quantity;
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(final Phone phone) {
        this.phone = phone;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }
}
