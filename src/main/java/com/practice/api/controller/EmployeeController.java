package com.practice.api.controller;

import com.practice.api.entity.Employee;
import com.practice.api.exception.EmployeeNotFoundException;
import com.practice.api.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        log.info("Fetching all employees");
        List<Employee> employees = employeeService.findAll();
        log.debug("Found {} employees", employees.size());
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id){
        log.info("Fetching employee with ID: {}", id);
        try {
            Employee employee = employeeService.findById(id);
            log.debug("Found employee: {}", employee);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            log.warn("Employee not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        log.info("Adding a new employee");
        if (employee.getId() != 0) {
            log.warn("Invalid input for new employee");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Invalid input
        }

        Employee newEmployee = employeeService.addEmployee(employee);
        log.debug("Added new employee: {}", newEmployee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee employee){
        log.info("Updating employee with ID: {}", id);
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            log.debug("Updated employee: {}", updatedEmployee);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            log.warn("Employee not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id){
        log.info("Deleting employee with ID: {}", id);
        try {
            employeeService.deleteById(id);
            log.debug("Employee with ID {} deleted", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmployeeNotFoundException e) {
            log.warn("Employee not found with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
