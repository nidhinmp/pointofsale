package com.cloudpos.repository;

import com.cloudpos.entity.Employee;
import com.cloudpos.entity.Branch;
import com.cloudpos.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByBranch(Branch branch);
    List<Employee> findByBranchId(Long branchId);
    List<Employee> findByStore(Store store);
    List<Employee> findByStoreId(Long storeId);
    List<Employee> findByStatus(Employee.EmployeeStatus status);
    Optional<Employee> findByUserId(Long userId);
    Optional<Employee> findByEmployeeId(String employeeId);
}