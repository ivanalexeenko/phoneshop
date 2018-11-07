package com.es.phoneshop.web.controller.pages;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.es.core.model.phone.PhoneDao;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import static com.es.phoneshop.web.helping.ConstantsWeb.*;

@Controller
@RequestMapping (value = PLP_REQUEST_MAPPING_VALUE)
public class ProductListPageController {
    @Resource
    private PhoneDao phoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        model.addAttribute(PHONES_ATTRIBUTE_NAME, phoneDao.findAll(START_OFFSET, START_LIMIT));
        return PRODUCT_LIST_ATTRIBUTE_NAME;
    }
}
