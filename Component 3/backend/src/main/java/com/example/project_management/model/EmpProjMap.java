package com.example.project_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class EmpProjMap {
    @Id
    @GeneratedValue
    private long id;
    private long empId;
    private long projectId;
    private Date StartDate;
    private boolean isCompleted = false;
}
