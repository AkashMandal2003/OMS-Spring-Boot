package com.jocata.ordermanagementsystem.daos;

import com.jocata.ordermanagementsystem.entities.OrderDetails;

import java.util.List;

public interface OrderDao {

    OrderDetails createOrder(OrderDetails orderDetails);
    OrderDetails getOrderById(Integer orderId);
    List<OrderDetails> allOrders();
    List<OrderDetails> customersAllOrders(Integer customerId);
    void updateOrder(OrderDetails existingOrder);
    void cancelOrder(OrderDetails orderDetails);
}
