package com.ecommerce.demo.features.Inventory.dto;

public class ProductRequestDTO {

    private String name;
    private String description;
    private Double price;
    private Integer quantityInStock;
    private String sku;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getQuantityInStock() { return quantityInStock; }
    public void setQuantityInStock(Integer quantityInStock) { this.quantityInStock = quantityInStock; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
}
