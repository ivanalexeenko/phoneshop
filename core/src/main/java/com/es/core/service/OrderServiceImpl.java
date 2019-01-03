package com.es.core.service;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.exception.OutOfStockException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final PhoneService phoneService;
    private final StockService stockService;
    private final PriceService priceService;
    private final MessageSource messageSource;

    @Value("exception.out.of.stock")
    private String outOfStockMessage;

    @Value("quantity.ok.message")
    private String quantityOkMessage;

    @Value("5")
    private BigDecimal deliveryPrice;

    @Autowired
    public OrderServiceImpl(PhoneService phoneService, StockService stockService, PriceService priceService, MessageSource messageSource) {
        this.phoneService = phoneService;
        this.stockService = stockService;
        this.priceService = priceService;
        this.messageSource = messageSource;
    }

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        cart.getCartItems().forEach(cartItem -> {
            Optional<Phone> optionalPhone = phoneService.get(cartItem.getPhoneId());
            if(optionalPhone.isPresent()) {
                Phone phone = optionalPhone.get();
                Long id = cartItem.getPhoneId();
                Pair quantityMessagePair = getLocalizedFinalQuantityAndMessage(cartItem);
                Long quantity = Long.valueOf(quantityMessagePair.getKey().toString());
                String message = quantityMessagePair.getValue().toString();
                OrderItem orderItem = createOrderItem(phone,id,quantity,message);
                orderItems.add(orderItem);
            }
        });
        order.setSubtotal(priceService.countOrderSubtotalPrice(orderItems));
        order.setTotalPrice(getTotalOrderPrice(order));
        order.setDeliveryPrice(deliveryPrice);
        order.setOrderItems(orderItems);
        return order;
    }

    private OrderItem createOrderItem(Phone phone,Long id,Long quantity,String message) {
        OrderItem orderItem = new OrderItem();
        orderItem.setMessage(message);
        orderItem.setQuantity(quantity);
        orderItem.setPhone(phone);
        orderItem.setId(id);
        return orderItem;
    }

    private Pair<Long,String> getLocalizedFinalQuantityAndMessage(CartItem cartItem) {
        Stock stock = stockService.getStock(cartItem.getPhoneId());
        Long finalQuantity;
        String message;
        try {
            finalQuantity = checkStockQuantityCoherence(stock,cartItem);
            message = messageSource.getMessage(quantityOkMessage,null, LocaleContextHolder.getLocale());
        } catch (OutOfStockException e) {
            finalQuantity = Long.valueOf(stock.getStock());
            message = messageSource.getMessage(outOfStockMessage,new Object[]{cartItem.getQuantity(),stock.getStock()},LocaleContextHolder.getLocale());
        }
        return new Pair<>(finalQuantity,message);
    }

    private Long checkStockQuantityCoherence(Stock stock,CartItem cartItem) throws OutOfStockException {
        if(stock.getStock() < cartItem.getQuantity()) {
            throw new OutOfStockException(outOfStockMessage);
        }
        return cartItem.getQuantity();
    }

    @Override
    public void placeOrder(Order order)  {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public BigDecimal getTotalOrderPrice(Order order) {
        BigDecimal subtotal = order.getSubtotal();
        return subtotal.add(deliveryPrice);
    }

    @Override
    public List<String> getOrderMessages(Order order) {
        return order.getOrderItems().stream().map(OrderItem::getMessage).collect(Collectors.toList());
    }
}
