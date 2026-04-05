package com.cloudpos.repository;

import com.cloudpos.entity.Order;
import com.cloudpos.entity.Branch;
import com.cloudpos.entity.Store;
import com.cloudpos.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBranch(Branch branch);
    List<Order> findByBranchId(Long branchId);
    List<Order> findByStore(Store store);
    List<Order> findByStoreId(Long storeId);
    List<Order> findByCashier(User cashier);
    List<Order> findByCustomerId(Long customerId);
    Optional<Order> findByOrderNumber(String orderNumber);
    Page<Order> findByBranchId(Long branchId, Pageable pageable);
    
    @Query("SELECT o FROM Order o WHERE o.branch.id = :branchId AND o.createdAt >= :startDate AND o.createdAt <= :endDate")
    List<Order> findByBranchIdAndDateRange(@Param("branchId") Long branchId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT o FROM Order o WHERE o.store.id = :storeId AND o.createdAt >= :startDate AND o.createdAt <= :endDate")
    List<Order> findByStoreIdAndDateRange(@Param("storeId") Long storeId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.branch.id = :branchId AND o.status = :status")
    Long countByBranchIdAndStatus(@Param("branchId") Long branchId, @Param("status") Order.OrderStatus status);
    
    @Query("SELECT SUM(o.total) FROM Order o WHERE o.branch.id = :branchId AND o.status = 'COMPLETED' AND o.createdAt >= :startDate")
    Double sumSalesByBranchId(@Param("branchId") Long branchId, @Param("startDate") LocalDateTime startDate);
}