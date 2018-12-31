package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartItem;
import com.es.core.cart.StringCartItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import com.es.core.service.CartService;
import com.es.core.service.PhoneService;
import com.es.core.service.PriceService;
import com.es.core.service.StockService;
import com.es.phoneshop.web.validator.StringCartItemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    private static final String CART_PAGE_NAME = "cart";
    private static final String CART_ITEMS_ATTRIBUTE_NAME = "cartItems";
    private static final String CART_SIZE_ATTRIBUTE_NAME = "cartSize";
    private static final String CART_PRICE_ATTRIBUTE_NAME = "cartPrice";
    private static final String PHONES_ATTRIBUTE_NAME = "phones";
    private static final String STOCKS_ATTRIBUTE_NAME = "stocks";
    private static final String PHONE_IDS_ATTRIBUTE_NAME = "phoneIds";
    private static final String QUANTITY_STRINGS_ATTRIBUTE_NAME = "quantityStrings";
    private static final String MESSAGES_ATTRIBUTE_NAME = "messages";
    private static final String HIDDEN_QUANTITY_PARAMETER_NAME = "hiddenQuantity";
    private static final String HIDDEN_PHONE_ID_PARAMETER_NAME = "hiddenPhoneId";
    private static final String EMPTY_MESSAGE = "";
    private static final String DELETE_ID_ATTRIBUTE_NAME = "deleteId";
    private final CartService cartService;
    private final PriceService priceService;
    private final PhoneService phoneService;
    private final StockService stockService;
    private final StringCartItemValidator validator;
    private final MessageSource messageSource;

    @Value("update.success")
    private String updateSuccessMessage;

    @Autowired
    public CartPageController(CartService cartService, PriceService priceService, PhoneService phoneService, StockService stockService, StringCartItemValidator validator, MessageSource messageSource) {
        this.cartService = cartService;
        this.priceService = priceService;
        this.phoneService = phoneService;
        this.stockService = stockService;
        this.validator = validator;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String getCart(Model model) {
        addModelAttributes(model);
        return CART_PAGE_NAME;
    }

    @PutMapping
    public String updateCart(HttpServletRequest request, Model model) {
        String[] quantities = request.getParameterValues(HIDDEN_QUANTITY_PARAMETER_NAME);
        String[] phoneIds = request.getParameterValues(HIDDEN_PHONE_ID_PARAMETER_NAME);
        if (quantities != null && phoneIds != null) {
            model.addAttribute(MESSAGES_ATTRIBUTE_NAME, new ArrayList<String>());
            model.addAttribute(QUANTITY_STRINGS_ATTRIBUTE_NAME, new ArrayList<String>());
            Map<Long, Long> cartItemMap = checkQuantityFields(phoneIds, quantities, model);
            cartService.update(cartItemMap);
        }
        addModelAttributes(model);
        return CART_PAGE_NAME;
    }

    @PostMapping
    public String deleteItem(@RequestParam(value = "deleteId") Long deleteId, Model model) {
        cartService.remove(deleteId);
        updateMessagesAndQuantityStringsAfterDelete(model);
        addModelAttributes(model);
        return CART_PAGE_NAME;
    }

    private void addModelAttributes(Model model) {
        addListedCartItemModelAttributes(model);
        model.addAttribute(CART_SIZE_ATTRIBUTE_NAME, cartService.getCartSize());
        model.addAttribute(CART_PRICE_ATTRIBUTE_NAME, priceService.getCartPrice());
    }

    private void addListedCartItemModelAttributes(Model model) {
        List<String> messages = (List<String>) model.asMap().get(MESSAGES_ATTRIBUTE_NAME);
        if (messages == null) {
            model.addAttribute(MESSAGES_ATTRIBUTE_NAME, new ArrayList<String>());
        }
        List<String> quantityStrings = (List<String>) model.asMap().get(QUANTITY_STRINGS_ATTRIBUTE_NAME);
        if (quantityStrings == null) {
            model.addAttribute(QUANTITY_STRINGS_ATTRIBUTE_NAME, new ArrayList<String>());
        }
        List<CartItem> cartItemList = cartService.getCart().getCartItems();
        List<Phone> phones = new ArrayList<>();
        List<Stock> stocks = new ArrayList<>();
        List<Long> phoneIds = new ArrayList<>();
        cartItemList.forEach(cartItem -> {
            phones.add(phoneService.get(cartItem.getPhoneId()).orElse(null));
            stocks.add(stockService.getStock(cartItem.getPhoneId()));
            phoneIds.add(cartItem.getPhoneId());
        });
        model.addAttribute(STOCKS_ATTRIBUTE_NAME, stocks);
        model.addAttribute(PHONE_IDS_ATTRIBUTE_NAME, phoneIds);
        model.addAttribute(PHONES_ATTRIBUTE_NAME, phones);
        model.addAttribute(CART_ITEMS_ATTRIBUTE_NAME, cartItemList);
    }

    private BindingResult validateItemFields(String tempPhoneId, String tempQuantity) {
        StringCartItem stringCartItem = new StringCartItem(tempPhoneId, tempQuantity);
        DataBinder dataBinder = new DataBinder(stringCartItem);
        dataBinder.addValidators(validator);
        dataBinder.validate();
        return dataBinder.getBindingResult();
    }

    private void addLocalizedMessages(Long phoneId, Long quantity, CartItem oldItem, BindingResult result, List<String> messages) {
        if (result.hasErrors()) {
            String error = result.getAllErrors().get(0).getCode();
            messages.add(messageSource.getMessage(error, null, LocaleContextHolder.getLocale()));
        } else {
            if (phoneId.equals(oldItem.getPhoneId()) && quantity.equals(oldItem.getQuantity())) {
                messages.add(EMPTY_MESSAGE);
            } else {
                messages.add(messageSource.getMessage(updateSuccessMessage, null, LocaleContextHolder.getLocale()));
            }
        }
    }

    private void updateMessagesAndQuantityStringsAfterDelete(Model model) {
        List<String> messages = (List<String>) model.asMap().get(MESSAGES_ATTRIBUTE_NAME);
        List<String> quantityStrings = (List<String>) model.asMap().get(QUANTITY_STRINGS_ATTRIBUTE_NAME);
        if (messages != null && !messages.isEmpty()) {
            int newSize = messages.size() - 1;
            messages = new ArrayList<>(newSize);
            messages.replaceAll(s -> "");
            if (quantityStrings != null) {
                quantityStrings.clear();
            } else {
                quantityStrings = new ArrayList<>();
            }
            for (int i = 0; i < messages.size(); i++) {
                quantityStrings.add(cartService.getCart().getCartItems().get(i).getQuantity().toString());
            }
        }
        model.addAttribute(MESSAGES_ATTRIBUTE_NAME, messages);
        model.addAttribute(QUANTITY_STRINGS_ATTRIBUTE_NAME, quantityStrings);
    }

    private Map<Long, Long> checkQuantityFields(String[] phoneIds, String[] quantities, Model model) {
        Map<Long, Long> cartItemMap = new HashMap<>();
        List<String> quantityStrings = new ArrayList<>();
        List<String> messages = new ArrayList<>();
        for (int i = 0; i < quantities.length; i++) {
            CartItem oldItem = cartService.getCart().getCartItems().get(i);
            String tempPhoneId = phoneIds[i];
            String tempQuantity = quantities[i];
            BindingResult result = validateItemFields(tempPhoneId, tempQuantity);
            if (result.hasErrors()) {
                addLocalizedMessages(null, null, null, result, messages);
                cartItemMap.put(oldItem.getPhoneId(), oldItem.getQuantity());
            } else {
                Long phoneId = Long.parseLong(tempPhoneId);
                Long quantity = Long.parseLong(tempQuantity);
                cartItemMap.put(phoneId, quantity);
                addLocalizedMessages(phoneId, quantity, oldItem, result, messages);
            }
            quantityStrings.add(tempQuantity);
        }
        model.addAttribute(QUANTITY_STRINGS_ATTRIBUTE_NAME, quantityStrings);
        model.addAttribute(MESSAGES_ATTRIBUTE_NAME, messages);
        return cartItemMap;
    }
}