package com.example.project_management.service;

import com.example.project_management.dto.AcceptTeamDto;
import com.example.project_management.dto.RiskLevelResponseDto;
import com.example.project_management.dto.TechProjectRequestDto;
import com.example.project_management.model.Employee;
import com.example.project_management.model.Project;
import com.example.project_management.util.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ProjectService{
    RiskLevelResponseDto createProject(Project project);
    Project acceptProject(Boolean isAccept,long id);
    Project findProject(Long id);
    List<Project> getProjects(String attribute);
    Map<Role, List<Employee>> findTeam(TechProjectRequestDto dto);
    Project acceptTeam(AcceptTeamDto dto);
    Project closeProject(long id);
}
