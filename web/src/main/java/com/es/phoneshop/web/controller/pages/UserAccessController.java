package com.es.phoneshop.web.controller.pages;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserAccessController {
    private static final String ERROR_PARAM_NAME = "error";
    private static final String LOGOUT_PARAM_NAME = "logout";
    private static final String IS_ERROR_ATTRIBUTE_NAME = "isError";
    private static final String IS_LOGOUT_ATTRIBUTE_NAME = "isLogout";
    private static final String IS_LOGIN_ATTRIBUTE_NAME = "isLogin";
    private static final String USERNAME_ATTRIBUTE_NAME = "username";
    private static final String LOGIN_PAGE_NAME = "login";

    @GetMapping(value = "/login")
    public String showLoginPage(Model model, @RequestParam(value = ERROR_PARAM_NAME, required = false) Boolean isError,
                                @RequestParam(value = LOGOUT_PARAM_NAME, required = false) Boolean isLogout, Authentication authentication) {
        model.addAttribute(IS_ERROR_ATTRIBUTE_NAME, isError);
        model.addAttribute(IS_LOGOUT_ATTRIBUTE_NAME, isLogout);
        boolean isLogin;
        if (authentication != null) {
            isLogin = authentication.isAuthenticated();
            model.addAttribute(USERNAME_ATTRIBUTE_NAME, authentication.getName());
        } else {
            isLogin = false;
        }
        model.addAttribute(IS_LOGIN_ATTRIBUTE_NAME, isLogin);
        return LOGIN_PAGE_NAME;
    }
}