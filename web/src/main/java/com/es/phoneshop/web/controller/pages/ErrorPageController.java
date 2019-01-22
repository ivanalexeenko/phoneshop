package com.es.phoneshop.web.controller.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorPageController {
    private static final String ERROR_PAGE_NAME = "error";
    private static final String MESSAGE_CODE_ATTRIBUTE_NAME = "code";
    private static final String MESSAGE_ATTRIBUTE_NAME = "message";
    private static final String REQUEST_ERROR_CODE_ATTRIBUTE_NAME = "javax.servlet.error.status_code";
    private static final String MESSAGE_404_CODE = "error.message.404";
    private static final String MESSAGE_500_CODE = "error.message.500";
    private static final String MESSAGE_DEFAULT_CODE = "error.message.default";
    private final MessageSource messageSource;

    @Autowired
    public ErrorPageController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping(value = ERROR_PAGE_NAME)
    public String handleErrorPageCodes(Model model, HttpServletRequest request) {
        Integer code = (Integer) request.getAttribute(REQUEST_ERROR_CODE_ATTRIBUTE_NAME);
        String message;
        switch (code) {
            case 404: {
                message = messageSource.getMessage(MESSAGE_404_CODE, null, LocaleContextHolder.getLocale());
                break;
            }
            case 500: {
                message = messageSource.getMessage(MESSAGE_500_CODE, null, LocaleContextHolder.getLocale());
                break;
            }
            default: {
                message = messageSource.getMessage(MESSAGE_DEFAULT_CODE, null, LocaleContextHolder.getLocale());
                break;
            }
        }

        model.addAttribute(MESSAGE_ATTRIBUTE_NAME, message);
        model.addAttribute(MESSAGE_CODE_ATTRIBUTE_NAME, code);
        return ERROR_PAGE_NAME;
    }
}
