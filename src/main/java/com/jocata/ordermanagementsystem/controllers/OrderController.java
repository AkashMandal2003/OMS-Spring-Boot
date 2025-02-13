package com.jocata.ordermanagementsystem.controllers;

import com.jocata.ordermanagementsystem.forms.OrderForm;
import com.jocata.ordermanagementsystem.services.OrderService;
import com.jocata.ordermanagementsystem.services.impl.OrderServiceImpl;

import java.util.List;

public class OrderController {

     OrderService orderService=new OrderServiceImpl();

    public OrderForm createOrder(OrderForm orderForm) {
        if(orderForm!=null) {
            return orderService.createOrder(orderForm.getCustomer(), orderForm.getProducts());
        }
        throw new IllegalArgumentException("Details are missing..");
    }

    public OrderForm updateOrder(OrderForm orderForm){
        if(orderForm!=null){
            return orderService.updateOrder(orderForm);
        }
        throw new IllegalArgumentException("Details are missing..");
    }

    public OrderForm getOrderById(Integer orderId){
        if(orderId!=null){
            return orderService.getOrder(orderId);
        }
        throw new IllegalArgumentException("Details are missing..");
    }

    public void cancelOrder(Integer orderId){
        if(orderId!=null){
            orderService.cancelOrder(orderId);
            return;
        }
        throw new IllegalArgumentException("Details are missing..");
    }

    public List<OrderForm> getCustomerAllOrders(OrderForm orderForm){
        return orderService.getCustomerAllOrders(Integer.valueOf(orderForm.getCustomer().getCustomerId()));
    }

}
