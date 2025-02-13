package com.jocata.ordermanagementsystem.controllers;

import com.jocata.ordermanagementsystem.forms.ProductForm;
import com.jocata.ordermanagementsystem.services.ProductService;
import com.jocata.ordermanagementsystem.services.impl.ProductServiceImpl;

import java.util.List;

public class ProductController {

    ProductService productService=new ProductServiceImpl();

    public ProductForm saveProduct(ProductForm productForm) {
        if(productForm!=null) {
            return productService.saveProduct(productForm);
        }
        throw new IllegalArgumentException("Product Details are missing..");
    }

    public ProductForm getProduct(Integer productId) {
        if(productId!=null) {
            return productService.getProduct(productId);
        }
        throw new IllegalArgumentException("Product id is missing..");
    }

    public List<ProductForm> getAllProducts() {
        return productService.getAllProducts();
    }

    public ProductForm updateProduct(ProductForm productForm) {
        if(productForm!=null) {
            return productService.updateProduct(productForm);
        }
        throw new IllegalArgumentException("Product Details are missing..");
    }

    public void deleteProduct(Integer productId) {
        if(productId!=null) {
            productService.deleteProduct(productId);
        }
        throw new IllegalArgumentException("Product id is missing..");
    }
}
