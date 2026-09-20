package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Employee;

public interface Employeerepository 
        extends CrudRepository<Employee, Integer>,
        JpaRepository<Employee, Integer>{

}