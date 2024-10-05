package com.notshopify.product_service.services.impl;

import com.notshopify.product_service.dto.ProductRequest;
import com.notshopify.product_service.dto.ProductResponse;
import com.notshopify.product_service.models.Product;
import com.notshopify.product_service.repository.ProductRepository;
import com.notshopify.product_service.services.definitions.ProductServiceLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceLogicImpl implements ProductServiceLogic {

    ProductRepository productRepository;


    private ProductServiceLogicImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {

        Product product = productRepository.save(Product.builder().
                id(productRequest.id()).
                name(productRequest.name()).
                price(productRequest.price()).
                description(productRequest.description()).
                build());

        log.info("Product Saved {} ", productRequest.name());
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getDescription());

    }

    @Override
    public List<ProductResponse> getProducts() {
        log.info("Getting products");
        return productRepository.findAll().stream().map(product ->
                new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getDescription())
        ).toList();

    }
}
