package com.notshopify.inventory_service.services;

public interface InventoryService {

    boolean isInStock(String skuCode,Integer quantity);
}
