package com.jocata.ordermanagementsystem.controllers;

import com.jocata.ordermanagementsystem.forms.ProductForm;
import com.jocata.ordermanagementsystem.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/save")
    public ProductForm saveProduct(@RequestBody ProductForm productForm) {
        if(productForm!=null) {
            return productService.saveProduct(productForm);
        }
        throw new IllegalArgumentException("Product Details are missing..");
    }

    @GetMapping("/{productId}")
    public ProductForm getProduct(@PathVariable Integer productId) {
        if(productId!=null) {
            return productService.getProduct(productId);
        }
        throw new IllegalArgumentException("Product id is missing..");
    }

    @GetMapping
    public List<ProductForm> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/update")
    public ProductForm updateProduct(@RequestBody ProductForm productForm) {
        if(productForm!=null) {
            return productService.updateProduct(productForm);
        }
        throw new IllegalArgumentException("Product Details are missing..");
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Integer productId) {
        if(productId!=null) {
            productService.deleteProduct(productId);
        }
    }
}
