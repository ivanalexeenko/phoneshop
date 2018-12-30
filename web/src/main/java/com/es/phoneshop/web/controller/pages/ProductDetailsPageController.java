package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.service.CartService;
import com.es.core.service.PhoneService;
import com.es.core.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {
    private static final String PRODUCT_DETAILS_VIEW_NAME = "productDetails";
    private static final String PHONE_ATTRIBUTE_NAME = "phone";
    private static final String CART_ITEM_ATTRIBUTE_NAME = "cartItem";
    private static final String CART_SIZE_ATTRIBUTE_NAME = "cartSize";
    private static final String CART_PRICE_ATTRIBUTE_NAME = "cartPrice";
    private final PhoneService phoneService;
    private final CartService cartService;
    private final PriceService priceService;

    @Autowired
    public ProductDetailsPageController(PhoneService phoneService, CartService cartService, PriceService priceService) {
        this.phoneService = phoneService;
        this.cartService = cartService;
        this.priceService = priceService;
    }

    @GetMapping(value = "/{phoneId}")
    public String showProductList(@PathVariable Long phoneId, Model model) {
        Optional<Phone> optionalPhone = phoneService.get(phoneId);
        Optional<CartItem> optionalCartItem = cartService.get(phoneId);
        model.addAttribute(PHONE_ATTRIBUTE_NAME, optionalPhone.orElse(null));
        model.addAttribute(CART_ITEM_ATTRIBUTE_NAME, optionalCartItem.orElse(null));
        model.addAttribute(CART_SIZE_ATTRIBUTE_NAME, cartService.getCartSize());
        model.addAttribute(CART_PRICE_ATTRIBUTE_NAME, priceService.getCartPrice());
        return PRODUCT_DETAILS_VIEW_NAME;
    }

    @PostMapping
    public String getRequest(HttpServletRequest request) throws IOException {
        return PRODUCT_DETAILS_VIEW_NAME;
    }
}