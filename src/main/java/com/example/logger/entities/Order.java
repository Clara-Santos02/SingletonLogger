package com.example.logger.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Order {
    private Long id;
    private Long userId;
    private Long productId;
    private String product;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal total;
    private String status;
    private LocalDateTime createdAt;
}
