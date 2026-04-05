package com.cloudpos.service;

import com.cloudpos.dto.*;
import com.cloudpos.entity.*;
import com.cloudpos.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public List<StoreDTO> getAllStores() {
        return storeRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public StoreDTO getStoreById(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found"));
        return mapToDTO(store);
    }

    @Transactional
    public StoreDTO createStore(StoreDTO dto) {
        Store store = Store.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .logoUrl(dto.getLogoUrl())
                .subscriptionPlan(Store.SubscriptionPlan.valueOf(dto.getSubscriptionPlan()))
                .build();
        
        store = storeRepository.save(store);
        return mapToDTO(store);
    }

    @Transactional
    public StoreDTO updateStore(Long id, StoreDTO dto) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found"));
        
        if (dto.getName() != null) store.setName(dto.getName());
        if (dto.getAddress() != null) store.setAddress(dto.getAddress());
        if (dto.getPhone() != null) store.setPhone(dto.getPhone());
        if (dto.getEmail() != null) store.setEmail(dto.getEmail());
        if (dto.getSubscriptionPlan() != null) store.setSubscriptionPlan(Store.SubscriptionPlan.valueOf(dto.getSubscriptionPlan()));
        
        store = storeRepository.save(store);
        return mapToDTO(store);
    }

    @Transactional
    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }

    private StoreDTO mapToDTO(Store store) {
        return StoreDTO.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .phone(store.getPhone())
                .email(store.getEmail())
                .logoUrl(store.getLogoUrl())
                .subscriptionPlan(store.getSubscriptionPlan().name())
                .subscriptionExpiry(store.getSubscriptionExpiry() != null ? store.getSubscriptionExpiry().toString() : null)
                .createdAt(store.getCreatedAt() != null ? store.getCreatedAt().toString() : null)
                .build();
    }
}