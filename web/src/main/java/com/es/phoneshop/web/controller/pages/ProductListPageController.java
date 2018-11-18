package com.es.phoneshop.web.controller.pages;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.es.core.model.phone.PhoneDao;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    private static final String PHONES_ATTRIBUTE_NAME = "phones";
    private static final String PRODUCT_LIST_ATTRIBUTE_NAME = "productList";
    private static final String CURRENT_PAGE_ATTRIBUTE_NAME = "currentPage";
    private static final String TOTAL_PAGES_ATTRIBUTE_NAME = "totalPages";
    private static final String VISIBLE_PAGES_ATTRIBUTE_NAME = "visiblePages";
    private static final String AMOUNT_PER_PAGE_ATTRIBUTE_NAME = "amountPerPage";
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

    @Resource
    private PhoneDao phoneDao;

    @PostConstruct
    public void init() {
        countPageParameters();
        data = Arrays.asList(dataArray);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(HttpSession session) {
        setSessionAttributes(session);
        return PRODUCT_LIST_ATTRIBUTE_NAME;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void getFormParam(HttpServletRequest request, HttpServletResponse response) throws IOException {

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
        setSessionAttributes(request.getSession());
        response.sendRedirect(request.getRequestURI());
    }

    private List recalculateData(String dataString) {
        Integer[] tempArray;
        try {
            if (dataString != null && !dataString.isEmpty()) {
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

    private void setSessionAttributes(HttpSession session) {
        session.setAttribute(PHONES_ATTRIBUTE_NAME, phoneDao.findAll(amountPerPage * (currentPage - 1), amountPerPage, search, orderBy, isAscend));
        session.setAttribute(TOTAL_PAGES_ATTRIBUTE_NAME, totalPages);
        session.setAttribute(VISIBLE_PAGES_ATTRIBUTE_NAME, visiblePages);
        session.setAttribute(CURRENT_PAGE_ATTRIBUTE_NAME, currentPage);
        session.setAttribute(PHONE_AMOUNT_ATTRIBUTE_NAME, phoneAmount);
        session.setAttribute(SEARCH_FIELD_ATTRIBUTE_NAME, search);
        session.setAttribute(ORDER_BY_ATTRIBUTE_NAME, orderBy);
        session.setAttribute(IS_ASCEND_ATTRIBUTE_NAME, isAscend);
        session.setAttribute(DATA_ATTRIBUTE_NAME, data);
    }

    private void countPageParameters() {
        phoneAmount = phoneDao.countStocks(search);
        totalPages = (int) Math.ceil(phoneAmount.doubleValue() / amountPerPage.doubleValue());
        if (totalPages == 0) {
            totalPages = DEFAULT_TOTAL_PAGES;
            currentPage = DEFAULT_CURRENT_PAGE;
        }
    }
}
