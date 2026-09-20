package com.example.demo.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Employee;
import com.example.demo.repository.Employeerepository;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

    private final Employeerepository employeeRepository;

    public EmployeeRestController(Employeerepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // POST - Save Employee
    @PostMapping
    public ResponseEntity<Employee> saveEmployee(
            @RequestBody Employee employee) {

        Employee savedEmployee = employeeRepository.save(employee);

        return new ResponseEntity<>(
                savedEmployee,
                HttpStatus.CREATED
        );
    }

    // GET - Get all Employees
    @GetMapping
    public ResponseEntity<Iterable<Employee>> getAllEmployees() {

        Iterable<Employee> employees = employeeRepository.findAll();

        return ResponseEntity.ok(employees);
    }

    // GET - Get Employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(
            @PathVariable int id) {

        Optional<Employee> employee =
                employeeRepository.findById(id);

        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        }

        return ResponseEntity.notFound().build();
    }

    // PUT - Update Employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable int id,
            @RequestBody Employee employeeDetails) {

        Optional<Employee> optionalEmployee =
                employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {

            Employee employee = optionalEmployee.get();

            employee.setEmpName(employeeDetails.getEmpName());
            employee.setDepartment(employeeDetails.getDepartment());
            employee.setSalary(employeeDetails.getSalary());

            Employee updatedEmployee =
                    employeeRepository.save(employee);

            return ResponseEntity.ok(updatedEmployee);
        }

        return ResponseEntity.notFound().build();
    }

    // DELETE - Delete Employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable int id) {

        if (!employeeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        employeeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}