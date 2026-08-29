package com.ecommerce.demo.features.Inventory.service.impl;

import com.ecommerce.demo.features.Inventory.dto.ProductRequestDTO;
import com.ecommerce.demo.features.Inventory.dto.ProductResponseDTO;
import com.ecommerce.demo.features.Inventory.model.Product;
import com.ecommerce.demo.features.Inventory.exception.InsufficientStockException;
import com.ecommerce.demo.features.Inventory.exception.ProductNotFoundException;
import com.ecommerce.demo.features.Inventory.mapper.ProductMapper;
import com.ecommerce.demo.features.Inventory.repository.ProductRepository;
import com.ecommerce.demo.features.Inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final ProductRepository productRepository;

    @Autowired
    public InventoryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductMapper.toDTO(product);
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        Product product = ProductMapper.toEntity(dto);
        Product saved = productRepository.save(product);
        return ProductMapper.toDTO(saved);
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantityInStock(dto.getQuantityInStock());
        product.setSku(dto.getSku());

        Product updated = productRepository.save(product);
        return ProductMapper.toDTO(updated);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponseDTO adjustStock(Long id, Integer amount) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        int newQuantity = product.getQuantityInStock() + amount;
        if (newQuantity < 0) {
            throw new InsufficientStockException(id);
        }

        product.setQuantityInStock(newQuantity);
        Product updated = productRepository.save(product);
        return ProductMapper.toDTO(updated);
    }

    @Override
    public boolean checkAvailability(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return product.getQuantityInStock() > 0;
    }

    @Override
    public List<ProductResponseDTO> getLowStock(Integer threshold) {
        return productRepository.findByQuantityInStockLessThan(threshold)
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }
}
