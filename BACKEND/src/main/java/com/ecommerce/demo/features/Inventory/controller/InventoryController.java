package com.ecommerce.demo.features.Inventory.controller;

import com.ecommerce.demo.features.Inventory.dto.ProductRequestDTO;
import com.ecommerce.demo.features.Inventory.dto.ProductResponseDTO;
import com.ecommerce.demo.features.Inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return inventoryService.getAllProducts();
    }



    @PostMapping
    public ProductResponseDTO createProduct(@RequestBody ProductRequestDTO dto) {
        return inventoryService.createProduct(dto);
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        inventoryService.deleteProduct(id);
    }




}
