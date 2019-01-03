package com.es.phoneshop.web.validator;

import com.es.core.cart.StringCartItem;
import com.es.core.dao.StockDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class StringCartItemValidator implements Validator {
    private static final String QUANTITY_FIELD_NAME = "quantityString";
    private static final String PHONE_ID_FIELD_NAME = "phoneIdString";
    private final StockDao stockDao;

    @Value("error.phoneid.empty")
    private String phoneIdEmptyMessage;

    @Value("error.input.empty")
    private String inputEmptyMessage;

    @Value("error.quantity.less.equal.zero")
    private String quantityLessEqualZeroMessage;

    @Value("error.not.number")
    private String notNumberMessage;

    @Value("error.not.enough")
    private String notEnoughMessage;

    @Autowired
    public StringCartItemValidator(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return StringCartItem.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object item, Errors errors) {
        StringCartItem stringCartItem = (StringCartItem) item;
        if (StringUtils.isEmpty(stringCartItem.getQuantityString())) {
            errors.rejectValue(QUANTITY_FIELD_NAME, inputEmptyMessage);
        }
        if (StringUtils.isEmpty(stringCartItem.getPhoneIdString())) {
            errors.rejectValue(PHONE_ID_FIELD_NAME, phoneIdEmptyMessage);
        }

        try {
            long quantity = Long.parseLong(stringCartItem.getQuantityString());
            long phoneId = Long.parseLong(stringCartItem.getPhoneIdString());
            if (quantity <= 0) {
                errors.rejectValue(QUANTITY_FIELD_NAME, quantityLessEqualZeroMessage);
            }
            if (stockDao.getStock(phoneId).getStock() < quantity) {
                errors.rejectValue(QUANTITY_FIELD_NAME, notEnoughMessage);
            }
        } catch (NumberFormatException exception) {
            errors.rejectValue(QUANTITY_FIELD_NAME, notNumberMessage);
        }
    }
}
