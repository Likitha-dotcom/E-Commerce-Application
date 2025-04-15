package com.ecommerce.microservices.product.model;

import lombok.Builder;
import lombok.Data;

@Data
public class ErrorResponse {

    private String status;
    private String description;
}
