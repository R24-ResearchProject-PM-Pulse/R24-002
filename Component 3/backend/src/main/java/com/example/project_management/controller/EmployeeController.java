package com.example.project_management.controller;

import com.example.project_management.dto.EmployeeDto;
import com.example.project_management.model.Employee;
import com.example.project_management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody  EmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.createEmployee(employeeDto), HttpStatus.CREATED);
    }

    @GetMapping("/{role}")
    public ResponseEntity<List<Employee>> getEmployees(@PathVariable(name = "role") String role){
        return new ResponseEntity<>(employeeService.getEmployees(role),HttpStatus.OK);
    }

    @GetMapping("/kpi/{id}")
    public ResponseEntity<Employee> getKpi(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(employeeService.generateKpi(id),HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody  EmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.createEmployee(employeeDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable(name="id") long id){
        boolean status = employeeService.deleteEmployee(id);
        if(status){
            return new ResponseEntity<>(true,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
    }
}
