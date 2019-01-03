package com.es.phoneshop.web.controller.pages;

import com.es.core.service.CartService;
import com.es.core.service.PhoneService;
import com.es.core.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    private static final String PHONES_ATTRIBUTE_NAME = "phones";
    private static final String PRODUCT_LIST_VIEW_NAME = "productList";
    private static final String CURRENT_PAGE_ATTRIBUTE_NAME = "currentPage";
    private static final String TOTAL_PAGES_ATTRIBUTE_NAME = "totalPages";
    private static final String VISIBLE_PAGES_ATTRIBUTE_NAME = "visiblePages";
    private static final String PHONE_AMOUNT_ATTRIBUTE_NAME = "phoneAmount";
    private static final String SEARCH_FIELD_ATTRIBUTE_NAME = "searchField";
    private static final Integer DEFAULT_CURRENT_PAGE = 1;
    private static final Integer DEFAULT_TOTAL_PAGES = 1;
    private static final String ORDER_BY_ATTRIBUTE_NAME = "orderBy";
    private static final String IS_ASCEND_ATTRIBUTE_NAME = "isAscend";
    private static final String DATA_ATTRIBUTE_NAME = "data";
    private static final String ORDER_BY_ASCEND_PARAMETER = "orderByAscend";
    private static final String ORDER_DATA_STRING_PARAMETER = "orderDataString";
    private static final String REGEX = ",";
    private static final String CART_SIZE = "cartSize";
    private static final String CART_PRICE_ATTRIBUTE_NAME = "cartPrice";
    private Integer currentPage = 1;
    private Integer totalPages;
    private Integer visiblePages = 7;
    private Integer amountPerPage = 20;
    private Integer phoneAmount;
    private String search = "";
    private String orderBy = "brand";
    private Boolean isAscend = true;
    private Integer[] dataArray = {0, 0, 0, 0, 0};
    private List data;
    private final PhoneService phoneService;
    private final CartService cartService;
    private final PriceService priceService;

    @Autowired
    public ProductListPageController(PhoneService phoneService, CartService cartService, PriceService priceService) {
        this.phoneService = phoneService;
        this.cartService = cartService;
        this.priceService = priceService;
    }

    @GetMapping
    public String showProductList(Model model) {
        countPageParameters();
        data = Arrays.asList(dataArray);
        setModelAttributes(model);
        return PRODUCT_LIST_VIEW_NAME;
    }

    @PostMapping
    public String getFormParam(HttpServletRequest request, Model model) {
        String tempCurrentPage = request.getParameter(CURRENT_PAGE_ATTRIBUTE_NAME);
        String tempSearch = request.getParameter(SEARCH_FIELD_ATTRIBUTE_NAME);
        String tempOrder = request.getParameter(ORDER_BY_ATTRIBUTE_NAME);
        String tempAscend = request.getParameter(ORDER_BY_ASCEND_PARAMETER);
        String dataString = request.getParameter(ORDER_DATA_STRING_PARAMETER);
        if (tempCurrentPage != null) {
            currentPage = Integer.valueOf(tempCurrentPage);
        } else if (tempSearch != null) {
            search = tempSearch;
            currentPage = DEFAULT_CURRENT_PAGE;
            countPageParameters();
        } else {
            if (tempOrder != null) {
                orderBy = tempOrder;
                isAscend = Boolean.valueOf(tempAscend);
            }
        }
        data = recalculateData(dataString);
        dataArray = (Integer[]) data.toArray();
        setModelAttributes(model);
        return PRODUCT_LIST_VIEW_NAME;
    }

    private List recalculateData(String dataString) {
        Integer[] tempArray;
        try {
            if (!StringUtils.isEmpty(dataString)) {
                String[] dataStringArray = dataString.split(REGEX);
                tempArray = new Integer[dataStringArray.length];
                for (int i = 0; i < dataStringArray.length; i++) {
                    tempArray[i] = Integer.valueOf(dataStringArray[i]);
                }
            } else return data;
        } catch (NumberFormatException exception) {
            return data;
        }
        return Arrays.asList(tempArray);
    }

    private void setModelAttributes(Model model) {
        model.addAttribute(PHONES_ATTRIBUTE_NAME, phoneService.findAll(amountPerPage * (currentPage - 1), amountPerPage, search, orderBy, isAscend));
        model.addAttribute(TOTAL_PAGES_ATTRIBUTE_NAME, totalPages);
        model.addAttribute(VISIBLE_PAGES_ATTRIBUTE_NAME, visiblePages);
        model.addAttribute(CURRENT_PAGE_ATTRIBUTE_NAME, currentPage);
        model.addAttribute(PHONE_AMOUNT_ATTRIBUTE_NAME, phoneAmount);
        model.addAttribute(SEARCH_FIELD_ATTRIBUTE_NAME, search);
        model.addAttribute(ORDER_BY_ATTRIBUTE_NAME, orderBy);
        model.addAttribute(IS_ASCEND_ATTRIBUTE_NAME, isAscend);
        model.addAttribute(DATA_ATTRIBUTE_NAME, data);
        model.addAttribute(CART_SIZE, cartService.getCartSize());
        model.addAttribute(CART_PRICE_ATTRIBUTE_NAME, priceService.getCartPrice());
    }

    private void countPageParameters() {
        phoneAmount = phoneService.countPhonesStockBiggerZero(search);
        totalPages = (int) Math.ceil(phoneAmount.doubleValue() / amountPerPage.doubleValue());
        if (totalPages == 0) {
            totalPages = DEFAULT_TOTAL_PAGES;
            currentPage = DEFAULT_CURRENT_PAGE;
        }
    }
}
