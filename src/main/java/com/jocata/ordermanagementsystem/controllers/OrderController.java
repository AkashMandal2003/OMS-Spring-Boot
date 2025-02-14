package com.jocata.ordermanagementsystem.controllers;

import com.jocata.ordermanagementsystem.forms.OrderForm;
import com.jocata.ordermanagementsystem.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/createOrder")
    public OrderForm createOrder(@RequestBody OrderForm orderForm) {
        if (orderForm != null) {
            return orderService.createOrder(orderForm.getCustomer(), orderForm.getProducts());
        }
        throw new IllegalArgumentException("Details are missing..");
    }

    @GetMapping("/{orderId}")
    public OrderForm getOrderById(@PathVariable Integer orderId) {
        if (orderId != null) {
            return orderService.getOrder(orderId);
        }
        throw new IllegalArgumentException("Details are missing..");
    }

    @DeleteMapping("/{orderId}")
    public void cancelOrder(@PathVariable Integer orderId) {
        if (orderId != null) {
            orderService.cancelOrder(orderId);
        }
    }

    @GetMapping("/{customerId}/allOrders")
    public List<OrderForm> getCustomerAllOrders(@PathVariable Integer customerId) {
        return orderService.getCustomerAllOrders(customerId);
    }

}
