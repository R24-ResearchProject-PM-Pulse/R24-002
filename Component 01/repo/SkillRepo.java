package com.example.project_management.repo;

import com.example.project_management.model.Skill;
import com.example.project_management.util.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepo extends JpaRepository<Skill,Long> {
    List<Skill> findByRolesContaining(Role role);
}
