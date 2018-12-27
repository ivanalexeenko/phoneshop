package com.es.core.cart;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartItem {
    @NotNull(message = "error.phoneid.empty")
    private Long phoneId;

    @NotNull(message = "error.input.empty")
    @Min(value = 1L, message = "error.quantity.less.equal.zero")
    private Long quantity;

    public CartItem() {}

    public CartItem(Long phoneId, Long quantity) {
        this.phoneId = phoneId;
        this.quantity = quantity;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getPhoneId() {
        return phoneId;
    }
}
