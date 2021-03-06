package com.es.core.service;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.dao.OrderDao;
import com.es.core.exception.OutOfStockException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final StockService stockService;
    private final PriceService priceService;
    private final PhoneService phoneService;
    private final MessageSource messageSource;
    private final CartService cartService;
    private final OrderDao orderDao;

    @Value("exception.out.of.stock")
    private String outOfStockMessage;

    @Value("quantity.ok.message")
    private String quantityOkMessage;

    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;

    @Autowired
    public OrderServiceImpl(StockService stockService, PriceService priceService, PhoneService phoneService, MessageSource messageSource, CartService cartService, OrderDao orderDao) {
        this.stockService = stockService;
        this.priceService = priceService;
        this.phoneService = phoneService;
        this.messageSource = messageSource;
        this.cartService = cartService;
        this.orderDao = orderDao;
    }

    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        cart.getCartItems().forEach(cartItem -> {
            String message = getLocalizedMessage(cartItem);
            Long quantity = cartItem.getQuantity();
            OrderItem orderItem = createOrderItem(cartItem.getPhoneId(), quantity, message);
            orderItems.add(orderItem);
        });
        order.setSubtotal(priceService.countOrderSubtotalPrice(orderItems));
        order.setTotalPrice(getTotalOrderPrice(order));
        order.setDeliveryPrice(deliveryPrice);
        order.setOrderItems(orderItems);
        order.setStatus(OrderStatus.NEW);
        order.setId(UUID.randomUUID().toString());
        setOrderItemsId(order, order.getId());
        return order;
    }

    private OrderItem createOrderItem(Long phoneId, Long quantity, String message) {
        OrderItem orderItem = new OrderItem();
        orderItem.setMessage(message);
        orderItem.setQuantity(quantity);
        orderItem.setPhoneId(phoneId);
        return orderItem;
    }

    private String getLocalizedMessage(CartItem cartItem) {
        Stock stock = stockService.getStock(cartItem.getPhoneId());
        Long finalQuantity;
        String message;
        try {
            finalQuantity = checkStockQuantityCoherence(stock, cartItem);
            message = messageSource.getMessage(quantityOkMessage, null, LocaleContextHolder.getLocale());
        } catch (OutOfStockException e) {
            finalQuantity = Long.valueOf(stock.getStock());
            message = messageSource.getMessage(outOfStockMessage, new Object[]{cartItem.getQuantity(), stock.getStock()}, LocaleContextHolder.getLocale());
        }
        cartItem.setQuantity(finalQuantity);
        return message;
    }

    private Long checkStockQuantityCoherence(Stock stock, CartItem cartItem) throws OutOfStockException {
        if (stock.getStock() < cartItem.getQuantity()) {
            throw new OutOfStockException(outOfStockMessage);
        }
        return cartItem.getQuantity();
    }

    @Override
    public void placeOrder(Order order) {
        cartService.getCart().getCartItems().clear();
        orderDao.placeOrder(order);
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

    @Override
    public void setOrderItemsId(Order order, String id) {
        order.getOrderItems().forEach(orderItem -> orderItem.setId(id));
    }

    @Override
    public List<OrderItem> getOrderItems(String orderId) {
        return orderDao.getOrderItems(orderId);
    }

    @Override
    public Optional<Order> getOrder(String id) {
        return orderDao.getOrder(id);
    }

    @Override
    public List<Order> getOrders() {
        return orderDao.getOrders();
    }

    @Override
    public List<Phone> getPhonesByOrderId(String id) {
        Optional<Order> optionalOrder = this.getOrder(id);
        Order order = null;
        List<Phone> phones = new ArrayList<>();
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
            List<OrderItem> itemList = this.getOrderItems(id);
            order.setOrderItems(itemList);
            phones.addAll(order.getOrderItems().stream().map(orderItem -> {
                Optional<Phone> optionalPhone = phoneService.get(orderItem.getPhoneId());
                return optionalPhone.get();
            }).collect(Collectors.toList()));
        }
        phones = phones.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return phones;
    }

    @Override
    public void updateStatus(OrderStatus status, String orderId) {
        orderDao.updateStatusWithId(status, orderId);
    }
}