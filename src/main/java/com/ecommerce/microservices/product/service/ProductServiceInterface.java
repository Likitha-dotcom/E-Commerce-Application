package com.ecommerce.microservices.product.service;

import com.ecommerce.microservices.product.model.Product;

import java.util.List;

public interface ProductServiceInterface {

    String createProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(String productId);
}
