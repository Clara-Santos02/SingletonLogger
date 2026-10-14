package com.example.logger.service;

import com.example.logger.dto.OrderRequestDTO;
import com.example.logger.entities.Order;
import com.example.logger.entities.Product;
import com.example.logger.entities.User;
import com.example.logger.repository.OrderRepository;
import com.example.logger.singleton.AppLogger;
import com.example.logger.singleton.LogLevel;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, UserService userService, ProductService productService) {

        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public Order createOrder(OrderRequestDTO request) {

        User user =
                userService.searchById(
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

        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(request.getQuantity());
        order.setUnitPrice(product.getPrice());
        order.setTotal(total);
        order.setStatus("CREATED");
        order.setCreatedAt(LocalDateTime.now());

        order = orderRepository.save(order);

        AppLogger.getInstance().log(
                LogLevel.ORDER,
                "Order #"
                        + order.getId()
                        + " created for "
                        + user.getName()
                        + " - "
                        + product.getName()
                        + " - Total R$ "
                        + total
        );
        return order;
    }

    public List<Order> listOrders() {
        return orderRepository.findAll();
    }
}