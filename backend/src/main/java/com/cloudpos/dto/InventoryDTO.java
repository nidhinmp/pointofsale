package com.cloudpos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private Long id;
    private Long productId;
    private String productName;
    private String productBarcode;
    private Double productPrice;
    private Long branchId;
    private String branchName;
    private Integer quantity;
    private Integer lowStockThreshold;
    private Boolean isLowStock;
    private String updatedAt;
}