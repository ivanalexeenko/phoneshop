package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartItem;
import com.es.core.model.order.Order;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import com.es.core.service.*;
import com.es.core.exception.OutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    private static final String ORDER_PAGE_NAME = "order";
    private static final String CART_SIZE_ATTRIBUTE_NAME = "cartSize";
    private static final String CART_PRICE_ATTRIBUTE_NAME = "cartSize";
    private static final String STOCKS_ATTRIBUTE_NAME = "stocks";
    private static final String PHONE_IDS_ATTRIBUTE_NAME = "phoneIds";
    private static final String PHONES_ATTRIBUTE_NAME = "phones";
    private static final String CART_ITEMS_ATTRIBUTE_NAME = "cartItems";
    private static final String MESSAGES_ATTRIBUTE_NAME = "messages";

    private List<String> messages;

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
        messages = new ArrayList<>();
    }



    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model) throws OutOfStockException {
        Order order = orderService.createOrder(cartService.getCart());
        model.addAttribute(CART_SIZE_ATTRIBUTE_NAME,cartService.getCartSize());
        model.addAttribute(CART_PRICE_ATTRIBUTE_NAME,priceService.getCartPrice());

        List<CartItem> cartItemList = cartService.getCart().getCartItems();
        List<Phone> phones = new ArrayList<>();
        List<Stock> stocks = new ArrayList<>();
        List<Long> phoneIds = new ArrayList<>();
        cartItemList.forEach(cartItem -> {
            phones.add(phoneService.get(cartItem.getPhoneId()).orElse(null));
            stocks.add(stockService.getStock(cartItem.getPhoneId()));
            phoneIds.add(cartItem.getPhoneId());
            messages.add("OK");
        });
        model.addAttribute(STOCKS_ATTRIBUTE_NAME, stocks);
        model.addAttribute(PHONE_IDS_ATTRIBUTE_NAME, phoneIds);
        model.addAttribute(PHONES_ATTRIBUTE_NAME, phones);
        model.addAttribute(CART_ITEMS_ATTRIBUTE_NAME, cartItemList);
        model.addAttribute(MESSAGES_ATTRIBUTE_NAME,messages);
        return ORDER_PAGE_NAME;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void placeOrder() throws OutOfStockException {
        orderService.placeOrder(null);
    }
}
