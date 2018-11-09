package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.es.core.model.phone.PhoneDao;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;


@Controller
@RequestMapping (value = "/productList")
public class ProductListPageController {
    private static final int START_LIMIT = 10;
    private static final int START_OFFSET = 0;
    private static final String PHONES_ATTRIBUTE_NAME = "phones";
    private static final String PRODUCT_LIST_ATTRIBUTE_NAME = "productList";
    @Resource
    private PhoneDao phoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model) {
        model.addAttribute(PHONES_ATTRIBUTE_NAME, phoneDao.findAll(START_OFFSET, START_LIMIT));
        return PRODUCT_LIST_ATTRIBUTE_NAME;
    }
}
