package com.example.logger.controller;

import com.example.logger.dto.OrderRequestDTO;
import com.example.logger.singleton.AppLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @PostMapping
    public ResponseEntity<String> createOrder(
            @RequestBody OrderRequestDTO request) {

        AppLogger logger = AppLogger.getInstance();

        logger.log(
                "ORDER",
                "Order created: "
                        + request.getProduct()
                        + " - R$ "
                        + request.getValue()
        );

        return ResponseEntity.ok(
                "Order created successfully"
        );
    }
}
