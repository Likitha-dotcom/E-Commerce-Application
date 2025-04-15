package com.ecommerce.microservices.product.controller;

import com.ecommerce.microservices.product.model.Product;
import com.ecommerce.microservices.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody Product product){
        String productStatus = productService.createProduct(product);
        return new ResponseEntity<>( productStatus, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> productList = productService.getAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") String id){
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
