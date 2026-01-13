package com.example.inventory.controller;

import com.example.inventory.api.InventoryApi;
import com.example.inventory.api.model.InventoryResponse;
import com.example.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InventoryController implements InventoryApi {

    private final InventoryService inventoryService;

    @Override
    public ResponseEntity<InventoryResponse> getInventory(String skuId) {
        log.info("Inventory request received for SKU {}", skuId);

        InventoryResponse response =
                inventoryService.getInventoryBySku(skuId);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<InventoryResponse> reduceInventory(String skuId, Integer quantity) {
        log.info(
                "Inventory reduce request received for SKU {} with quantity {}",
                skuId,
                quantity
        );

        InventoryResponse response =
                inventoryService.reduceInventory(skuId, quantity);

        return ResponseEntity.ok(response);
    }
}
