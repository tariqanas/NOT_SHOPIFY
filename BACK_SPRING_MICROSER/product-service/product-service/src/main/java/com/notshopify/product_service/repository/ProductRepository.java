package com.notshopify.product_service.repository;

import com.notshopify.product_service.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProductRepository extends MongoRepository<Product,Long> {
}
