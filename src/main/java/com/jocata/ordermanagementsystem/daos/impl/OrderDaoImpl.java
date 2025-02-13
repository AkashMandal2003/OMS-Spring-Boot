package com.jocata.ordermanagementsystem.daos.impl;

import com.jocata.ordermanagementsystem.daos.OrderDao;
import com.jocata.ordermanagementsystem.entities.OrderDetails;

import java.util.List;

public class OrderDaoImpl implements OrderDao {

    @Override
    public OrderDetails createOrder(OrderDetails orderDetails) {
        return null;
    }

    @Override
    public OrderDetails getOrderById(Integer orderId) {
        return null;
    }

    @Override
    public List<OrderDetails> allOrders() {
        return List.of();
    }

    @Override
    public List<OrderDetails> customersAllOrders(Integer customerId) {
        return List.of();
    }

    @Override
    public void updateOrder(OrderDetails existingOrder) {

    }

    @Override
    public void cancelOrder(OrderDetails orderDetails) {

    }
}
