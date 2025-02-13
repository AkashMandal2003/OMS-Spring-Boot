package com.jocata.ordermanagementsystem.services;

import com.jocata.ordermanagementsystem.entities.ProductDetails;

public interface InventoryService {
    void addProductToInventory(ProductDetails product, int quantity);

    boolean checkStock(ProductDetails product);

    void reduceStock(ProductDetails product);
}
