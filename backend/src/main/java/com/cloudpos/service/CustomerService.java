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
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<CustomerDTO> getCustomersByStore(Long storeId) {
        return customerRepository.findByStoreId(storeId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return mapToDTO(customer);
    }

    @Transactional
    public CustomerDTO createCustomer(CustomerDTO dto) {
        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        Customer customer = Customer.builder()
                .store(store)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();

        customer = customerRepository.save(customer);
        return mapToDTO(customer);
    }

    @Transactional
    public CustomerDTO updateCustomer(Long id, CustomerDTO dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (dto.getFirstName() != null) customer.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) customer.setLastName(dto.getLastName());
        if (dto.getEmail() != null) customer.setEmail(dto.getEmail());
        if (dto.getPhone() != null) customer.setPhone(dto.getPhone());
        if (dto.getLoyaltyPoints() != null) customer.setLoyaltyPoints(dto.getLoyaltyPoints());

        customer = customerRepository.save(customer);
        return mapToDTO(customer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    private CustomerDTO mapToDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .storeId(customer.getStore().getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .loyaltyPoints(customer.getLoyaltyPoints())
                .createdAt(customer.getCreatedAt() != null ? customer.getCreatedAt().toString() : null)
                .build();
    }
}