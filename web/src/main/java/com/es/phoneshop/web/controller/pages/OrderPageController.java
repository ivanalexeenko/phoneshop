package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import com.es.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    private static final String ORDER_PAGE_NAME = "order";
    private static final String ORDER_OVERVIEW_PAGE_NAME = "orderOverview";
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
    private final OrderService orderService;
    private final CartService cartService;
    private final PriceService priceService;
    private final PhoneService phoneService;
    private final StockService stockService;

    @Autowired
    public OrderPageController(OrderService orderService, CartService cartService, PriceService priceService, PhoneService phoneService, StockService stockService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.priceService = priceService;
        this.phoneService = phoneService;
        this.stockService = stockService;
    }

    @GetMapping
    public String getOrder(Model model) {
        Order order = orderService.createOrder(cartService.getCart());
        List<Phone> phones = new ArrayList<>();
        List<Stock> stocks = new ArrayList<>();
        List<Long> phoneIds = new ArrayList<>();
        cartService.getCart().getCartItems().forEach(cartItem -> {
            phones.add(phoneService.get(cartItem.getPhoneId()).orElse(null));
            stocks.add(stockService.getStock(cartItem.getPhoneId()));
            phoneIds.add(cartItem.getPhoneId());
        });
        addModelAttributes(model, stocks, phones, phoneIds, order);
        return ORDER_PAGE_NAME;
    }

    private void addModelAttributes(Model model, List<Stock> stocks, List<Phone> phones, List<Long> phoneIds, Order order) {
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
    }

    @PostMapping
    public void placeOrder() {
        orderService.placeOrder(null);
    }
}
