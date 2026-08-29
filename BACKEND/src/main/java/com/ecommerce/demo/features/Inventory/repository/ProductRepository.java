package com.ecommerce.demo.features.Inventory.repository;

import com.ecommerce.demo.features.Inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByQuantityInStockLessThan(Integer threshold);
}
