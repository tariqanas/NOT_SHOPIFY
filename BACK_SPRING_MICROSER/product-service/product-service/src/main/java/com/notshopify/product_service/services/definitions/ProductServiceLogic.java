package com.notshopify.product_service.services.definitions;

import com.notshopify.product_service.dto.ProductRequest;
import com.notshopify.product_service.dto.ProductResponse;
import com.notshopify.product_service.models.Product;

import java.util.List;

public interface ProductServiceLogic {

    ProductResponse createProduct(ProductRequest productRequest);

    List<ProductResponse> getProducts();

}
