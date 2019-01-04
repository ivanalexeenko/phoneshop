package com.es.phoneshop.web.controller.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {
    private static final String MESSAGE_ATTRIBUTE_NAME = "orderMessage";
    private static final String ORDER_OVERVIEW_PAGE_NAME = "orderOverview";

    @Value("order.id.text")
    private String orderCreatedMessage;

    private final MessageSource messageSource;

    @Autowired
    public OrderOverviewPageController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/{orderId}")
    public String showOrder(Model model, @PathVariable String orderId) {
        model.addAttribute(MESSAGE_ATTRIBUTE_NAME,messageSource.getMessage(orderCreatedMessage,new Object[]{orderId}, LocaleContextHolder.getLocale()));
        return ORDER_OVERVIEW_PAGE_NAME;
    }
}
