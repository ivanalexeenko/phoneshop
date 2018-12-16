package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartItem;
import com.es.core.cart.StringifiedCartItem;
import com.es.core.message.ApplicationMessage;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import com.es.core.service.CartService;
import com.es.core.service.PhoneService;
import com.es.core.service.PriceService;
import com.es.core.service.StockService;
import com.es.phoneshop.web.validator.StringifiedCartItemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    private static final String CART_PAGE_NAME = "cart";
    private static final String CART_ITEMS_ATTRIBUTE_NAME = "cartItems";
    private static final String CART_SIZE_ATTRIBUTE_NAME = "cartSize";
    private static final String CART_PRICE_ATTRIBUTE_NAME = "cartPrice";
    private static final String PHONES_ATTRIBUTE_NAME = "phones";
    private static final String STOCKS_ATTRIBUTE_NAME = "stocks";
    private static final String BUTTON_DELETE_PARAMETER_NAME = "buttonDelete";
    private static final String PHONE_IDS_ATTRIBUTE_NAME = "phoneIds";
    private static final String QUANTITY_STRINGS_ATTRIBUTE_NAME = "quantityStrings";
    private static final String MESSAGES_ATTRIBUTE_NAME = "messages";
    private static final String HIDDEN_QUANTITY_PARAMETER_NAME = "hiddenQuantity";
    private static final String HIDDEN_PHONE_ID_PATAMETER_NAME = "hiddenPhoneId";
    private final CartService cartService;
    private final PriceService priceService;
    private final PhoneService phoneService;
    private final StockService stockService;
    private final StringifiedCartItemValidator validator;
    private final MessageSource messageSource;
    private List<String> quantityStrings;
    private List<String> messages;
    private Map<Long,Long> cartItemMap;

    @Autowired
    public CartPageController(CartService cartService, PriceService priceService, PhoneService phoneService, StockService stockService, StringifiedCartItemValidator validator, MessageSource messageSource) {
        this.cartService = cartService;
        this.priceService = priceService;
        this.phoneService = phoneService;
        this.stockService = stockService;
        this.validator = validator;
        this.messageSource = messageSource;
        quantityStrings = new ArrayList<>();
        messages = new ArrayList<>();
        cartItemMap = new HashMap<>();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        setModelAttributes(model);
        return CART_PAGE_NAME;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateCart(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String[] quantities = request.getParameterValues(HIDDEN_QUANTITY_PARAMETER_NAME);
        String[] phoneIds = request.getParameterValues(HIDDEN_PHONE_ID_PATAMETER_NAME);
        if (quantities != null && phoneIds != null) {
            messages.clear();
            quantityStrings.clear();
            cartItemMap.clear();
            for (int i = 0; i < quantities.length; i++) {
                CartItem oldItem = cartService.getCart().getCartItems().get(i);
                cartItemMap.put(oldItem.getPhoneId(),oldItem.getQuantity());

                String tempPhoneId = phoneIds[i];
                String tempQuantity = quantities[i];
                StringifiedCartItem stringifiedCartItem = new StringifiedCartItem(tempPhoneId, tempQuantity);

                final DataBinder dataBinder = new DataBinder(stringifiedCartItem);
                dataBinder.addValidators(validator);
                dataBinder.validate();
                BindingResult result = dataBinder.getBindingResult();
                if(result.hasErrors()) {
                    String error = result.getAllErrors().get(0).getCode();
                    String message = messageSource.getMessage(error,null, LocaleContextHolder.getLocale());
                    messages.add(message);
                }
                else {
                    long phoneId = Long.parseLong(tempPhoneId);
                    long quantity = Long.parseLong(tempQuantity);
                    cartItemMap.put(phoneId,quantity);
                    messages.add(messageSource.getMessage(ApplicationMessage.UPDATE_SUCCESS,null,LocaleContextHolder.getLocale()));
                }
                quantityStrings.add(tempQuantity);
            }
            cartService.update(cartItemMap);
        }
        setModelAttributes(model);
        response.sendRedirect(request.getRequestURI());
    }

    @RequestMapping(method = RequestMethod.POST)
    public void deleteItem(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String deleteParameter = request.getParameter(BUTTON_DELETE_PARAMETER_NAME);
        Long deleteId;
        try {
            deleteId = Long.parseLong(deleteParameter);
            cartService.remove(deleteId);
        } catch (NumberFormatException e) {
            deleteId = null;
        }
        if(!messages.isEmpty()) {
            int newSize = messages.size() - 1;
            messages = new ArrayList<>(newSize);
            messages.replaceAll(s -> "");
            quantityStrings.clear();
            for (int i = 0;i < messages.size();i++) {
                quantityStrings.add(cartService.getCart().getCartItems().get(i).getQuantity().toString());
            }
        }
        setModelAttributes(model);
        response.sendRedirect(request.getRequestURI());
    }

    private void setModelAttributes(Model model) {
        List<CartItem> cartItemList = cartService.getCart().getCartItems();
        List<Phone> phones = new ArrayList<>();
        List<Stock> stocks = new ArrayList<>();
        List<Long> phoneIds = new ArrayList<>();
        for (CartItem cartItem : cartItemList) {
            phones.add(phoneService.get(cartItem.getPhoneId()).orElse(null));
            stocks.add(stockService.getStock(cartItem.getPhoneId()));
            phoneIds.add(cartItem.getPhoneId());
        }
        model.addAttribute(PHONES_ATTRIBUTE_NAME, phones);
        model.addAttribute(CART_ITEMS_ATTRIBUTE_NAME, cartItemList);
        model.addAttribute(CART_SIZE_ATTRIBUTE_NAME, cartService.getCartSize());
        model.addAttribute(CART_PRICE_ATTRIBUTE_NAME, priceService.getCartPrice());
        model.addAttribute(STOCKS_ATTRIBUTE_NAME, stocks);
        model.addAttribute(PHONE_IDS_ATTRIBUTE_NAME, phoneIds);
        model.addAttribute(QUANTITY_STRINGS_ATTRIBUTE_NAME, quantityStrings);
        model.addAttribute(MESSAGES_ATTRIBUTE_NAME,messages);
    }
}
