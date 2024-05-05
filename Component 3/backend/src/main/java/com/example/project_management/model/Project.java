package com.example.project_management.model;

import com.example.project_management.util.Risk;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Date;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String projectName;
    private String projectObjective;
    private Boolean mobile;
    private Boolean web;
    private Boolean desktop;
    private Boolean iot;
    private Double expectedBudget;
    private Date startDate;
    private Date expectedDate;
    private Date decidedDate;
    private Integer expectedTeam;
    private Risk risk;
    private Boolean isAccept = false;
    private Boolean isComplete = false;
    private long user;
}
