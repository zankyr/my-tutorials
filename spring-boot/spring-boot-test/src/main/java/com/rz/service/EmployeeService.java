package com.rz.service;

import com.rz.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Employee getEmployeeByName(String name);
}
