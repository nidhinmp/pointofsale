package com.cloudpos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private Long storeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer loyaltyPoints;
    private String createdAt;
}