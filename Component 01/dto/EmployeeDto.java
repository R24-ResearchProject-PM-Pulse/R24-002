package com.example.project_management.dto;

import com.example.project_management.model.EmpExpMap;
import com.example.project_management.model.EmpSkillMap;
import com.example.project_management.util.Gender;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private short age;
    private String role;
    private List<EmpExpMap> experience;
    private List<EmpSkillMap> skills;
    private double kpi;
}
