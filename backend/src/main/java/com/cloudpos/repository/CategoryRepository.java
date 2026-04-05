package com.cloudpos.repository;

import com.cloudpos.entity.Category;
import com.cloudpos.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByStore(Store store);
    List<Category> findByStoreId(Long storeId);
}