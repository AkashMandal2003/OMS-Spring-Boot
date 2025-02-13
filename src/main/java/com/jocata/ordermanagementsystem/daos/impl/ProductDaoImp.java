package com.jocata.ordermanagementsystem.daos.impl;

import com.jocata.ordermanagementsystem.daos.ProductDao;
import com.jocata.ordermanagementsystem.entities.ProductDetails;

import java.util.List;

public class ProductDaoImp implements ProductDao {

    @Override
    public ProductDetails saveProduct(ProductDetails productDetails) {
        return null;
    }

    @Override
    public ProductDetails getProduct(Integer productId) {
        return null;
    }

    @Override
    public List<ProductDetails> getAllProducts() {
        return List.of();
    }

    @Override
    public ProductDetails updateProduct(ProductDetails productDetails) {
        return null;
    }

    @Override
    public void deleteProduct(Integer productId) {

    }
}
