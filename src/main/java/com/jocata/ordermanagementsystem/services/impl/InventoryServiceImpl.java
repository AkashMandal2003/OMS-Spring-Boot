package com.jocata.ordermanagementsystem.services.impl;

import com.jocata.ordermanagementsystem.daos.ProductDao;
import com.jocata.ordermanagementsystem.entities.ProductDetails;
import com.jocata.ordermanagementsystem.services.InventoryService;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class InventoryServiceImpl implements InventoryService {

    private static final Logger logger=Logger.getLogger(InventoryServiceImpl.class.getName());
    private final ProductDao productDao;

    public InventoryServiceImpl(ProductDao productDao){
        this.productDao = productDao;
    }

    @Override
    public void addProductToInventory(ProductDetails product, int quantity) {

        ProductDetails existingProduct = productDao.getProduct(product.getProductId());

        if (existingProduct != null) {
            int newQuantity = existingProduct.getProductInStock() + quantity;
            existingProduct.setProductInStock(newQuantity);
            productDao.updateProduct(existingProduct);
            logger.info("Updated stock for " + existingProduct.getProductName() + ": " + newQuantity);
        } else {
            product.setProductInStock(quantity);
            productDao.saveProduct(product);
            logger.info("Added product " + product.getProductName() + " with stock: " + quantity);
        }
    }

    @Override
    public boolean checkStock(ProductDetails product) {
        ProductDetails existingProduct = productDao.getProduct(product.getProductId());
        return existingProduct != null && existingProduct.getProductInStock() > 0;
    }

    @Override
    public void reduceStock(ProductDetails product) {
        ProductDetails existingProduct = productDao.getProduct(product.getProductId());

        if (existingProduct != null && existingProduct.getProductInStock() > 0) {
            existingProduct.setProductInStock(existingProduct.getProductInStock() - 1);
            productDao.updateProduct(existingProduct);
            logger.info("Reduced stock for " + existingProduct.getProductName() + ": " + existingProduct.getProductInStock());
        } else {
            logger.info("Stock unavailable for " + product.getProductName());
        }
    }
}
