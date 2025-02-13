package com.jocata.ordermanagementsystem.services.impl;

import com.jocata.ordermanagementsystem.daos.OrderDao;
import com.jocata.ordermanagementsystem.entities.OrderDetails;
import com.jocata.ordermanagementsystem.services.PaymentService;
import com.jocata.ordermanagementsystem.util.OrderStatus;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class OrderProcessor extends Thread{

    private static final Logger logger=Logger.getLogger(OrderProcessor.class.getName());

    private final OrderDao orderDao;

    private OrderDetails order;
    private PaymentService paymentService;

    public OrderProcessor(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void run() {
        if (order == null || paymentService == null) {
            logger.info("Order or PaymentService is not set. Cannot process order.");
            return;
        }

        logger.info("Processing Order ID: " + order.getOrderId());
        boolean paymentSuccessful = paymentService.processPayment(order);

        if (paymentSuccessful) {
            logger.info("Order ID: " + order.getOrderId() + " has been paid.");
            order.setStatus(OrderStatus.SHIPPED);
            orderDao.updateOrder(order);
        } else {
            logger.info("Payment failed for Order ID: " + order.getOrderId());
        }
    }

    public void setOrder(OrderDetails order) {
        this.order = order;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
