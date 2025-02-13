package com.jocata.ordermanagementsystem.services.impl;

import com.jocata.ordermanagementsystem.daos.ProductDao;
import com.jocata.ordermanagementsystem.entities.ProductDetails;
import com.jocata.ordermanagementsystem.forms.ProductForm;
import com.jocata.ordermanagementsystem.services.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public ProductForm saveProduct(ProductForm productDetails) {
        if (isValid(productDetails)) {
            ProductDetails productEntity = formToEntity(productDetails);
            ProductDetails savedProduct = productDao.saveProduct(productEntity);
            return entityToForm(savedProduct);
        }
        throw new IllegalArgumentException("Product Details are missing..");
    }

    @Override
    public ProductForm getProduct(Integer productId) {
        if (productId != null) {
            ProductDetails product = productDao.getProduct(productId);
            return entityToForm(product);
        }
        throw new IllegalArgumentException("Product Id is missing..");
    }

    @Override
    public List<ProductForm> getAllProducts() {
        List<ProductDetails> productList = productDao.getAllProducts();
        List<ProductForm> productForms = new ArrayList<>();

        for (ProductDetails product : productList) {
            productForms.add(entityToForm(product));
        }
        return productForms;
    }


    @Override
    public ProductForm updateProduct(ProductForm productDetails) {
        if (isValid(productDetails)) {
            ProductForm existingProduct = getProduct(Integer.valueOf(productDetails.getProductId()));
            existingProduct.setProductName(productDetails.getProductName());
            existingProduct.setProductPrice(productDetails.getProductPrice());
            existingProduct.setProductInStock(productDetails.getProductInStock());
            existingProduct.setProductDescription(productDetails.getProductDescription());
            existingProduct.setProductCategory(productDetails.getProductCategory());

            ProductDetails productEntity = new ProductDetails();
            productEntity.setProductId(Integer.valueOf(existingProduct.getProductId()));
            productEntity.setProductName(existingProduct.getProductName());
            productEntity.setProductPrice(new BigDecimal(existingProduct.getProductPrice()));
            productEntity.setProductInStock(Integer.valueOf(existingProduct.getProductInStock()));
            productEntity.setProductCategory(existingProduct.getProductCategory());
            productEntity.setProductDescription(existingProduct.getProductDescription());
            ProductDetails updatedProduct = productDao.updateProduct(productEntity);
            return entityToForm(updatedProduct);
        }
        throw new IllegalArgumentException("Product Details are missing..");
    }

    @Override
    public void deleteProduct(Integer productId) {
        if (productId != null) {
            productDao.deleteProduct(productId);
        }
    }

    private ProductDetails formToEntity(ProductForm productForm) {
        ProductDetails productEntity = new ProductDetails();
        productEntity.setProductName(productForm.getProductName());
        productEntity.setProductPrice(new BigDecimal(productForm.getProductPrice()));
        productEntity.setProductInStock(Integer.valueOf(productForm.getProductInStock()));
        productEntity.setProductDescription(productForm.getProductDescription());
        productEntity.setProductCategory(productForm.getProductCategory());
        return productEntity;
    }

    private ProductForm entityToForm(ProductDetails productEntity) {
        ProductForm productForm = new ProductForm();
        productForm.setProductId(String.valueOf(productEntity.getProductId()));
        productForm.setProductName(productEntity.getProductName());
        productForm.setProductPrice(String.valueOf(productEntity.getProductPrice()));
        productForm.setProductInStock(String.valueOf(productEntity.getProductInStock()));
        productForm.setProductDescription(productEntity.getProductDescription());
        productForm.setProductCategory(productEntity.getProductCategory());
        return productForm;
    }

    private boolean isValid(ProductForm productForm) {
        return (productForm.getProductName() != null && !productForm.getProductName().isEmpty()) ||
                (productForm.getProductPrice() != null && !productForm.getProductPrice().isEmpty()) ||
                (productForm.getProductInStock() != null && !productForm.getProductInStock().isEmpty());
    }
}
