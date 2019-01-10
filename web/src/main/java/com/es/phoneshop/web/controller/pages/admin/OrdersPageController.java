package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.service.OrderService;
import com.es.core.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final int DELIVERED_INDEX = 0;
    private static final int REJECTED_INDEX = 1;
    private final OrderService orderService;
    private final StatusService statusService;

    @Autowired
    public OrdersPageController(OrderService orderService, StatusService statusService) {
        this.orderService = orderService;
        this.statusService = statusService;
    }

    @GetMapping("/orders")
    public String showOrders(Model model) {
        List<Order> orders = orderService.getOrders();
        model.addAttribute(ORDERS_ATTRIBUTE_NAME, orders);
        return ORDERS_PAGE_NAME;
    }

    @GetMapping("/orders/{orderId}")
    public String showOrder(Model model, @PathVariable String orderId) {
        addModelOrderPhoneAttributes(model, orderId);
        Optional<Order> optionalOrder = orderService.getOrder(orderId);
        if (optionalOrder.isPresent()) {
            boolean setOrder = !optionalOrder.get().getStatus().equals(OrderStatus.NEW);
            model.addAttribute(STATUS_SET_ATTRIBUTE_NAME, setOrder);
        }
        return ORDERS_PAGE_NAME;
    }

    @PostMapping("/orders/{orderId}")
    public String setOrderStatus(Model model, @PathVariable String orderId,
                                 @RequestParam(value = NEW_STATUS_PARAM_NAME) Integer newStatus) {
        if (newStatus == DELIVERED_INDEX) {
            statusService.updateStatus(OrderStatus.DELIVERED, orderId);
        } else if (newStatus == REJECTED_INDEX) {
            statusService.updateStatus(OrderStatus.REJECTED, orderId);
        }
        addModelOrderPhoneAttributes(model, orderId);
        model.addAttribute(STATUS_SET_ATTRIBUTE_NAME, true);
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
}
