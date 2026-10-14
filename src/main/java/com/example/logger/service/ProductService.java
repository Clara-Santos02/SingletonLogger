package com.example.logger.service;

import com.example.logger.dto.ProductRequestDTO;
import com.example.logger.entities.Product;
import com.example.logger.repository.ProductRepository;
import com.example.logger.singleton.AppLogger;
import com.example.logger.singleton.LogLevel;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductRequestDTO request) {

        Product product = new Product();
        product.setName(request.getName());
        product.setStock(request.getStock());
        product.setPrice(request.getPrice());

        product = productRepository.save(product);

        AppLogger.getInstance().log(
                LogLevel.PRODUCT,
                "Product "
                        + product.getName()
                        + " registered with ID "
                        + product.getId()
        );

        return product;
    }

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    public Product searchById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> {

                    AppLogger.getInstance().log(
                            LogLevel.ERROR,
                            "Product with ID "
                                    + id
                                    + " not found."
                    );

                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Product not found"
                    );
                });
    }

    public synchronized Product reserveStock(Long id, int quantity) {

        Product product = searchById(id);

        if (product.getStock() < quantity) {

            AppLogger.getInstance().log(
                    LogLevel.WARNING,
                    "Insufficient stock for "
                            + product.getName()
            );

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Insufficient stock."
            );
        }

        product.setStock(
                product.getStock() - quantity
        );

        return productRepository.save(product);
    }
}