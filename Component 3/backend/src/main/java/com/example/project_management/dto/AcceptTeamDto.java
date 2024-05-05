package com.example.project_management.dto;

import lombok.Data;

import java.util.List;

@Data
public class AcceptTeamDto {
    private List<Long> team;
    private long projectId;
}
