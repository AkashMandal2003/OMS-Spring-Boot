package com.jocata.ordermanagementsystem.services;

import com.jocata.ordermanagementsystem.forms.ProductForm;

import java.util.List;

public interface ProductService {

    ProductForm saveProduct(ProductForm productDetails);
    ProductForm getProduct(Integer productId);
    List<ProductForm> getAllProducts();
    ProductForm updateProduct(ProductForm productDetails);
    void deleteProduct(Integer productId);

}
