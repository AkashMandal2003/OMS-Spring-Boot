package com.jocata.ordermanagementsystem.services.impl;

import com.jocata.ordermanagementsystem.entities.OrderDetails;
import com.jocata.ordermanagementsystem.services.PaymentService;
import com.jocata.ordermanagementsystem.util.OrderStatus;

import java.util.logging.Logger;

public class
OrderProcessor extends Thread{

    private static final Logger logger=Logger.getLogger(OrderProcessor.class.getName());

    private OrderDetails order;
    private PaymentService paymentService;

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
            //InMemoryOrders.updateOrderStatus(order.getOrderId(),OrderStatus.SHIPPED);
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
