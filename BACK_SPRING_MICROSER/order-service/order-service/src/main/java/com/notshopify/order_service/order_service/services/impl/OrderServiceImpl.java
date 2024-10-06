package com.notshopify.order_service.order_service.services.impl;

import com.notshopify.order_service.order_service.clients.InventoryClient;
import com.notshopify.order_service.order_service.dto.OrderRequest;
import com.notshopify.order_service.order_service.model.Order;
import com.notshopify.order_service.order_service.repositories.OrderRepository;
import com.notshopify.order_service.order_service.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final InventoryClient inventory;

    @Override
    public void placeOrder(OrderRequest orderRequest) {

        boolean isInStock = inventory.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (isInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setQuantity(order.getQuantity());
            order.setSkuCode(order.getSkuCode());
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Failed to place order because not in stock");
        }


    }
}
