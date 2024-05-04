package com.example.project_management.repo;

import com.example.project_management.model.Employee;
import com.example.project_management.util.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    List<Employee> findAllByRole(Role role);
}
