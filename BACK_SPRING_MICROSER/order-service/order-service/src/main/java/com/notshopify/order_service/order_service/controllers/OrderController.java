package com.notshopify.order_service.order_service.controllers;

import com.notshopify.order_service.order_service.dto.OrderRequest;
import com.notshopify.order_service.order_service.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/orders/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        this.orderService.placeOrder(orderRequest);
        log.info("Order placed {}", orderRequest.skuCode());
        return "Your order has been placed";
    }

}
