package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartItem;
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
        model.addAttribute("cartSize",cartService.getCartSize());
        model.addAttribute("cartPrice",priceService.getCartPrice());

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
        model.addAttribute("stocks", stocks);
        model.addAttribute("phoneIds", phoneIds);
        model.addAttribute("phones", phones);
        model.addAttribute("cartItems", cartItemList);
        model.addAttribute("messages",messages);
        return ORDER_PAGE_NAME;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void placeOrder() throws OutOfStockException {
        orderService.placeOrder(null);
    }
}
