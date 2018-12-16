package com.es.core.cart;

import com.es.core.message.ApplicationMessage;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartItem {
    public CartItem() {}

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

    @NotNull(message = ApplicationMessage.PHONE_ID_IS_NULL)
    private Long phoneId;

    public CartItem(Long phoneId, Long quantity) {
        this.phoneId = phoneId;
        this.quantity = quantity;
    }

    @NotNull(message = ApplicationMessage.INPUT_IS_NULL)
    @Min(value = 1L, message = ApplicationMessage.QUANTITY_BIGGER_ZERO)
    private Long quantity;
}
