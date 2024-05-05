package com.example.project_management.model;

import com.example.project_management.util.Level;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class EmpSkillMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long skillId;
    private long empId;
    private Level level;
}
