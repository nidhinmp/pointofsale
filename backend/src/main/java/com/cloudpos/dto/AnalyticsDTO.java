package com.cloudpos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsDTO {
    private Double totalSales;
    private Long totalOrders;
    private Double averageOrderValue;
    private Long completedOrders;
    private Long pendingOrders;
    private Long refundedOrders;
    private Double salesGrowth;
    private List<DailySalesDTO> dailySales;
    private Map<String, Double> paymentMethodBreakdown;
    private List<TopProductDTO> topProducts;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DailySalesDTO {
    private String date;
    private Double sales;
    private Long orders;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class TopProductDTO {
    private Long productId;
    private String productName;
    private Long quantitySold;
    private Double totalSales;
}