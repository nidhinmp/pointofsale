package com.cloudpos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDTO {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String logoUrl;
    private String subscriptionPlan;
    private String subscriptionExpiry;
    private String createdAt;
}