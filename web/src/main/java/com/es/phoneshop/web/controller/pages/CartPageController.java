package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import com.es.core.service.CartService;
import com.es.core.service.PhoneService;
import com.es.core.service.PriceService;
import com.es.core.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    private static String CART_PAGE_NAME = "cart";
    private static String CART_ITEMS_ATTRIBUTE_NAME = "cartItems";
    private static String CART_SIZE_ATTRIBUTE_NAME = "cartSize";
    private static String CART_PRICE_ATTRIBUTE_NAME = "cartPrice";
    private static String PHONES_ATTRIBUTE_NAME = "phones";
    private static String STOCKS_ATTRIBUTE_NAME = "stocks";
    private static String BUTTON_DELETE_PARAMETER_NAME = "buttonDelete";
    private final CartService cartService;
    private final PriceService priceService;
    private final PhoneService phoneService;
    private final StockService stockService;

    @Autowired
    public CartPageController(CartService cartService, PriceService priceService, PhoneService phoneService, StockService stockService) {
        this.cartService = cartService;
        this.priceService = priceService;
        this.phoneService = phoneService;
        this.stockService = stockService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        List<CartItem> cartItemList = cartService.getCart().getCartItems();
        List<Phone> phones = new ArrayList<>();
        List<Stock> stocks = new ArrayList<>();
        for(CartItem cartItem : cartItemList) {
            phones.add(phoneService.get(cartItem.getPhoneId()).orElse(null));
            stocks.add(stockService.getStock(cartItem.getPhoneId()));
        }
        model.addAttribute(PHONES_ATTRIBUTE_NAME,phones);
        model.addAttribute(CART_ITEMS_ATTRIBUTE_NAME,cartItemList);
        model.addAttribute(CART_SIZE_ATTRIBUTE_NAME,cartService.getCartSize());
        model.addAttribute(CART_PRICE_ATTRIBUTE_NAME,priceService.getCartPrice());
        model.addAttribute(STOCKS_ATTRIBUTE_NAME,stocks);
        return CART_PAGE_NAME;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateCart() {
        cartService.update(null);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void deleteItem(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String deleteParameter = request.getParameter(BUTTON_DELETE_PARAMETER_NAME);
        Long deleteId;
        try {
            deleteId = Long.parseLong(deleteParameter);
            cartService.remove(deleteId);
        }
        catch (NumberFormatException e) {
            deleteId = null;
        }
        response.sendRedirect(request.getRequestURI());
    }

}
