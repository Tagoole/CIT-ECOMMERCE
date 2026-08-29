package com.ecommerce.demo.features.Inventory.mapper;

import com.ecommerce.demo.features.Inventory.dto.ProductRequestDTO;
import com.ecommerce.demo.features.Inventory.dto.ProductResponseDTO;
import com.ecommerce.demo.features.Inventory.model.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequestDTO dto) {
        return new Product(dto.getName(), dto.getDescription(), dto.getPrice(), dto.getQuantityInStock(), dto.getSku());
    }

    public static ProductResponseDTO toDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantityInStock(),
                product.getSku()
        );
    }
}
