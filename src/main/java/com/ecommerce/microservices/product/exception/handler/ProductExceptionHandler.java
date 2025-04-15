package com.ecommerce.microservices.product.exception.handler;

import com.ecommerce.microservices.product.exception.ProductException;
import com.ecommerce.microservices.product.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ProductExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ErrorResponse> handleException(ProductException productException){
        ErrorResponse errors = new ErrorResponse();

        errors.setStatus(HttpStatus.NOT_FOUND.toString());
        errors.setDescription(productException.getMessage());

        return new ResponseEntity<>(errors,HttpStatus.NOT_FOUND);
    }

}
