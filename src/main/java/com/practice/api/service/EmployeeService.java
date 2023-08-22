package com.practice.api.service;

import com.practice.api.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();

    Employee findById(int id);

    Employee addEmployee(Employee employee);

    Employee updateEmployee(int id,Employee employee);

     void deleteById(int id);

}
