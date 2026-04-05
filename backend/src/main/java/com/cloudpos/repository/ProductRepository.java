package com.cloudpos.repository;

import com.cloudpos.entity.Product;
import com.cloudpos.entity.Store;
import com.cloudpos.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStore(Store store);
    List<Product> findByStoreId(Long storeId);
    List<Product> findByCategory(Category category);
    List<Product> findByStoreAndActiveTrue(Store store);
    Optional<Product> findByBarcode(String barcode);
    
    @Query("SELECT p FROM Product p WHERE p.store.id = :storeId AND (p.name LIKE %:query% OR p.barcode LIKE %:query%)")
    List<Product> searchProducts(@Param("storeId") Long storeId, @Param("query") String query);
}