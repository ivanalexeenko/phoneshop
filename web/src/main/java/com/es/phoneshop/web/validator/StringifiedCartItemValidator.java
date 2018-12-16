package com.es.phoneshop.web.validator;

import com.es.core.cart.StringifiedCartItem;
import com.es.core.dao.StockDao;
import com.es.core.message.ApplicationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class StringifiedCartItemValidator implements Validator {
    private static final String QUANTITY_FIELD_NAME = "quantityString";
    private static final String PHONE_ID_FIELD_NAME = "phoneIdString";
    private final StockDao stockDao;

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
        if (stringifiedCartItem.getQuantityString() == null) {
            errors.rejectValue(QUANTITY_FIELD_NAME, ApplicationMessage.INPUT_IS_NULL);
        }
        if (stringifiedCartItem.getPhoneIdString() == null) {
            errors.rejectValue(PHONE_ID_FIELD_NAME, ApplicationMessage.PHONE_ID_IS_NULL);
        }

        try {
            long quantity = Long.parseLong(stringifiedCartItem.getQuantityString());
            long phoneId = Long.parseLong(stringifiedCartItem.getPhoneIdString());
            if (quantity <= 0) {
                errors.rejectValue(QUANTITY_FIELD_NAME, ApplicationMessage.QUANTITY_BIGGER_ZERO);
            }
            if (stockDao.getStock(phoneId).getStock() < quantity) {
                errors.rejectValue(QUANTITY_FIELD_NAME, ApplicationMessage.NOT_ENOUGH);
            }
        } catch (NumberFormatException exception) {
            errors.rejectValue(QUANTITY_FIELD_NAME, ApplicationMessage.NOT_A_NUMBER);
        }
    }
}
