package com.example.project_management.service;

import com.example.project_management.dto.EmployeeDto;
import com.example.project_management.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    Employee createEmployee(EmployeeDto employeeDto);
    Employee generateKpi(long empId);
    Employee updateEmployee(EmployeeDto employeeDto);
    Boolean deleteEmployee(Long id);
    List<Employee> getEmployees(String role);
}
