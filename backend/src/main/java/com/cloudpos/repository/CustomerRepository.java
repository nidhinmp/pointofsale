package com.cloudpos.repository;

import com.cloudpos.entity.Customer;
import com.cloudpos.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByStore(Store store);
    List<Customer> findByStoreId(Long storeId);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhone(String phone);
}