package com.jocata.ordermanagementsystem.services.impl;

import com.jocata.ordermanagementsystem.entities.ProductDetails;
import com.jocata.ordermanagementsystem.services.InventoryService;

import java.util.logging.Logger;


public class InventoryServiceImpl implements InventoryService {

    private static final Logger logger=Logger.getLogger(InventoryServiceImpl.class.getName());

    @Override
    public void addProductToInventory(ProductDetails product, int quantity) {
        //ProductDetails existingProduct = InMemoryProduct.read(product.getProductId());
//
//        if (existingProduct != null) {
//            int newQuantity = existingProduct.getProductInStock() + quantity;
//            existingProduct.setProductInStock(newQuantity);
//            //InMemoryProduct.update(existingProduct);
//            logger.info("Updated stock for " + existingProduct.getProductName() + ": " + newQuantity);
//        } else {
//            product.setProductInStock(quantity);
//            //InMemoryProduct.persist(product);
//            logger.info("Added product " + product.getProductName() + " with stock: " + quantity);
//        }
    }

    @Override
    public boolean checkStock(ProductDetails product) {
        //ProductDetails existingProduct = InMemoryProduct.read(product.getProductId());
        //return existingProduct != null && existingProduct.getProductInStock() > 0;
        return false;
    }

    @Override
    public void reduceStock(ProductDetails product) {
        //ProductDetails existingProduct = InMemoryProduct.read(product.getProductId());

//        if (existingProduct != null && existingProduct.getProductInStock() > 0) {
//            existingProduct.setProductInStock(existingProduct.getProductInStock() - 1);
//            InMemoryProduct.update(existingProduct);
//            logger.info("Reduced stock for " + existingProduct.getProductName() + ": " + existingProduct.getProductInStock());
//        } else {
//            logger.info("Stock unavailable for " + product.getProductName());
//        }
    }
}
