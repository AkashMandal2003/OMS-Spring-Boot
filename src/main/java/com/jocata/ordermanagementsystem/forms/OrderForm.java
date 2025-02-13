package com.jocata.ordermanagementsystem.forms;

import java.util.List;

public class OrderForm {
    private String orderId;
    private CustomerForm customer;
    private List<ProductForm> products;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public CustomerForm getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerForm customer) {
        this.customer = customer;
    }

    public List<ProductForm> getProducts() {
        return products;
    }

    public void setProducts(List<ProductForm> products) {
        this.products = products;
    }
}
