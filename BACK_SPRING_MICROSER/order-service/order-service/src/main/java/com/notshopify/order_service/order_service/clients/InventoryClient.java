package com.notshopify.order_service.order_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "inventory", url = "${inventory.url}")
public interface InventoryClient {

    @GetMapping( value = "/api/inventory")
    boolean isInStock(@RequestParam(name = "skuCode") String skuCode, @RequestParam(name = "quantity")Integer quantity);

}
