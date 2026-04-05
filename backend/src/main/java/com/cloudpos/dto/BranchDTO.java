package com.cloudpos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchDTO {
    private Long id;
    private Long storeId;
    private String storeName;
    private String name;
    private String address;
    private String phone;
    private String email;
    private Long managerId;
    private String managerName;
    private String createdAt;
}