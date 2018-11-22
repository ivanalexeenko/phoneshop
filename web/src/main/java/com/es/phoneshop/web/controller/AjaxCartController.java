package com.es.phoneshop.web.controller;

import com.es.core.cart.CartItem;
import com.es.core.cart.CartItemJsonResponse;
import com.es.core.cart.CartService;
import com.es.core.cart.QuantityAgreedStock;
import org.h2.mvstore.DataUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AjaxCartController {
    @Resource
    private CartService cartService;

    private static final String NOT_A_NUMBER_MESSAGE = "Ooops, input value is not a number";
    private static final String QUANTITY_FIELD_NAME = "quantity";
    private static final String PHONE_ID_FIELD_NAME = "phoneId";
    private static final String NOT_ENOUGH_MESSAGE = "Sorry, we do not have such amount of this item";

    @PostMapping(value = "/ajaxCart", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public CartItemJsonResponse addPhone(@RequestBody @Valid CartItem cartItem, BindingResult result) {

        CartItemJsonResponse response = new CartItemJsonResponse();

        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream().collect(
                    Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
            );

            if (errors.get(PHONE_ID_FIELD_NAME) == null || errors.get(QUANTITY_FIELD_NAME) == null) {
                errors = new HashMap<>();
                errors.put(QUANTITY_FIELD_NAME, NOT_ENOUGH_MESSAGE);
            }

            response.setIsValidated(false);
            response.setErrorMessages(errors);
        } else {
            cartService.addPhone(cartItem.getPhoneId(), cartItem.getQuantity());
            response.setIsValidated(true);
            response.setCartItem(cartItem);
            response.setSuccessMessage();
        }
        response.setCartSize(cartService.getCart().getCartItems().size());

        return response;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleException() {
        Map<String, String> error = new HashMap<>();
        error.put(QUANTITY_FIELD_NAME, NOT_A_NUMBER_MESSAGE);
        CartItemJsonResponse response = new CartItemJsonResponse();
        response.setIsValidated(false);
        response.setErrorMessages(error);
    }
}
