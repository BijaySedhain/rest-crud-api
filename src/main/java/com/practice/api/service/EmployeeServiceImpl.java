package com.practice.api.service;

import com.practice.api.dao.EmployeeDAO;
import com.practice.api.entity.Employee;
import com.practice.api.exception.EmployeeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Override
    public List<Employee> findAll() {
        log.info("Fetching all employees");
        return employeeDAO.findAll();
    }

    @Override
    @Transactional
    public Employee addEmployee(Employee employee) {
        log.info("Adding new employee: {}", employee);
        return employeeDAO.save(employee);
    }

    @Override
    public Employee updateEmployee(int id, Employee employee) {
        log.info("Updating employee with ID {}: {}", id, employee);
        Employee existingEmployee = employeeDAO.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found for the ID " + id));

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());

        return employeeDAO.save(existingEmployee);
    }

    @Override
    public Employee findById(int id) {
        log.info("Fetching employee by ID: {}", id);
        return employeeDAO.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found for the ID " + id));
    }

    @Override
    public void deleteById(int id) {
        log.info("Deleting employee with ID: {}", id);
        Optional<Employee> employeeOptional = employeeDAO.findById(id);
        if (employeeOptional.isPresent()) {
            employeeDAO.deleteById(id);
            log.info("Employee with ID {} has been deleted", id);
        } else {
            throw new EmployeeNotFoundException("Employee not found for the ID " + id);
        }
    }
}
