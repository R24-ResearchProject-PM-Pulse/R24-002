package com.example.project_management.dto;

import com.example.project_management.model.Domain;
import com.example.project_management.model.Skill;
import com.example.project_management.util.Risk;
import com.example.project_management.util.Role;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TechProjectRequestDto {
    private long projectId;
    private List<Long> skills;
    private long domainId;
    private Risk risk;
    private Map<Role,Integer> teamCount;

}
