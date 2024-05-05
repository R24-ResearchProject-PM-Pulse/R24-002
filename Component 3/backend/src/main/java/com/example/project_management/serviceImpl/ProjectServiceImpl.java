package com.example.project_management.serviceImpl;

import com.example.project_management.dto.AcceptTeamDto;
import com.example.project_management.dto.RiskLevelResponseDto;
import com.example.project_management.dto.TechProjectRequestDto;
import com.example.project_management.model.*;
import com.example.project_management.repo.*;
import com.example.project_management.service.ProjectService;
import com.example.project_management.util.ProjectStatus;
import com.example.project_management.util.Risk;
import com.example.project_management.util.Role;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleStatus;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private EmpSkillRepo empSkillRepo;
    @Autowired
    private EmpExpRepo empExpRepo;
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private EmpProjMapRepo empProjMapRepo;
    @Autowired
    private EmpProjMapRepo projMapRepo;

    @Override
    public RiskLevelResponseDto createProject(Project project) {
         projectRepo.save(project);
         RiskLevelResponseDto risk = new RiskLevelResponseDto();
         //TODO: send an api call to flask and get the risk
        risk.setRisk(Risk.AVARAGE_RISK);
        project.setRisk(risk.getRisk());
        projectRepo.save(project);
         return risk;
    }

    @Override
    public Project acceptProject(Boolean isAccept, long id) {
        Project project = projectRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Project not found"));
        project.setIsAccept(isAccept);
        if(isAccept){
            project.setIsComplete(false);
        }
        return projectRepo.save(project);
    }

    @Override
    public Project findProject(Long id) {
        return projectRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("project not exist"));
    }

    @Override
    public List<Project> getProjects(String attribute) {
        if(attribute != null && ProjectStatus.valueOf(attribute).equals(ProjectStatus.ALL) && attribute.isBlank()){
            return projectRepo.findAll();
        }else if(ProjectStatus.valueOf(attribute).equals(ProjectStatus.ACCEPT)){
            return projectRepo.findAllByIsAccept(true);
        }else if(ProjectStatus.valueOf(attribute).equals(ProjectStatus.REJECTED)){
            return projectRepo.findAllByIsAccept(false);
        }else if(ProjectStatus.valueOf(attribute).equals(ProjectStatus.ONGOING)){
            return projectRepo.findAllByIsComplete(false);
        }else if(ProjectStatus.valueOf(attribute).equals(ProjectStatus.COMPLETED)){
            return projectRepo.findAllByIsComplete(true);
        }else{
            return projectRepo.findAll();
        }
    }

    @Override
    public Map<Role, List<Employee>> findTeam(TechProjectRequestDto dto) {
        Project project = projectRepo.findById(dto.getProjectId()).orElseThrow();
        if(project.getExpectedTeam() < dto.getTeamCount().values().stream().mapToInt(Integer::intValue).sum()){
            throw new RuntimeException("team count mismatch");
        }
        List<EmpSkillMap> skillMap = empSkillRepo.findDistinctByEmployeeSkillsSkillIdIn(dto.getSkills());
        List<EmpExpMap> expMap = empExpRepo.findDistinctByEmployeeExpDomainIdIn(List.of(dto.getDomainId()));

        //Employees with Skill
        List<Long> empIds = skillMap.stream()
                .map(EmpSkillMap::getEmpId) // Extract empId from each EmpSkillMap
                .distinct() // Get distinct empIds
                .toList(); //  them into a list
        List<Long> empExpIds = expMap.stream()
                .map(EmpExpMap::getEmpId) // Extract empId from each EmpSkillMap
                .distinct() // Get distinct empIds
                .toList(); // Collect them into a list

        List<Employee> empList = employeeRepo.findAllById(empIds);
        Map<Role, List<Employee>> employeesByRole = empList.stream()
                .collect(Collectors.groupingBy(Employee::getRole));
        employeesByRole.forEach((role, employees) -> {
            int memberCount = dto.getTeamCount().getOrDefault(role, 0);
            if (memberCount < employees.size()) {
                // Filter out employees based on the comparison
                List<Employee> filteredEmployees = employees.stream()
                        .filter(employee -> empExpIds.contains(employee.getEmpId()))
                        .limit(memberCount) // Keep only up to memberCount employees
                        .collect(Collectors.toList());
                employeesByRole.put(role, filteredEmployees);
            }
        });

        return employeesByRole;

    }

    @Override
    public Project acceptTeam(AcceptTeamDto dto) {
        Project project = projectRepo.findById(dto.getProjectId()).orElseThrow(()-> new EntityNotFoundException("Project not found"));
        List<EmpProjMap> mapList = new ArrayList<>();
        dto.getTeam().forEach(emp->{
            EmpProjMap projMap = new EmpProjMap();
            projMap.setEmpId(emp);
            projMap.setProjectId(dto.getProjectId());
            projMap.setStartDate(project.getStartDate());
            projMap.setCompleted(false);
            mapList.add(projMap);
        });
        empProjMapRepo.saveAll(mapList);
        return project;
    }

    @Override
    public Project closeProject(long id) {
        Project project = findProject(id);
        project.setIsComplete(true);
        List<EmpProjMap> map = projMapRepo.findAllByProjectId(project.getId());
        map.forEach(empProjMap -> empProjMap.setCompleted(true));
        projMapRepo.saveAll(map);
        return projectRepo.save(project);
    }
}
