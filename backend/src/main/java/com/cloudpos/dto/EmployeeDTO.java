package com.cloudpos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    private Long userId;
    private String userEmail;
    private String firstName;
    private String lastName;
    private Long branchId;
    private String branchName;
    private Long storeId;
    private String storeName;
    private String employeeId;
    private String hireDate;
    private String status;
    private String createdAt;
}