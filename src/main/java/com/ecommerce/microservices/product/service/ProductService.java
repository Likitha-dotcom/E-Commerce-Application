package com.ecommerce.microservices.product.service;

import com.ecommerce.microservices.product.exception.ProductException;
import com.ecommerce.microservices.product.model.Product;
import com.ecommerce.microservices.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService implements ProductServiceInterface{

    @Autowired // Dependency Injection
    private ProductRepository productRepository;


    @Override
    public String createProduct(Product productRequest) {
        Product product = Product.builder()
                .id(productRequest.getId())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        if(productRepository.findById(productRequest.getId()).isPresent())
            throw new ProductException("Product already exists with this ID!");
        else
            productRepository.save(product);

        log.info("Product was added to DB successfully!");
        return "Product is created with Product ID : " + product.getId();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() ->  new ProductException("Product ID " + productId + " not found"));
    }
}
