package com.es.core.cart;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

@Constraint(validatedBy = QuantityAgreedStockValidator.class)
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface QuantityAgreedStock {
    public static final String DEFAULT_MESSAGE = "Not Enough";

    String message() default DEFAULT_MESSAGE;

    String fieldId();

    String fieldQuantity();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}