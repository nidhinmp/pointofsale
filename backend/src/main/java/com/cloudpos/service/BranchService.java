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
public class BranchService {

    private final BranchRepository branchRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public List<BranchDTO> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<BranchDTO> getBranchesByStore(Long storeId) {
        return branchRepository.findByStoreId(storeId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public BranchDTO getBranchById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        return mapToDTO(branch);
    }

    @Transactional
    public BranchDTO createBranch(BranchDTO dto) {
        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));
        
        Branch branch = Branch.builder()
                .store(store)
                .name(dto.getName())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .build();
        
        if (dto.getManagerId() != null) {
            User manager = userRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
            branch.setManager(manager);
        }
        
        branch = branchRepository.save(branch);
        return mapToDTO(branch);
    }

    @Transactional
    public BranchDTO updateBranch(Long id, BranchDTO dto) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        
        if (dto.getName() != null) branch.setName(dto.getName());
        if (dto.getAddress() != null) branch.setAddress(dto.getAddress());
        if (dto.getPhone() != null) branch.setPhone(dto.getPhone());
        if (dto.getEmail() != null) branch.setEmail(dto.getEmail());
        
        if (dto.getManagerId() != null) {
            User manager = userRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
            branch.setManager(manager);
        }
        
        branch = branchRepository.save(branch);
        return mapToDTO(branch);
    }

    @Transactional
    public void deleteBranch(Long id) {
        branchRepository.deleteById(id);
    }

    private BranchDTO mapToDTO(Branch branch) {
        return BranchDTO.builder()
                .id(branch.getId())
                .storeId(branch.getStore().getId())
                .storeName(branch.getStore().getName())
                .name(branch.getName())
                .address(branch.getAddress())
                .phone(branch.getPhone())
                .email(branch.getEmail())
                .managerId(branch.getManager() != null ? branch.getManager().getId() : null)
                .managerName(branch.getManager() != null ? branch.getManager().getFirstName() + " " + branch.getManager().getLastName() : null)
                .createdAt(branch.getCreatedAt() != null ? branch.getCreatedAt().toString() : null)
                .build();
    }
}