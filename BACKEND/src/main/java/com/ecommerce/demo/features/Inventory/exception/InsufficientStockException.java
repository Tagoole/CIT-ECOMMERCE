package com.ecommerce.demo.features.Inventory.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(Long id) {
        super("Insufficient stock for product id: " + id);
    }
}
