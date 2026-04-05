package com.cloudpos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private Long storeId;
    private Long categoryId;
    private String categoryName;
    private String name;
    private String description;
    private String barcode;
    private Double price;
    private Double costPrice;
    private String imageUrl;
    private Boolean active;
    private String createdAt;
}