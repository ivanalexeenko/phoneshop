package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartItem;
import com.es.core.model.comment.Comment;
import com.es.core.model.comment.CommentStatus;
import com.es.core.model.phone.Phone;
import com.es.core.service.CartService;
import com.es.core.service.CommentService;
import com.es.core.service.PhoneService;
import com.es.core.service.PriceService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {
    private static final String PRODUCT_DETAILS_VIEW_NAME = "productDetails";
    private static final String PHONE_ATTRIBUTE_NAME = "phone";
    private static final String CART_ITEM_ATTRIBUTE_NAME = "cartItem";
    private static final String CART_SIZE_ATTRIBUTE_NAME = "cartSize";
    private static final String CART_PRICE_ATTRIBUTE_NAME = "cartPrice";
    private static final String COMMENTS_ATTRIBUTE_NAME = "comments";
    private static final String COMMENT_INPUTS_ATTRIBUTE_NAME = "commentInput";
    private static final String INPUT_MESSAGES_ATTRIBUTE_NAME = "inputMessages";
    private final PhoneService phoneService;
    private final CartService cartService;
    private final PriceService priceService;
    private final CommentService commentService;

    @Autowired
    public ProductDetailsPageController(PhoneService phoneService, CartService cartService, PriceService priceService, CommentService commentService) {
        this.phoneService = phoneService;
        this.cartService = cartService;
        this.priceService = priceService;
        this.commentService = commentService;
    }

    @GetMapping(value = "/{phoneId}")
    public String showProductList(@PathVariable Long phoneId, Model model) {
        addModelAttributes(model, phoneId);
        addModelCommentsAttribute(model, phoneId);
        return PRODUCT_DETAILS_VIEW_NAME;
    }

    @PostMapping(value = "/{phoneId}")
    public String addComment(@PathVariable Long phoneId, @RequestParam(value = COMMENT_INPUTS_ATTRIBUTE_NAME) List<String> commentInputs, Model model) {
        String name = commentInputs.get(0);
        Integer rating = Integer.valueOf(commentInputs.get(1));
        String comment = commentInputs.get(2);
        Comment commentItem = new Comment();
        commentItem.setName(name);
        commentItem.setRating(rating);
        commentItem.setPhoneId(phoneId);
        commentItem.setComment(comment);
        commentItem.setStatus(CommentStatus.NEW);
        commentService.addComment(commentItem);
        addModelAttributes(model, phoneId);
        addModelCommentsAttribute(model, phoneId);
        return PRODUCT_DETAILS_VIEW_NAME;
    }

    private void addModelAttributes(Model model, Long phoneId) {
        Optional<Phone> optionalPhone = phoneService.get(phoneId);
        Optional<CartItem> optionalCartItem = cartService.getCartItem(phoneId);
        model.addAttribute(PHONE_ATTRIBUTE_NAME, optionalPhone.orElse(null));
        model.addAttribute(CART_ITEM_ATTRIBUTE_NAME, optionalCartItem.orElse(null));
        model.addAttribute(CART_SIZE_ATTRIBUTE_NAME, cartService.getCartSize());
        model.addAttribute(CART_PRICE_ATTRIBUTE_NAME, priceService.getCartPrice());
        addModelCommentsAttribute(model, phoneId);
    }

    private void addModelCommentsAttribute(Model model, Long phoneId) {
        List<Comment> comments = commentService.getComments(phoneId);
        model.addAttribute(COMMENTS_ATTRIBUTE_NAME, comments);
    }
}
