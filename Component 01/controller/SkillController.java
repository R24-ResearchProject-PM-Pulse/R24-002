package com.example.project_management.controller;

import com.example.project_management.model.Skill;
import com.example.project_management.service.SkillService;
import com.example.project_management.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skill")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @PostMapping("/create")
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill){
        Skill returnedSkill = skillService.createSkill(skill);
        return new ResponseEntity<>(returnedSkill, HttpStatus.CREATED);
    }

    @GetMapping("/get/{role}")
    public ResponseEntity<List<Skill>> getSkills(@PathVariable(name = "role") String role){
        try {
            Role roleEnum = Role.valueOf(role);
            List<Skill> skills = skillService.getSkillsByRole(roleEnum);
            return new ResponseEntity<>(skills, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Handle invalid role gracefully
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
