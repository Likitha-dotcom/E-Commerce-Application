package com.ecommerce.microservices.product.exception;

public class ProductException extends RuntimeException{

    public ProductException(String message){
        super(message);
    }
}
