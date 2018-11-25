package com.es.core.cart;

import com.es.core.model.phone.Phone;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


//@QuantityAgreedStock(fieldId = "phoneId", fieldQuantity = "quantity")
public class CartItem {
    private static final String PHONE_ID_IS_NULL_MESSAGE = "Phone id is empty";
    public static final String INPUT_IS_NULL_MESSAGE = "Input field is empty";
    private static final String QUANTITY_BIGGER_ZERO_MESSAGE = "Quantity should be >= zero";

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

    @NotNull(message = PHONE_ID_IS_NULL_MESSAGE)
    private Long phoneId;

    @NotNull(message = INPUT_IS_NULL_MESSAGE)
    @Min(value = 1L, message = QUANTITY_BIGGER_ZERO_MESSAGE)

    private Long quantity;


}
