package com.es.phoneshop.web.validator;

import com.es.core.cart.StringifiedCartItem;
import com.es.core.dao.StockDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class StringifiedCartItemValidator implements Validator {
    private static final String QUANTITY_FIELD_NAME = "quantityString";
    private static final String PHONE_ID_FIELD_NAME = "phoneIdString";
    private final StockDao stockDao;

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
    public StringifiedCartItemValidator(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return StringifiedCartItem.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object item, Errors errors) {
        StringifiedCartItem stringifiedCartItem = (StringifiedCartItem) item;
        validateIfFieldsEmpty(stringifiedCartItem, errors);
        try {
            long quantity = Long.parseLong(stringifiedCartItem.getQuantityString());
            long phoneId = Long.parseLong(stringifiedCartItem.getPhoneIdString());
            validateQuantity(quantity, phoneId, errors);
        } catch (NumberFormatException exception) {
            errors.rejectValue(QUANTITY_FIELD_NAME, notNumberMessage);
        }
    }

    private void validateIfFieldsEmpty(StringifiedCartItem stringifiedCartItem, Errors errors) {
        if (StringUtils.isEmpty(stringifiedCartItem.getQuantityString())) {
            errors.rejectValue(QUANTITY_FIELD_NAME, inputNullMessage);
        }
        if (StringUtils.isEmpty(stringifiedCartItem.getPhoneIdString())) {
            errors.rejectValue(PHONE_ID_FIELD_NAME, phoneIdNullMessage);
        }
    }

    private void validateQuantity(Long quantity, Long phoneId, Errors errors) {
        if (quantity <= 0) {
            errors.rejectValue(QUANTITY_FIELD_NAME, quantityLessEqualZeroMessage);
        }
        if (stockDao.getStock(phoneId).getStock() < quantity) {
            errors.rejectValue(QUANTITY_FIELD_NAME, notEnoughMessage);
        }
    }
}
