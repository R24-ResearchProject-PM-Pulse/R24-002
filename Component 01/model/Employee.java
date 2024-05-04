package com.example.project_management.model;

import com.example.project_management.util.Education;
import com.example.project_management.util.Gender;
import com.example.project_management.util.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long empId;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Education highestEducation;
    private short age;
    private Role role;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "employee_skills",
            joinColumns = { @JoinColumn(name = "employee_id") },
            inverseJoinColumns = { @JoinColumn(name = "skill_id") }
    )
    private Set<Skill> skills = new HashSet<>();
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "employee_exp",
            joinColumns = { @JoinColumn(name = "employee_id") },
            inverseJoinColumns = { @JoinColumn(name = "exp_id") }
    )
    private Set<Domain> experiences = new HashSet<>();
    private double kpi;
}
