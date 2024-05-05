package com.example.project_management.controller;

import com.example.project_management.dto.AcceptTeamDto;
import com.example.project_management.dto.BasicProjectRequestDto;
import com.example.project_management.dto.RiskLevelResponseDto;
import com.example.project_management.dto.TechProjectRequestDto;
import com.example.project_management.mapper.Mapper;
import com.example.project_management.model.Employee;
import com.example.project_management.model.Project;
import com.example.project_management.service.ProjectService;
import com.example.project_management.util.ProjectStatus;
import com.example.project_management.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private Mapper mapper;

    @PostMapping("/create")
    public ResponseEntity<RiskLevelResponseDto> createProject(@RequestBody BasicProjectRequestDto dto){
        return new ResponseEntity<>(projectService.createProject(mapper.BasicReqToProject(dto)), HttpStatus.CREATED);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Project>> getProjects(@PathVariable(name = "status") String attribute){
        return new ResponseEntity<>(projectService.getProjects(attribute),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(projectService.findProject(id),HttpStatus.OK);
    }

    @PatchMapping("/accept/{id}/{value}")
    public ResponseEntity<Project> acceptProject(@PathVariable(name = "id") long id , @PathVariable(name = "value") ProjectStatus status){
        if(status != null && status.equals(ProjectStatus.ACCEPT)){
            return new ResponseEntity<>(projectService.acceptProject(true,id),HttpStatus.BAD_REQUEST);
        }else if(status != null && status.equals(ProjectStatus.REJECTED)){
            return new ResponseEntity<>(projectService.acceptProject(false,id),HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(new Project(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/findTeam")
    public ResponseEntity<Map<Role,List<Employee>>> findTeam(@RequestBody TechProjectRequestDto dto){
        if(dto.getSkills() == null || dto.getSkills().isEmpty()  || dto.getRisk() ==null){
            return new ResponseEntity<>(new HashMap<>(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(projectService.findTeam(dto),HttpStatus.CREATED);
    }

    @PostMapping("/accept-team")
    public ResponseEntity<Project> acceptTeam(@RequestBody AcceptTeamDto dto){
        return new ResponseEntity<>(projectService.acceptTeam(dto),HttpStatus.ACCEPTED);
    }

    @PutMapping("/close/{projectId}")
    public ResponseEntity<Project> closeProject(@PathVariable(name = "projectId") long id){
        return new ResponseEntity<>(projectService.closeProject(id),HttpStatus.OK);
    }

}
