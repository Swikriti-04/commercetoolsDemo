package com.example.inventory.repository;

import com.example.inventory.entity.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInventoryRepository
        extends JpaRepository<ProductInventory, String> {
}
