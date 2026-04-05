package com.cloudpos.controller;

import com.cloudpos.dto.*;
import com.cloudpos.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<InventoryDTO>> getInventoryByBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(inventoryService.getInventoryByBranch(branchId));
    }

    @GetMapping("/branch/{branchId}/low-stock")
    public ResponseEntity<List<InventoryDTO>> getLowStockByBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(inventoryService.getLowStockByBranch(branchId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getInventoryById(id));
    }

    @PostMapping
    public ResponseEntity<InventoryDTO> createInventory(
            @RequestParam Long productId,
            @RequestParam Long branchId,
            @RequestParam Integer quantity,
            @RequestParam(required = false) Integer lowStockThreshold) {
        return ResponseEntity.ok(inventoryService.createInventory(productId, branchId, quantity, lowStockThreshold));
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<InventoryDTO> updateStock(@PathVariable Long id, @RequestParam Integer quantity) {
        return ResponseEntity.ok(inventoryService.updateStock(id, quantity));
    }
}