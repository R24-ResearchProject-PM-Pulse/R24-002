package com.example.project_management.service;

import com.example.project_management.model.Skill;
import com.example.project_management.util.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SkillService {
    List<Skill> getSkillsByRole(Role role);
    Skill createSkill(Skill skill);
}
