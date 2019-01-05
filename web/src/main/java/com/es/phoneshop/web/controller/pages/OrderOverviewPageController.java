package com.es.phoneshop.web.controller.pages;

import com.es.core.dao.OrderDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.service.OrderService;
import com.es.core.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {
    private static final String MESSAGE_ATTRIBUTE_NAME = "orderMessage";
    private static final String ORDER_OVERVIEW_PAGE_NAME = "orderOverview";
    private static final String ORDER_ATTRIBUTE_NAME = "order";
    private static final String PHONES_ATTRIBUTE_NAME = "phones";

    @Value("order.id.text")
    private String orderCreatedMessage;

    private final OrderService orderService;
    private final PhoneService phoneService;
    private final MessageSource messageSource;

    @Autowired
    public OrderOverviewPageController(MessageSource messageSource, OrderService orderService, PhoneService phoneService) {
        this.orderService = orderService;
        this.phoneService = phoneService;
        this.messageSource = messageSource;
    }

    @GetMapping("/{orderId}")
    public String showOrder(Model model, @PathVariable String orderId) {
        model.addAttribute(MESSAGE_ATTRIBUTE_NAME, messageSource.getMessage(orderCreatedMessage, new Object[]{orderId}, LocaleContextHolder.getLocale()));
        Optional<Order> optionalOrder = orderService.getOrder(orderId);
        Order order = null;
        List<Phone> phones = new ArrayList<>();
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
            List<OrderItem> itemList = orderService.getOrderItems(orderId);
            order.setOrderItems(itemList);
            phones.addAll(order.getOrderItems().stream().map(orderItem -> {
                Optional<Phone> optionalPhone = phoneService.get(orderItem.getPhoneId());
                return optionalPhone.get();
            }).collect(Collectors.toList()));
        }
        phones = phones.stream().filter(Objects::nonNull).collect(Collectors.toList());
        model.addAttribute(ORDER_ATTRIBUTE_NAME, order);
        model.addAttribute(PHONES_ATTRIBUTE_NAME, phones);
        return ORDER_OVERVIEW_PAGE_NAME;
    }
}