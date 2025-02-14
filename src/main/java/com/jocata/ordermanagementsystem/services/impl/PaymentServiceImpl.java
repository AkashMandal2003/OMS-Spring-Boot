package com.jocata.ordermanagementsystem.services.impl;

import com.jocata.ordermanagementsystem.daos.OrderDao;
import com.jocata.ordermanagementsystem.daos.PaymentDao;
import com.jocata.ordermanagementsystem.entities.OrderDetails;
import com.jocata.ordermanagementsystem.entities.PaymentDetails;
import com.jocata.ordermanagementsystem.services.PaymentService;
import com.jocata.ordermanagementsystem.util.OrderStatus;
import com.jocata.ordermanagementsystem.util.PaymentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger=Logger.getLogger(PaymentServiceImpl.class.getName());

    private final OrderDao orderDao;
    private final PaymentDao paymentDao;

    public PaymentServiceImpl(OrderDao orderDao, PaymentDao paymentDao) {
        this.orderDao = orderDao;
        this.paymentDao = paymentDao;
    }

    @Override
    public boolean processPayment(OrderDetails order) {
        logger.info("Processing payment of $" + order.getTotalAmount() + " for Order ID: " + order.getOrderId());
        PaymentDetails paymentDetails=new PaymentDetails();
        paymentDetails.setPaymentTransactionId(UUID.randomUUID().toString());
        paymentDetails.setPaymentDate(LocalDateTime.now());
        paymentDetails.setPaymentStatus(String.valueOf(PaymentStatus.COMPLETED));
        paymentDetails.setAmount(order.getTotalAmount());
        paymentDetails.setOrder(order);

        paymentDao.savePayment(paymentDetails);
        order.setStatus(OrderStatus.PAID);
        orderDao.updateOrder(order);
        return true;
    }
}
