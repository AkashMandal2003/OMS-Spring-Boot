package com.jocata.ordermanagementsystem.services;

import com.jocata.ordermanagementsystem.forms.CustomerForm;
import com.jocata.ordermanagementsystem.forms.OrderForm;
import com.jocata.ordermanagementsystem.forms.ProductForm;

import java.util.List;

public interface OrderService {
    OrderForm createOrder(CustomerForm customer, List<ProductForm> products);

    OrderForm getOrder(Integer orderId);

    List<OrderForm> getCustomerAllOrders(Integer customerId);

    void cancelOrder(Integer orderId);
}
