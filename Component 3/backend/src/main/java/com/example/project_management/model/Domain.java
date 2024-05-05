package com.example.project_management.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long domainId;
    private String domain;
//    @ManyToMany(mappedBy = "experiences")
//    private Set<Employee> employees = new HashSet<>();
}
