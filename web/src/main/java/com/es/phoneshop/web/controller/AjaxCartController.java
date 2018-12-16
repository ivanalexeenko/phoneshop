package com.es.phoneshop.web.controller;

import com.es.core.cart.CartItem;
import com.es.core.cart.CartItemJsonResponse;
import com.es.core.service.CartService;
import com.es.core.service.PriceService;
import com.es.core.service.StockService;
import com.es.core.model.phone.Stock;
import com.es.core.message.ApplicationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "**/ajaxCart", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AjaxCartController {

    private static final String QUANTITY_FIELD_NAME = "quantity";
    private final CartService cartService;
    private final StockService stockService;
    private final PriceService priceService;
    private final MessageSource messageSource;

    @Autowired
    public AjaxCartController(CartService cartService, StockService stockService, PriceService priceService, MessageSource messageSource) {
        this.cartService = cartService;
        this.stockService = stockService;
        this.priceService = priceService;
        this.messageSource = messageSource;
    }

    @PostMapping
    public CartItemJsonResponse addPhone(@RequestBody @Valid CartItem cartItem, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream().collect(
                    Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
            );
            return handleResponse(true, errors, null);
        }

        Stock stock = stockService.getStock(cartItem.getPhoneId());
        if (cartItem.getQuantity() > stock.getStock()) {
            Map<String, String> errors = new HashMap<>();
            errors.put(QUANTITY_FIELD_NAME, ApplicationMessage.NOT_ENOUGH);
            return handleResponse(true, errors, null);
        }
        cartService.addPhone(cartItem.getPhoneId(), cartItem.getQuantity());
        return handleResponse(false, null, cartItem);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CartItemJsonResponse handleException() {
        Map<String, String> errors = new HashMap<>();
        errors.put(QUANTITY_FIELD_NAME, ApplicationMessage.NOT_A_NUMBER);
        return handleResponse(true, errors, null);
    }

    private CartItemJsonResponse handleResponse(boolean hasErrors, Map<String, String> errors, CartItem cartItem) {
        CartItemJsonResponse response = new CartItemJsonResponse();
        if (hasErrors) {
            response.setIsValidated(false);
            errors.replaceAll((key, value) -> messageSource.getMessage(value, null, LocaleContextHolder.getLocale()));
            response.setErrorMessages(errors);
        } else {
            response.setIsValidated(true);
            response.setSuccessMessage(messageSource.getMessage(ApplicationMessage.ADDED_TO_CART_SUCCESS, new Object[]{cartItem.getPhoneId(), cartItem.getQuantity()}, LocaleContextHolder.getLocale()));
        }
        response.setCartSize(cartService.getCartSize());
        response.setTotalCartPrice(priceService.getCartPrice());
        return response;
    }
}
