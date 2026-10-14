package com.example.logger.controller;

import com.example.logger.dto.OrderRequestDTO;
import com.example.logger.entities.Order;
import com.example.logger.service.OrderService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController (OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRequestDTO request) {
        return orderService.createOrder(request);
    }

    @GetMapping
    public List<Order> listOrders() {return orderService.listOrders();}
}
