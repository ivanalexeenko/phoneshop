package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.service.OrderService;
import com.es.core.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin")
public class OrdersPageController {
    private static final String ORDERS_ATTRIBUTE_NAME = "orders";
    private static final String ORDER_ATTRIBUTE_NAME = "order";
    private static final String PHONES_ATTRIBUTE_NAME = "phones";
    private static final String ORDERS_PAGE_NAME = "orders";
    private static final String STATUS_SET_ATTRIBUTE_NAME = "statusSet";
    private static final String NEW_STATUS_PARAM_NAME = "newStatus";
    private static final String DELIVERED_INDEX_ATTRIBUTE_NAME = "deliveredIndex";
    private static final String REJECTED_INDEX_ATTRIBUTE_NAME = "rejectedIndex";
    private static final String IS_LOGIN_ATTRIBUTE_NAME = "isLogin";
    private static final String USERNAME_ATTRIBUTE_NAME = "username";
    private static final Integer DELIVERED_INDEX = 1;
    private static final Integer REJECTED_INDEX = 2;
    private final OrderService orderService;
    private final StockService stockService;

    @Autowired
    public OrdersPageController(OrderService orderService, StockService stockService) {
        this.orderService = orderService;
        this.stockService = stockService;
    }

    @GetMapping("/orders")
    public String showOrders(Model model, Authentication authentication) {
        List<Order> orders = orderService.getOrders();
        model.addAttribute(ORDERS_ATTRIBUTE_NAME, orders);
        setModelAuthenticationAttributes(authentication, model);
        return ORDERS_PAGE_NAME;
    }

    @GetMapping("/orders/{orderId}")
    public String showOrder(Model model, @PathVariable String orderId, Authentication authentication) {
        addModelOrderPhoneAttributes(model, orderId);
        model.addAttribute(DELIVERED_INDEX_ATTRIBUTE_NAME, DELIVERED_INDEX);
        model.addAttribute(REJECTED_INDEX_ATTRIBUTE_NAME, REJECTED_INDEX);
        Optional<Order> optionalOrder = orderService.getOrder(orderId);
        if (optionalOrder.isPresent()) {
            boolean setOrder = !optionalOrder.get().getStatus().equals(OrderStatus.NEW);
            model.addAttribute(STATUS_SET_ATTRIBUTE_NAME, setOrder);
        }
        setModelAuthenticationAttributes(authentication, model);
        return ORDERS_PAGE_NAME;
    }

    @PostMapping("/orders/{orderId}")
    public String setOrderStatus(Model model, @PathVariable String orderId,
                                 @RequestParam(value = NEW_STATUS_PARAM_NAME) Integer newStatusIndex,
                                 Authentication authentication) {
        OrderStatus status = OrderStatus.values()[newStatusIndex];
        stockService.updateStockStatusBased(status, orderId);
        orderService.updateStatus(status, orderId);
        addModelOrderPhoneAttributes(model, orderId);
        model.addAttribute(STATUS_SET_ATTRIBUTE_NAME, true);
        setModelAuthenticationAttributes(authentication, model);
        return ORDERS_PAGE_NAME;
    }

    private void addModelOrderPhoneAttributes(Model model, String orderId) {
        List<Phone> phones = orderService.getPhonesByOrderId(orderId);
        Order order = null;
        Optional<Order> optionalOrder = orderService.getOrder(orderId);
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
            order.setOrderItems(orderService.getOrderItems(orderId));
        }
        model.addAttribute(ORDER_ATTRIBUTE_NAME, order);
        model.asMap().remove(ORDERS_ATTRIBUTE_NAME);
        model.addAttribute(PHONES_ATTRIBUTE_NAME, phones);
    }

    private void setModelAuthenticationAttributes(Authentication authentication, Model model) {
        boolean isLogin;
        if (authentication != null) {
            isLogin = authentication.isAuthenticated();
            model.addAttribute(USERNAME_ATTRIBUTE_NAME, authentication.getName());
        } else {
            isLogin = false;
        }
        model.addAttribute(IS_LOGIN_ATTRIBUTE_NAME, isLogin);
    }
}
