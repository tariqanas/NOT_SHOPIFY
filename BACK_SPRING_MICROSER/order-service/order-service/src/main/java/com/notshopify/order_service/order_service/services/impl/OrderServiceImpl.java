package com.notshopify.order_service.order_service.services.impl;

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

    private  final OrderRepository orderRepository;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setQuantity(order.getQuantity());
        order.setSkuCode(order.getSkuCode());

        orderRepository.save(order);


    }
}
