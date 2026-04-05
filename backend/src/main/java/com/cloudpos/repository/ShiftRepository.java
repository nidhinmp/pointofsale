package com.cloudpos.repository;

import com.cloudpos.entity.Shift;
import com.cloudpos.entity.Branch;
import com.cloudpos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
    List<Shift> findByBranch(Branch branch);
    List<Shift> findByBranchId(Long branchId);
    Optional<Shift> findByUserAndStatus(User user, Shift.ShiftStatus status);
    Optional<Shift> findByUserIdAndStatus(Long userId, Shift.ShiftStatus status);
    List<Shift> findByStatus(Shift.ShiftStatus status);
}