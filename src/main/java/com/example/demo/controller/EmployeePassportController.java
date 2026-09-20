package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.demo.entity.Employee;
import com.example.demo.repository.Employeerepository;
import java.util.List;


@RestController
@RequestMapping("/api/employees-passport")
public class EmployeePassportController {

    private final Employeerepository employeeRepository;

    public EmployeePassportController(
            Employeerepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping
    public ResponseEntity<Employee> create(
            @RequestBody Employee employee) {

        Employee saved =
                employeeRepository.save(employee);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @GetMapping
    public List<Employee> getAll() {

        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(
            @PathVariable int id) {

        return employeeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id) {

        if (!employeeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        employeeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}