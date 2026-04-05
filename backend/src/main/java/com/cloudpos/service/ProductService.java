package com.cloudpos.service;

import com.cloudpos.dto.*;
import com.cloudpos.entity.*;
import com.cloudpos.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final InventoryRepository inventoryRepository;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByStore(Long storeId) {
        return productRepository.findByStoreId(storeId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return productRepository.findByCategory(category).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapToDTO(product);
    }

    public List<ProductDTO> searchProducts(Long storeId, String query) {
        return productRepository.searchProducts(storeId, query).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO dto) {
        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        Product product = Product.builder()
                .store(store)
                .name(dto.getName())
                .description(dto.getDescription())
                .barcode(dto.getBarcode())
                .price(BigDecimal.valueOf(dto.getPrice()))
                .costPrice(dto.getCostPrice() != null ? BigDecimal.valueOf(dto.getCostPrice()) : null)
                .imageUrl(dto.getImageUrl())
                .active(dto.getActive() != null ? dto.getActive() : true)
                .build();

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }

        product = productRepository.save(product);
        return mapToDTO(product);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (dto.getName() != null) product.setName(dto.getName());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (dto.getBarcode() != null) product.setBarcode(dto.getBarcode());
        if (dto.getPrice() != null) product.setPrice(BigDecimal.valueOf(dto.getPrice()));
        if (dto.getCostPrice() != null) product.setCostPrice(BigDecimal.valueOf(dto.getCostPrice()));
        if (dto.getImageUrl() != null) product.setImageUrl(dto.getImageUrl());
        if (dto.getActive() != null) product.setActive(dto.getActive());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }

        product = productRepository.save(product);
        return mapToDTO(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDTO mapToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .storeId(product.getStore().getId())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .name(product.getName())
                .description(product.getDescription())
                .barcode(product.getBarcode())
                .price(product.getPrice().doubleValue())
                .costPrice(product.getCostPrice() != null ? product.getCostPrice().doubleValue() : null)
                .imageUrl(product.getImageUrl())
                .active(product.isActive())
                .createdAt(product.getCreatedAt() != null ? product.getCreatedAt().toString() : null)
                .build();
    }
}