package com.notshopify.order_service.order_service.services;

import com.notshopify.order_service.order_service.dto.OrderRequest;


public interface OrderService {

    void placeOrder(OrderRequest orderRequest);

}
