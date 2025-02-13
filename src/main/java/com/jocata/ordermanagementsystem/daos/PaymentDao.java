package com.jocata.ordermanagementsystem.daos;

import com.jocata.ordermanagementsystem.entities.PaymentDetails;

public interface PaymentDao {

    PaymentDetails savePayment(PaymentDetails paymentDetails);

}
