package com.example.project_management.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BasicProjectRequestDto {
    private String projectName;
    private String projectObjective;
    private Boolean mobile;
    private Boolean web;
    private Boolean desktop;
    private Boolean iot;
    private Double expectedBudget;
    private Date startDate;
    private Date expectedDate;
    private Integer expectedTeam;
    private long userId;
}
