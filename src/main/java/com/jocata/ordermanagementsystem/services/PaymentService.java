package com.jocata.ordermanagementsystem.services;

import com.jocata.ordermanagementsystem.entities.OrderDetails;

public interface PaymentService {
    boolean processPayment(OrderDetails order);
}
