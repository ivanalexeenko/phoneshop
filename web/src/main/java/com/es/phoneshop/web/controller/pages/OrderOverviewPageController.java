package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import com.es.core.model.phone.Phone;
import com.es.core.service.CartService;
import com.es.core.service.OrderService;
import com.es.core.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {
    private static final String MESSAGE_ATTRIBUTE_NAME = "orderMessage";
    private static final String ORDER_OVERVIEW_PAGE_NAME = "orderOverview";
    private static final String ORDER_ATTRIBUTE_NAME = "order";
    private static final String PHONES_ATTRIBUTE_NAME = "phones";
    private static final String CART_PRICE_ATTRIBUTE_NAME = "cartPrice";
    private static final String CART_SIZE_ATTRIBUTE_NAME = "cartSize";

    @Value("order.id.text")
    private String orderCreatedMessage;

    private final OrderService orderService;
    private final MessageSource messageSource;
    private final CartService cartService;
    private final PriceService priceService;

    @Autowired
    public OrderOverviewPageController(MessageSource messageSource, OrderService orderService, CartService cartService, PriceService priceService) {
        this.orderService = orderService;
        this.messageSource = messageSource;
        this.cartService = cartService;
        this.priceService = priceService;
    }

    @GetMapping("/{orderId}")
    public String showOrder(Model model, @PathVariable String orderId) {
        model.addAttribute(MESSAGE_ATTRIBUTE_NAME, messageSource.getMessage(orderCreatedMessage, new Object[]{orderId}, LocaleContextHolder.getLocale()));
        List<Phone> phones = orderService.getPhonesByOrderId(orderId);
        Order order = null;
        Optional<Order> optionalOrder = orderService.getOrder(orderId);
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
            order.setOrderItems(orderService.getOrderItems(orderId));
        }
        model.addAttribute(ORDER_ATTRIBUTE_NAME, order);
        model.addAttribute(PHONES_ATTRIBUTE_NAME, phones);
        model.addAttribute(CART_SIZE_ATTRIBUTE_NAME, cartService.getCartSize());
        model.addAttribute(CART_PRICE_ATTRIBUTE_NAME, priceService.getCartPrice());
        return ORDER_OVERVIEW_PAGE_NAME;
    }
}