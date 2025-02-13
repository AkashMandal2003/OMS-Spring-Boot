package com.jocata.ordermanagementsystem.daos;

import com.jocata.ordermanagementsystem.entities.ProductDetails;

import java.util.List;

public interface ProductDao {

    ProductDetails saveProduct(ProductDetails productDetails);

    ProductDetails getProduct(Integer productId);

    List<ProductDetails> getAllProducts();

    ProductDetails updateProduct(ProductDetails productDetails);

    void deleteProduct(Integer productId);

}
