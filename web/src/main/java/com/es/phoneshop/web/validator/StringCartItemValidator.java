package com.es.phoneshop.web.validator;

import com.es.core.cart.StringCartItem;
import com.es.core.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class StringCartItemValidator implements Validator {
    private static final String QUANTITY_FIELD_NAME = "quantityString";
    private static final String PHONE_ID_FIELD_NAME = "phoneIdString";
    private final StockService stockService;

    @Value("error.input.empty")
    private String inputNullMessage;

    @Value("error.phoneid.empty")
    private String phoneIdNullMessage;

    @Value("error.quantity.less.equal.zero")
    private String quantityLessEqualZeroMessage;

    @Value("error.not.enough")
    private String notEnoughMessage;

    @Value("error.not.number")
    private String notNumberMessage;

    @Autowired
    public StringCartItemValidator(StockService stockService) {
        this.stockService = stockService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return StringCartItem.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object item, Errors errors) {
        StringCartItem stringCartItem = (StringCartItem) item;
        validateIfFieldsEmpty(stringCartItem, errors);
        try {
            long quantity = Long.parseLong(stringCartItem.getQuantityString());
            long phoneId = Long.parseLong(stringCartItem.getPhoneIdString());
            validateQuantity(quantity, phoneId, errors);
        } catch (NumberFormatException exception) {
            errors.rejectValue(QUANTITY_FIELD_NAME, notNumberMessage);
        }
    }

    private void validateIfFieldsEmpty(StringCartItem stringCartItem, Errors errors) {
        if (StringUtils.isEmpty(stringCartItem.getQuantityString())) {
            errors.rejectValue(QUANTITY_FIELD_NAME, inputNullMessage);
        }
        if (StringUtils.isEmpty(stringCartItem.getPhoneIdString())) {
            errors.rejectValue(PHONE_ID_FIELD_NAME, phoneIdNullMessage);
        }
    }

    private void validateQuantity(Long quantity, Long phoneId, Errors errors) {
        if (quantity <= 0) {
            errors.rejectValue(QUANTITY_FIELD_NAME, quantityLessEqualZeroMessage);
        }
        if (stockService.getStock(phoneId).getStock() < quantity) {
            errors.rejectValue(QUANTITY_FIELD_NAME, notEnoughMessage);
        }
    }
}
