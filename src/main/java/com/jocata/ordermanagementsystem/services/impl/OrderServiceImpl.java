package com.jocata.ordermanagementsystem.services.impl;

import com.jocata.ordermanagementsystem.daos.CustomerDao;
import com.jocata.ordermanagementsystem.daos.OrderDao;
import com.jocata.ordermanagementsystem.daos.ProductDao;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    private final OrderProcessor orderProcessor;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public OrderServiceImpl(OrderDao orderDao, PaymentService paymentService, InventoryService inventoryService, OrderProcessor orderProcessor, CustomerDao customerDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
        this.orderProcessor = orderProcessor;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    @Override
    public OrderForm createOrder(CustomerForm customer, List<ProductForm> products) {
        if (customer != null && products != null) {
            CustomerDetails customerDetails = customerDao.getCustomer(Integer.valueOf(customer.getCustomerId()));

            List<ProductDetails> productDetailsList = new ArrayList<>();
            for (ProductForm productForm : products) {
                ProductDetails product = productDao.getProduct(Integer.valueOf(productForm.getProductId()));
                if (!inventoryService.checkStock(product)) {
                    throw new IllegalArgumentException("Product " + product.getProductName() + " is out of stock!");
                }
                productDetailsList.add(product);
            }

            OrderDetails order = new OrderDetails();
            order.setOrderTransactionId(UUID.randomUUID().toString());
            order.setCustomer(customerDetails);
            order.setProducts(productDetailsList);
            order.setStatus(OrderStatus.PENDING);

            OrderDetails createdOrder = orderDao.createOrder(order);

            for (ProductDetails product : productDetailsList) {
                inventoryService.reduceStock(product);
            }

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
        if (customerId != null) {
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
        customerForm.setAddress(orderDetails.getCustomer().getAddress());
        orderForm.setCustomer(customerForm);

        List<ProductForm> productForms = new ArrayList<>();
        for (ProductDetails product : orderDetails.getProducts()) {
            ProductForm productForm = new ProductForm();
            productForm.setProductId(String.valueOf(product.getProductId()));
            productForm.setProductName(product.getProductName());
            productForm.setProductPrice(String.valueOf(product.getProductPrice()));
            productForm.setProductDescription(product.getProductDescription());
            productForm.setProductCategory(product.getProductCategory());
            productForms.add(productForm);
        }
        orderForm.setProducts(productForms);

        return orderForm;
    }

}
