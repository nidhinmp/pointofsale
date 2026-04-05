package com.cloudpos.repository;

import com.cloudpos.entity.Branch;
import com.cloudpos.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findByStore(Store store);
    List<Branch> findByStoreId(Long storeId);
}