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
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final StoreRepository storeRepository;

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> getEmployeesByBranch(Long branchId) {
        return employeeRepository.findByBranchId(branchId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> getEmployeesByStore(Long storeId) {
        return employeeRepository.findByStoreId(storeId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return mapToDTO(employee);
    }

    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        Employee employee = Employee.builder()
                .user(user)
                .branch(branch)
                .store(store)
                .employeeId(dto.getEmployeeId())
                .status(Employee.EmployeeStatus.valueOf(dto.getStatus()))
                .build();

        employee = employeeRepository.save(employee);
        return mapToDTO(employee);
    }

    @Transactional
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (dto.getStatus() != null) {
            employee.setStatus(Employee.EmployeeStatus.valueOf(dto.getStatus()));
        }

        employee = employeeRepository.save(employee);
        return mapToDTO(employee);
    }

    @Transactional
    public EmployeeDTO toggleEmployeeStatus(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setStatus(employee.getStatus() == Employee.EmployeeStatus.ACTIVE 
                ? Employee.EmployeeStatus.INACTIVE 
                : Employee.EmployeeStatus.ACTIVE);

        employee = employeeRepository.save(employee);
        return mapToDTO(employee);
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .userId(employee.getUser().getId())
                .userEmail(employee.getUser().getEmail())
                .firstName(employee.getUser().getFirstName())
                .lastName(employee.getUser().getLastName())
                .branchId(employee.getBranch().getId())
                .branchName(employee.getBranch().getName())
                .storeId(employee.getStore().getId())
                .storeName(employee.getStore().getName())
                .employeeId(employee.getEmployeeId())
                .hireDate(employee.getHireDate() != null ? employee.getHireDate().toString() : null)
                .status(employee.getStatus().name())
                .createdAt(employee.getCreatedAt() != null ? employee.getCreatedAt().toString() : null)
                .build();
    }
}