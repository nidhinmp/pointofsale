package com.cloudpos.controller;

import com.cloudpos.dto.*;
import com.cloudpos.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<AnalyticsDTO> getBranchAnalytics(@PathVariable Long branchId) {
        return ResponseEntity.ok(analyticsService.getBranchAnalytics(branchId));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<AnalyticsDTO> getStoreAnalytics(@PathVariable Long storeId) {
        return ResponseEntity.ok(analyticsService.getStoreAnalytics(storeId));
    }
}