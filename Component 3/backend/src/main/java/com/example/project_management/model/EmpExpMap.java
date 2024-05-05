package com.example.project_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class EmpExpMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long domainId;
    private long empId;
    private short months;
}
