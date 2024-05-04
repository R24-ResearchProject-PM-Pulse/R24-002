package com.example.project_management.dto;

import com.example.project_management.util.Level;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmpSkillInfo {
    private long skillId;
    private String skillName;
    private int points;
    private Level level;

    // Override toString() method for debugging or printing
    @Override
    public String toString() {
        return "EmpSkillInfo{" +
                "skillId=" + skillId +
                ", skillName='" + skillName + '\'' +
                ", points=" + points +
                ", level=" + level +
                '}';
    }
}

