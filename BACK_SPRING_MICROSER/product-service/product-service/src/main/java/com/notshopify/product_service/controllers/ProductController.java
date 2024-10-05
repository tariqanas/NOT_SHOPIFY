package com.notshopify.product_service.controllers;

import com.notshopify.product_service.dto.ProductRequest;
import com.notshopify.product_service.dto.ProductResponse;
import com.notshopify.product_service.services.definitions.ProductServiceLogic;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/")
public class ProductController {

    private ProductServiceLogic productServiceLogic;

    private ProductController(ProductServiceLogic PproductServiceLogic) {
        this.productServiceLogic = PproductServiceLogic;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        return productServiceLogic.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProducts() {
        return productServiceLogic.getProducts();
    }
}
