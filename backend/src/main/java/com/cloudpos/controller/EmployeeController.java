package com.cloudpos.controller;

import com.cloudpos.dto.*;
import com.cloudpos.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(employeeService.getEmployeesByBranch(branchId));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(employeeService.getEmployeesByStore(storeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO dto) {
        return ResponseEntity.ok(employeeService.createEmployee(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO dto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EmployeeDTO> toggleEmployeeStatus(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.toggleEmployeeStatus(id));
    }
}