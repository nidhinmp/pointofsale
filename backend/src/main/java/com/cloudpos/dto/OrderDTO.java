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
public class OrderDTO {
    private Long id;
    private Long storeId;
    private Long branchId;
    private Long customerId;
    private Long cashierId;
    private String orderNumber;
    private BigDecimal subtotal;
    private String discountType;
    private BigDecimal discountValue;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal total;
    private String status;
    private String paymentMethod;
    private String notes;
    private List<OrderItemDTO> items;
    private String createdAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class OrderItemDTO {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal total;
}