package com.example.logger.service;

import com.example.logger.dto.OrderRequestDTO;
import com.example.logger.entities.Order;
import com.example.logger.entities.Product;
import com.example.logger.entities.User;
import com.example.logger.singleton.AppLogger;
import com.example.logger.singleton.LogLevel;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

    private final ConcurrentHashMap<Long, Order> orders = new ConcurrentHashMap<>();

    private final AtomicLong counter = new AtomicLong(1);

    private final UserService userService;
    private final ProductService productService;

    public OrderService(UserService userService, ProductService productService) {

        this.userService = userService;
        this.productService = productService;
    }

    public Order createOrder(OrderRequestDTO request) {

        User user =
                userService.serchById(
                        request.getUserId()
                );

        Product product =
                productService.reserveStock(
                        request.getProductId(),
                        request.getQuantity()
                );

        BigDecimal total =
                product.getPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        request.getQuantity()
                                )
                        );

        Long id = counter.getAndIncrement();

        Order order = new Order(
                id,
                user.getId(),
                product.getId(),
                product.getName(),
                request.getQuantity(),
                product.getPrice(),
                total,
                "CREATED",
                LocalDateTime.now()
        );

        orders.put(id, order);

        AppLogger.getInstance().log(
                LogLevel.ORDER,
                "Order #" + id
                        + " created for "
                        + user.getName()
                        + " - Product: "
                        + product.getName()
                        + " - Quantity: "
                        + request.getQuantity()
                        + " - Total: R$ "
                        + total
        );
        return order;
    }

    public List<Order> listOrders() {
        return List.copyOf(orders.values());
    }
}