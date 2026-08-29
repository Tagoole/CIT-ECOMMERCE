package com.ecommerce.demo.features.Inventory.dto;

public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantityInStock;
    private String sku;

    public ProductResponseDTO(Long id, String name, String description, Double price, Integer quantityInStock, String sku) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.sku = sku;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Double getPrice() { return price; }
    public Integer getQuantityInStock() { return quantityInStock; }
    public String getSku() { return sku; }
}
