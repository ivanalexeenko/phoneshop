package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import com.es.core.service.*;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    private static final String ORDER_PAGE_NAME = "order";
    private static final String ORDER_OVERVIEW_PAGE_NAME = "orderOverview/";
    private static final String CART_SIZE_ATTRIBUTE_NAME = "cartSize";
    private static final String CART_PRICE_ATTRIBUTE_NAME = "cartPrice";
    private static final String STOCKS_ATTRIBUTE_NAME = "stocks";
    private static final String PHONE_IDS_ATTRIBUTE_NAME = "phoneIds";
    private static final String PHONES_ATTRIBUTE_NAME = "phones";
    private static final String CART_ITEMS_ATTRIBUTE_NAME = "cartItems";
    private static final String MESSAGES_ATTRIBUTE_NAME = "messages";
    private static final String DELIVERY_ATTRIBUTE_NAME = "deliveryPrice";
    private static final String SUBTOTAL_ATTRIBUTE_NAME = "subtotalPrice";
    private static final String TOTAL_ATTRIBUTE_NAME = "totalPrice";
    private static final String INPUT_MESSAGES_ATTRIBUTE_NAME = "inputMessages";
    private static final String ORDER_INPUTS_ATTRIBUTE_NAME = "orderInputs";
    private static final String ORDER_INPUT_PARAMETER_NAME = "orderInput";
    private static final int FIRST_NAME_INDEX = 0;
    private static final int LAST_NAME_INDEX = 1;
    private static final int ADDRESS_INDEX = 2;
    private static final int PHONE_INDEX = 3;
    private static final int DESC_INDEX = 4;
    private final OrderService orderService;
    private final CartService cartService;
    private final PriceService priceService;
    private final PhoneService phoneService;
    private final StockService stockService;
    private final MessageSource messageSource;

    @Value("field.required")
    private String requiredFieldMessage;

    @Autowired
    public OrderPageController(OrderService orderService, CartService cartService, PriceService priceService, PhoneService phoneService, StockService stockService, MessageSource messageSource) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.priceService = priceService;
        this.phoneService = phoneService;
        this.stockService = stockService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String getOrder(Model model) {
        Order order = orderService.createOrder(cartService.getCart());
        addModelAttributes(model, order);
        return ORDER_PAGE_NAME;
    }

    @PostMapping
    public String placeOrder(@RequestParam(value = ORDER_INPUT_PARAMETER_NAME) List<String> orderInputs, Model model) {
        Pair<List<String>, Boolean> inputPair = addAndGetInputMessages(orderInputs);
        List<String> inputMessages = inputPair.getKey();
        Boolean hasErrors = inputPair.getValue();
        Order order = orderService.createOrder(cartService.getCart());
        setOrderInputFields(order, orderInputs);
        addModelAttributes(model, order);
        model.addAttribute(INPUT_MESSAGES_ATTRIBUTE_NAME, inputMessages);
        model.addAttribute(ORDER_INPUTS_ATTRIBUTE_NAME, orderInputs);
        if (hasErrors) {
            return ORDER_PAGE_NAME;
        }
        model.asMap().clear();
        orderService.placeOrder(order);
        stockService.updateStock(order);
        String redirect = ORDER_OVERVIEW_PAGE_NAME + order.getId();
        return "redirect:/" + redirect;
    }

    private void addModelAttributes(Model model, Order order) {
        List<Phone> phones = new ArrayList<>();
        List<Stock> stocks = new ArrayList<>();
        List<Long> phoneIds = new ArrayList<>();
        cartService.getCart().getCartItems().forEach(cartItem -> {
            phones.add(phoneService.get(cartItem.getPhoneId()).orElse(null));
            stocks.add(stockService.getStock(cartItem.getPhoneId()));
            phoneIds.add(cartItem.getPhoneId());
        });
        model.addAttribute(CART_SIZE_ATTRIBUTE_NAME, cartService.getCartSize());
        model.addAttribute(CART_PRICE_ATTRIBUTE_NAME, priceService.getCartPrice());
        model.addAttribute(STOCKS_ATTRIBUTE_NAME, stocks);
        model.addAttribute(PHONE_IDS_ATTRIBUTE_NAME, phoneIds);
        model.addAttribute(PHONES_ATTRIBUTE_NAME, phones);
        model.addAttribute(CART_ITEMS_ATTRIBUTE_NAME, cartService.getCart().getCartItems());
        model.addAttribute(MESSAGES_ATTRIBUTE_NAME, orderService.getOrderMessages(order));
        model.addAttribute(DELIVERY_ATTRIBUTE_NAME, order.getDeliveryPrice());
        model.addAttribute(SUBTOTAL_ATTRIBUTE_NAME, order.getSubtotal());
        model.addAttribute(TOTAL_ATTRIBUTE_NAME, order.getTotalPrice());
        if (!model.containsAttribute(INPUT_MESSAGES_ATTRIBUTE_NAME) || !model.containsAttribute(ORDER_INPUTS_ATTRIBUTE_NAME)) {
            model.addAttribute(INPUT_MESSAGES_ATTRIBUTE_NAME, new ArrayList<>(cartService.getCartSize()));
            model.addAttribute(ORDER_INPUTS_ATTRIBUTE_NAME, new ArrayList<>(cartService.getCartSize()));
        }
    }

    private Pair<List<String>, Boolean> addAndGetInputMessages(List<String> orderInputs) {
        Boolean[] hasErrors = new Boolean[1];
        hasErrors[0] = false;
        final int indices[] = new int[1];
        List<String> inputMessages = new ArrayList<>();
        orderInputs.forEach(input -> {
            if (indices[0] != DESC_INDEX && StringUtils.isEmpty(input)) {
                inputMessages.add(messageSource.getMessage(requiredFieldMessage, null, LocaleContextHolder.getLocale()));
                hasErrors[0] = true;
            } else {
                inputMessages.add("");
            }
            indices[0]++;
        });
        return new Pair<>(inputMessages, hasErrors[0]);
    }

    private void setOrderInputFields(Order order, List<String> orderInputs) {
        String firstName = orderInputs.get(FIRST_NAME_INDEX);
        String lastName = orderInputs.get(LAST_NAME_INDEX);
        String address = orderInputs.get(ADDRESS_INDEX);
        String phone = orderInputs.get(PHONE_INDEX);
        String desc = orderInputs.get(DESC_INDEX);
        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setDeliveryAddress(address);
        order.setContactPhoneNo(phone);
        order.setDescription(desc);
    }
}