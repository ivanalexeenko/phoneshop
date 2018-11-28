package com.es.phoneshop.web.controller;

import com.es.core.cart.CartItem;
import com.es.core.cart.CartItemJsonResponse;
import com.es.core.service.CartService;
import com.es.core.service.StockService;
import com.es.core.model.phone.Stock;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AjaxCartController {

    @Resource
    private CartService cartService;

    @Resource
    private StockService stockService;

    private static final String NOT_A_NUMBER_MESSAGE = "Ooops, input value is not a number";
    private static final String QUANTITY_FIELD_NAME = "quantity";
    private static final String PHONE_ID_FIELD_NAME = "phoneId";
    private static final String NOT_ENOUGH_MESSAGE = "Sorry, we do not have such amount of this item";

    @PostMapping(value = "/ajaxCart", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public CartItemJsonResponse addPhone(@RequestBody @Valid CartItem cartItem, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream().collect(
                    Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
            );
            return handleResponse(true, errors, null);
        }
        Stock stock = stockService.getStock(cartItem.getPhoneId());
        if (cartItem.getQuantity() > stock.getStock()) {
            Map<String, String> errors = new HashMap<>();
            errors.put(QUANTITY_FIELD_NAME, NOT_ENOUGH_MESSAGE);
            return handleResponse(true, errors, null);
        }
        cartService.addPhone(cartItem.getPhoneId(), cartItem.getQuantity());
        return handleResponse(false, null, cartItem);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public CartItemJsonResponse handleException() {
        Map<String, String> error = new HashMap<>();
        error.put(QUANTITY_FIELD_NAME, NOT_A_NUMBER_MESSAGE);
        CartItemJsonResponse response = new CartItemJsonResponse();
        response.setIsValidated(false);
        response.setErrorMessages(error);
        return response;
    }

    private CartItemJsonResponse handleResponse(boolean hasErrors, Map<String, String> errors, CartItem cartItem) {
        CartItemJsonResponse response = new CartItemJsonResponse();
        if (hasErrors) {
            response.setIsValidated(false);
            response.setErrorMessages(errors);
            response.setCartSize(cartService.getCartSize());
        } else {
            response.setIsValidated(true);
            response.setCartItem(cartItem);
            response.setSuccessMessage();
            response.setCartSize(cartService.getCart().getCartItems().size());
        }
        return response;
    }
}
