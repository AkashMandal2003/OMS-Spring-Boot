package com.jocata.ordermanagementsystem.entities;

import com.jocata.ordermanagementsystem.util.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "order_details")
public class OrderDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "order_transaction_id")
    private String orderTransactionId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerDetails customer;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductDetails> products;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PaymentDetails payment;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    private BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ProductDetails product : products) {
            total = total.add(product.getProductPrice());
        }
        return total;
    }

    public String getOrderTransactionId() {
        return orderTransactionId;
    }

    public void setOrderTransactionId(String orderTransactionId) {
        this.orderTransactionId = orderTransactionId;
    }

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public CustomerDetails getCustomer() { return customer; }
    public void setCustomer(CustomerDetails customer) { this.customer = customer; }

    public List<ProductDetails> getProducts() { return products; }
    public void setProducts(List<ProductDetails> products) {
        this.products = products;
        this.totalAmount = calculateTotal();
    }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public PaymentDetails getPayment() { return payment; }
    public void setPayment(PaymentDetails payment) { this.payment = payment; }
}
