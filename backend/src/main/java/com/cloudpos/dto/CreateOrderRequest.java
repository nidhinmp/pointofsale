package com.cloudpos.dto;

import com.cloudpos.entity.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    @NotNull(message = "Store ID is required")
    private Long storeId;

    @NotNull(message = "Branch ID is required")
    private Long branchId;

    private Long customerId;
    
    private String cashierEmail;

    @NotNull(message = "Items are required")
    private List<CreateOrderItemRequest> items;

    private String discountType;
    private BigDecimal discountValue;
    private String paymentMethod;
    private String notes;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class CreateOrderItemRequest {
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @NotNull(message = "Unit price is required")
    @Positive(message = "Unit price must be positive")
    private BigDecimal unitPrice;
}