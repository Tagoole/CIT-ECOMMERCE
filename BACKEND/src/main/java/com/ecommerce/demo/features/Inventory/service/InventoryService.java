package com.ecommerce.demo.features.Inventory.service;

import com.ecommerce.demo.features.Inventory.dto.ProductRequestDTO;
import com.ecommerce.demo.features.Inventory.dto.ProductResponseDTO;
import java.util.List;

public interface InventoryService {
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO getProductById(Long id);
    ProductResponseDTO createProduct(ProductRequestDTO dto);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto);
    void deleteProduct(Long id);
    ProductResponseDTO adjustStock(Long id, Integer amount);
    boolean checkAvailability(Long id);
    List<ProductResponseDTO> getLowStock(Integer threshold);
}
