package com.cloudpos.repository;

import com.cloudpos.entity.Inventory;
import com.cloudpos.entity.Branch;
import com.cloudpos.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductAndBranch(Product product, Branch branch);
    List<Inventory> findByBranch(Branch branch);
    List<Inventory> findByBranchId(Long branchId);
    
    @Query("SELECT i FROM Inventory i WHERE i.branch.id = :branchId AND i.quantity <= i.lowStockThreshold")
    List<Inventory> findLowStockByBranch(@Param("branchId") Long branchId);
}