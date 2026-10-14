package com.example.logger.controller;

import com.example.logger.dto.ProductRequestDTO;
import com.example.logger.entities.Product;
import com.example.logger.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {this.productService = productService;}

    @PostMapping
    public Product createOrder(@RequestBody ProductRequestDTO request) {
        return productService.createProduct(request);
    }

    @GetMapping
    public List<Product> listOrders() {return productService.listProducts();}
}
