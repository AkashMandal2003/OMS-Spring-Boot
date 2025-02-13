package com.jocata.ordermanagementsystem.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product_details")
public class ProductDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Column(name = "product_price", nullable = false)
    private BigDecimal productPrice;

    @Column(name = "product_in_stock", nullable = false)
    private Integer productInStock;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_category", nullable = false)
    private String productCategory;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductInStock() {
        return productInStock;
    }

    public void setProductInStock(Integer productInStock) {
        this.productInStock = productInStock;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
}
