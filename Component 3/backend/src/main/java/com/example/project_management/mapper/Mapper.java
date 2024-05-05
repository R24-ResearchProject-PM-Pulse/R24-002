package com.example.project_management.mapper;

import com.example.project_management.dto.BasicProjectRequestDto;
import com.example.project_management.dto.EmployeeDto;
import com.example.project_management.model.Employee;
import com.example.project_management.model.Project;
import com.example.project_management.repo.UserRepo;
import com.example.project_management.util.Gender;
import com.example.project_management.util.Risk;
import com.example.project_management.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Mapper {

    @Autowired
    private UserRepo userRepo;

    public Employee EmpDtoToEntity(EmployeeDto dto){
        Employee emp = new Employee();
        if(dto.getId() != null){
            emp.setEmpId(dto.getId());
        }
        emp.setFirstName(dto.getFirstName());
        emp.setLastName(dto.getLastName());
        emp.setGender(dto.getGender());
        emp.setAge(dto.getAge());
        emp.setRole(Role.valueOf(dto.getRole()));
        return emp;
    }

    public Project BasicReqToProject(BasicProjectRequestDto dto){
        Project project = new Project();
        project.setProjectName(dto.getProjectName());
        project.setProjectObjective(dto.getProjectObjective());
        project.setMobile(dto.getMobile());
        project.setWeb(dto.getWeb());
        project.setDesktop(dto.getDesktop());
        project.setIot(dto.getIot());
        project.setExpectedBudget(dto.getExpectedBudget());
        project.setExpectedDate(dto.getExpectedDate());
        project.setStartDate(dto.getStartDate());
        project.setExpectedTeam(dto.getExpectedTeam());
        project.setUser(dto.getUserId());
        return project;
    }
}
