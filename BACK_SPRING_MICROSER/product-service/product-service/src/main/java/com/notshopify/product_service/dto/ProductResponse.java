package com.notshopify.product_service.dto;

import java.math.BigDecimal;

public record ProductResponse(Long id , String name , BigDecimal price , String description) {}
