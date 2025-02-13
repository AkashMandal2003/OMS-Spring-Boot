package com.jocata.ordermanagementsystem.services.impl;

import com.jocata.ordermanagementsystem.entities.OrderDetails;
import com.jocata.ordermanagementsystem.services.PaymentService;
import com.jocata.ordermanagementsystem.util.OrderStatus;

import java.util.logging.Logger;

public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger=Logger.getLogger(PaymentServiceImpl.class.getName());

    @Override
    public boolean processPayment(OrderDetails order) {
        logger.info("Processing payment of $" + order.getTotalAmount() + " for Order ID: " + order.getOrderId());
        order.setStatus(OrderStatus.PAID);
        return true;
    }
}
