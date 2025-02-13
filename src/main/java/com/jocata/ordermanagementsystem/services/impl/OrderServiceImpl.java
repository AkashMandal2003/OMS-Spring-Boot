package com.jocata.ordermanagementsystem.services.impl;

import com.jocata.ordermanagementsystem.daos.OrderDao;
import com.jocata.ordermanagementsystem.daos.impl.OrderDaoImpl;
import com.jocata.ordermanagementsystem.entities.CustomerDetails;
import com.jocata.ordermanagementsystem.entities.OrderDetails;
import com.jocata.ordermanagementsystem.entities.ProductDetails;
import com.jocata.ordermanagementsystem.forms.CustomerForm;
import com.jocata.ordermanagementsystem.forms.OrderForm;
import com.jocata.ordermanagementsystem.forms.ProductForm;
import com.jocata.ordermanagementsystem.services.InventoryService;
import com.jocata.ordermanagementsystem.services.OrderService;
import com.jocata.ordermanagementsystem.services.PaymentService;
import com.jocata.ordermanagementsystem.util.OrderStatus;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    OrderDao orderDao = new OrderDaoImpl();
    PaymentService paymentService = new PaymentServiceImpl();
    InventoryService inventoryService = new InventoryServiceImpl();

    @Override
    public OrderForm createOrder(CustomerForm customer, List<ProductForm> products) {
        if (customer != null && products != null) {
            CustomerDetails customerDetails = new CustomerDetails();
            customerDetails.setCustomerId(Integer.valueOf(customer.getCustomerId()));
            customerDetails.setCustomerName(customer.getCustomerName());
            customerDetails.setEmail(customer.getEmail());

            List<ProductDetails> productDetailsList = new ArrayList<>();
            for (ProductForm productForm : products) {
                ProductDetails product = new ProductDetails();
                product.setProductId(Integer.valueOf(productForm.getProductId()));
                product.setProductName(productForm.getProductName());
               // product.setProductPrice(Double.valueOf(productForm.getProductPrice()));
                if (!inventoryService.checkStock(product)) {
                    throw new IllegalArgumentException("Product " + product.getProductName() + " is out of stock!");
                }
                productDetailsList.add(product);
            }

            OrderDetails order = new OrderDetails();
            order.setOrderId(generateRandomId());
            order.setCustomer(customerDetails);
            order.setProducts(productDetailsList);
            order.setStatus(OrderStatus.PENDING);

            OrderDetails createdOrder = orderDao.createOrder(order);

            for (ProductDetails product : productDetailsList) {
                inventoryService.reduceStock(product);
            }

            OrderProcessor orderProcessor = new OrderProcessor();
            orderProcessor.setOrder(createdOrder);
            orderProcessor.setPaymentService(paymentService);
            orderProcessor.start();

            return toOrderForm(createdOrder);
        }
        throw new IllegalArgumentException("Check Details, Some fields are missing...");
    }

    @Override
    public OrderForm getOrder(Integer orderId) {
        if (orderId != null) {
            return toOrderForm(orderDao.getOrderById(orderId));
        }
        throw new IllegalArgumentException("Order id is missing...");
    }


    @Override
    public List<OrderForm> getCustomerAllOrders(Integer customerId) {
        if(customerId!=null) {
            List<OrderDetails> orderDetails = orderDao.customersAllOrders(customerId);
            List<OrderForm> forms = new ArrayList<>();
            for (OrderDetails details : orderDetails) {
                forms.add(toOrderForm(details));
            }
            return forms;
        }
        throw new IllegalArgumentException("Customer id is missing...");
    }

    @Override
    public OrderForm updateOrder(OrderForm updatedOrderForm) {
        if (updatedOrderForm != null) {
            OrderDetails existingOrder = orderDao.getOrderById(Integer.valueOf(updatedOrderForm.getOrderId()));
            if (existingOrder == null) {
                throw new IllegalArgumentException("Order ID " + updatedOrderForm.getOrderId() + " not found.");
            }

            CustomerDetails customer = new CustomerDetails();
            customer.setCustomerId(Integer.valueOf(updatedOrderForm.getCustomer().getCustomerId()));
            customer.setCustomerName(updatedOrderForm.getCustomer().getCustomerName());
            customer.setEmail(updatedOrderForm.getCustomer().getEmail());

            List<ProductDetails> updatedProducts = new ArrayList<>();
            for (ProductForm productForm : updatedOrderForm.getProducts()) {
                ProductDetails product = new ProductDetails();
                product.setProductId(Integer.parseInt(productForm.getProductId()));
                product.setProductName(productForm.getProductName());
                //product.setProductPrice(Double.parseDouble(productForm.getProductPrice()));
                updatedProducts.add(product);
            }

            existingOrder.setCustomer(customer);
            existingOrder.setProducts(updatedProducts);
            existingOrder.setStatus(OrderStatus.PENDING);

            orderDao.updateOrder(existingOrder);
            return toOrderForm(existingOrder);
        }
        throw new IllegalArgumentException("Check Details, Some fields are missing...");
    }

    @Override
    public void cancelOrder(Integer orderId) {
        if (orderId != null) {
            OrderDetails order = orderDao.getOrderById(orderId);
            if (order == null) {
                throw new IllegalArgumentException("Order ID " + orderId + " not found.");
            }
            order.setStatus(OrderStatus.CANCELED);
            orderDao.cancelOrder(order);
            return;
        }
        throw new IllegalArgumentException("Order Id is missing...");
    }


    private OrderForm toOrderForm(OrderDetails orderDetails) {
        OrderForm orderForm = new OrderForm();
        orderForm.setOrderId(String.valueOf(orderDetails.getOrderId()));

        CustomerForm customerForm = new CustomerForm();
        customerForm.setCustomerId(String.valueOf(orderDetails.getCustomer().getCustomerId()));
        customerForm.setCustomerName(orderDetails.getCustomer().getCustomerName());
        customerForm.setEmail(orderDetails.getCustomer().getEmail());
        orderForm.setCustomer(customerForm);

        List<ProductForm> productForms = new ArrayList<>();
        for (ProductDetails product : orderDetails.getProducts()) {
            ProductForm productForm = new ProductForm();
            productForm.setProductId(String.valueOf(product.getProductId()));
            productForm.setProductName(product.getProductName());
            productForm.setProductPrice(String.valueOf(product.getProductPrice()));
            productForms.add(productForm);
        }
        orderForm.setProducts(productForms);

        return orderForm;
    }

    private static int generateRandomId() {
        return (int) (Math.random() * 1000000);
    }


}
