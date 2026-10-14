package com.example.logger.service;

import com.example.logger.dto.ProductRequestDTO;
import com.example.logger.entities.Product;
import com.example.logger.singleton.AppLogger;
import com.example.logger.singleton.LogLevel;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    private final ConcurrentHashMap<Long, Product> products =
            new ConcurrentHashMap<>();

    private final AtomicLong counter =
            new AtomicLong(1);

    public Product createProduct(ProductRequestDTO request) {

        Long id = counter.getAndIncrement();

        Product product = new Product(
                id,
                request.getName(),
                request.getPrice(),
                request.getStock()
        );

        products.put(id, product);

        AppLogger.getInstance().log(
                LogLevel.PRODUCT,
                "Product "
                        + product.getName()
                        + " registered with ID "
                        + id
        );

        return product;
    }

    public List<Product> listProducts() {
        return List.copyOf(products.values());
    }

    public Product searchById(Long id) {

        Product product = products.get(id);

        if (product == null) {

            AppLogger.getInstance().log(
                    LogLevel.ERROR,
                    "Product with ID "
                            + id
                            + " not found."
            );

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Product not found"
            );
        }

        return product;
    }

  /*  public synchronized Order reservarEstoque(
            Long id,
            int quantidade) {

        Order produto = searchById(id);

        if (produto.getEstoque() < quantidade) {

            AppLogger.getInstance().log(
                    LogLevel.WARNING,
                    "Estoque insuficiente para o produto "
                            + produto.getNome()
            );

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Estoque insuficiente"
            );
        }

        produto.setEstoque(
                produto.getEstoque() - quantidade
        );

        return produto;
    } */
}
