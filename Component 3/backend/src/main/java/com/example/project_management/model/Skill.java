package com.example.project_management.model;

import com.example.project_management.util.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long skillId;
    @Column(unique = true)
    private String skill;
    private int points;
    //append by semi colon
    @ElementCollection
    private Set<Role> roles;
}
