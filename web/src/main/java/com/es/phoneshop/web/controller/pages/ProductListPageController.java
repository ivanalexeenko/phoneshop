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
    @Resource
    private PhoneDao phoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        model.addAttribute("phones", phoneDao.findAll(0, 10));
        return "productList";
    }
}
