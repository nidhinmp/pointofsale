package com.cloudpos.service;

import com.cloudpos.dto.*;
import com.cloudpos.entity.*;
import com.cloudpos.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final CustomerRepository customerRepository;

    public AnalyticsDTO getBranchAnalytics(Long branchId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        LocalDateTime weekAgo = now.minusDays(7);
        LocalDateTime lastWeekAgo = weekAgo.minusDays(7);

        List<Order> todayOrders = orderRepository.findByBranchIdAndDateRange(branchId, startOfDay, now);
        List<Order> weekOrders = orderRepository.findByBranchIdAndDateRange(branchId, weekAgo, now);
        List<Order> lastWeekOrders = orderRepository.findByBranchIdAndDateRange(branchId, lastWeekAgo, weekAgo);

        double todaySales = todayOrders.stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.COMPLETED)
                .mapToDouble(o -> o.getTotal().doubleValue())
                .sum();

        double weekSales = weekOrders.stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.COMPLETED)
                .mapToDouble(o -> o.getTotal().doubleValue())
                .sum();

        double lastWeekSales = lastWeekOrders.stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.COMPLETED)
                .mapToDouble(o -> o.getTotal().doubleValue())
                .sum();

        long completedOrders = todayOrders.stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.COMPLETED)
                .count();

        long pendingOrders = todayOrders.stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.PENDING)
                .count();

        long refundedOrders = todayOrders.stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.REFUNDED)
                .count();

        double avgOrderValue = completedOrders > 0 ? todaySales / completedOrders : 0;

        double salesGrowth = lastWeekSales > 0 ? ((weekSales - lastWeekSales) / lastWeekSales) * 100 : 0;

        Map<String, Double> paymentBreakdown = weekOrders.stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.COMPLETED)
                .filter(o -> o.getPaymentMethod() != null)
                .collect(Collectors.groupingBy(
                        o -> o.getPaymentMethod().name(),
                        Collectors.summingDouble(o -> o.getTotal().doubleValue())
                ));

        List<AnalyticsDTO.DailySalesDTO> dailySales = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDateTime dayStart = now.minusDays(i).toLocalDate().atStartOfDay();
            LocalDateTime dayEnd = dayStart.plusDays(1);
            List<Order> dayOrders = orderRepository.findByBranchIdAndDateRange(branchId, dayStart, dayEnd);
            double dayTotal = dayOrders.stream()
                    .filter(o -> o.getStatus() == Order.OrderStatus.COMPLETED)
                    .mapToDouble(o -> o.getTotal().doubleValue()).sum();
            dailySales.add(AnalyticsDTO.DailySalesDTO.builder()
                    .date(dayStart.toLocalDate().toString())
                    .sales(dayTotal)
                    .orders(dayOrders.stream().filter(o -> o.getStatus() == Order.OrderStatus.COMPLETED).count())
                    .build());
        }

        List<AnalyticsDTO.TopProductDTO> topProducts = new ArrayList<>();

        return AnalyticsDTO.builder()
                .totalSales(todaySales)
                .totalOrders((long) todayOrders.size())
                .averageOrderValue(avgOrderValue)
                .completedOrders(completedOrders)
                .pendingOrders(pendingOrders)
                .refundedOrders(refundedOrders)
                .salesGrowth(salesGrowth)
                .dailySales(dailySales)
                .paymentMethodBreakdown(paymentBreakdown)
                .topProducts(topProducts)
                .build();
    }

    public AnalyticsDTO getStoreAnalytics(Long storeId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();

        List<Order> todayOrders = orderRepository.findByStoreIdAndDateRange(storeId, startOfDay, now);

        double todaySales = todayOrders.stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.COMPLETED)
                .mapToDouble(o -> o.getTotal().doubleValue())
                .sum();

        long completedOrders = todayOrders.stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.COMPLETED)
                .count();

        double avgOrderValue = completedOrders > 0 ? todaySales / completedOrders : 0;

        long customerCount = customerRepository.findByStoreId(storeId).size();

        Map<String, Double> paymentBreakdown = todayOrders.stream()
                .filter(o -> o.getStatus() == Order.OrderStatus.COMPLETED)
                .filter(o -> o.getPaymentMethod() != null)
                .collect(Collectors.groupingBy(
                        o -> o.getPaymentMethod().name(),
                        Collectors.summingDouble(o -> o.getTotal().doubleValue())
                ));

        List<AnalyticsDTO.DailySalesDTO> dailySales = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDateTime dayStart = now.minusDays(i).toLocalDate().atStartOfDay();
            LocalDateTime dayEnd = dayStart.plusDays(1);
            List<Order> dayOrders = orderRepository.findByStoreIdAndDateRange(storeId, dayStart, dayEnd);
            double dayTotal = dayOrders.stream()
                    .filter(o -> o.getStatus() == Order.OrderStatus.COMPLETED)
                    .mapToDouble(o -> o.getTotal().doubleValue()).sum();
            dailySales.add(AnalyticsDTO.DailySalesDTO.builder()
                    .date(dayStart.toLocalDate().toString())
                    .sales(dayTotal)
                    .orders(dayOrders.stream().filter(o -> o.getStatus() == Order.OrderStatus.COMPLETED).count())
                    .build());
        }

        return AnalyticsDTO.builder()
                .totalSales(todaySales)
                .totalOrders((long) todayOrders.size())
                .averageOrderValue(avgOrderValue)
                .completedOrders(completedOrders)
                .paymentMethodBreakdown(paymentBreakdown)
                .dailySales(dailySales)
                .build();
    }
}