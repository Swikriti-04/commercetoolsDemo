package com.example.inventory.service;

import com.example.inventory.api.model.InventoryResponse;
import com.example.inventory.entity.ProductInventory;
import com.example.inventory.repository.ProductInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final ProductInventoryRepository repository;

    public InventoryResponse getInventoryBySku(String skuId) {
        log.debug("Fetching inventory for SKU {}", skuId);

        ProductInventory inventory = repository.findById(skuId)
                .orElseThrow(() -> {
                    log.error("SKU not found: {}", skuId);
                    return new RuntimeException("SKU not found");
                });

        return mapToResponse(inventory);
    }

    @Transactional
    public InventoryResponse reduceInventory(String skuId, int quantity) {
        log.info("Reducing inventory for SKU {} by {}", skuId, quantity);

        ProductInventory inventory = repository.findById(skuId)
                .orElseThrow(() -> {
                    log.error("SKU not found: {}", skuId);
                    return new RuntimeException("SKU not found");
                });

        if (inventory.getInventory() < quantity) {
            log.error(
                    "Insufficient inventory for SKU {}. Available={}, Requested={}",
                    skuId,
                    inventory.getInventory(),
                    quantity
            );
            throw new IllegalStateException("Insufficient inventory");
        }

        inventory.setInventory(inventory.getInventory() - quantity);
        repository.save(inventory);

        log.info(
                "Inventory updated for SKU {}. Remaining quantity={}",
                skuId,
                inventory.getInventory()
        );

        return mapToResponse(inventory);
    }

    private InventoryResponse mapToResponse(ProductInventory inventory) {
        InventoryResponse response = new InventoryResponse();
        response.setSkuId(inventory.getSkuId());
        response.setAvailableQuantity(inventory.getInventory());
        return response;
    }
}
