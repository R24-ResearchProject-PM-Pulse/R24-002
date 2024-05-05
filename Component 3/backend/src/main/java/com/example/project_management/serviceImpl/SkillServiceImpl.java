package com.example.project_management.serviceImpl;

import com.example.project_management.model.Skill;
import com.example.project_management.repo.SkillRepo;
import com.example.project_management.service.SkillService;
import com.example.project_management.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {
    @Autowired
    private SkillRepo skillRepo;
    @Override
    public List<Skill> getSkillsByRole(Role role) {
        if(Role.ALL.equals(role)){
            return skillRepo.findAll();
        }
        return skillRepo.findByRolesContaining(role);
    }

    @Override
    public Skill createSkill(Skill skill) {
        return skillRepo.save(skill);
    }
}
