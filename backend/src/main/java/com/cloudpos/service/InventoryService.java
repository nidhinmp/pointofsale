package com.cloudpos.service;

import com.cloudpos.dto.*;
import com.cloudpos.entity.*;
import com.cloudpos.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;

    public List<InventoryDTO> getAllInventory() {
        return inventoryRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<InventoryDTO> getInventoryByBranch(Long branchId) {
        return inventoryRepository.findByBranchId(branchId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<InventoryDTO> getLowStockByBranch(Long branchId) {
        return inventoryRepository.findLowStockByBranch(branchId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public InventoryDTO getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        return mapToDTO(inventory);
    }

    @Transactional
    public InventoryDTO updateStock(Long id, Integer quantity) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        inventory.setQuantity(quantity);
        inventory = inventoryRepository.save(inventory);
        return mapToDTO(inventory);
    }

    @Transactional
    public InventoryDTO createInventory(Long productId, Long branchId, Integer quantity, Integer lowStockThreshold) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        Inventory inventory = Inventory.builder()
                .product(product)
                .branch(branch)
                .quantity(quantity)
                .lowStockThreshold(lowStockThreshold)
                .build();

        inventory = inventoryRepository.save(inventory);
        return mapToDTO(inventory);
    }

    private InventoryDTO mapToDTO(Inventory inventory) {
        return InventoryDTO.builder()
                .id(inventory.getId())
                .productId(inventory.getProduct().getId())
                .productName(inventory.getProduct().getName())
                .productBarcode(inventory.getProduct().getBarcode())
                .productPrice(inventory.getProduct().getPrice().doubleValue())
                .branchId(inventory.getBranch().getId())
                .branchName(inventory.getBranch().getName())
                .quantity(inventory.getQuantity())
                .lowStockThreshold(inventory.getLowStockThreshold())
                .isLowStock(inventory.getQuantity() <= inventory.getLowStockThreshold())
                .updatedAt(inventory.getUpdatedAt() != null ? inventory.getUpdatedAt().toString() : null)
                .build();
    }
}