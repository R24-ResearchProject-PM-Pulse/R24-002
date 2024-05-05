package com.example.project_management.util;

import com.example.project_management.dto.EmpSkillInfo;
import com.example.project_management.model.EmpExpMap;
import com.example.project_management.model.EmpSkillMap;
import com.example.project_management.model.Employee;
import com.example.project_management.model.Skill;
import com.example.project_management.repo.EmpExpRepo;
import com.example.project_management.repo.EmpSkillRepo;
import com.example.project_management.repo.EmployeeRepo;
import com.example.project_management.repo.SkillRepo;
import com.example.project_management.service.SkillService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Data
public class KpiGenerationUtil {

    @Autowired
    private EmpSkillRepo empSkillRepo;
    @Autowired
    private SkillRepo skillRepo;
    @Autowired
    private EmpExpRepo empExpRepo;
    @Autowired
    EmployeeRepo employeeRepo;

    public double generateKPI(long empId){
        int analytical = 0;
        int problemSolving =0;
        int communication =0;
        int tech= 0;
        int years=0;
        int domain=0;
        int education=0;

        List<EmpSkillMap> skillsMap = empSkillRepo.findAllByEmpId(empId);
        List<EmpExpMap> expMap = empExpRepo.findAllByEmpId(empId);
        Employee emp = employeeRepo.findById(empId).orElseThrow();
        // Extract all skill IDs from EmpSkillMap
        Set<Long> skillIds = skillsMap.stream()
                .map(EmpSkillMap::getSkillId)
                .collect(Collectors.toSet());

        // Retrieve skills from skillRepo using all skill IDs at once
        List<Skill> skills = skillRepo.findAllById(skillIds); // Assuming findAllById() returns a list of Skill objects

        List<EmpSkillInfo> empSkillInfos = skillsMap.stream()
                .flatMap(empSkillMap ->
                        skills.stream()
                                .filter(skill -> empSkillMap.getSkillId() == skill.getSkillId())
                                .map(skill -> new EmpSkillInfo(
                                        empSkillMap.getSkillId(),
                                        skill.getSkill(),
                                        skill.getPoints(),
                                        empSkillMap.getLevel()
                                ))
                )
                .collect(Collectors.toList());

        for(EmpSkillInfo empSkillInfo : empSkillInfos){
            if("analytical".equalsIgnoreCase(empSkillInfo.getSkillName())) analytical = getValue(analytical,empSkillInfo);
            else if("problem solving".equalsIgnoreCase(empSkillInfo.getSkillName())) problemSolving = getValue(problemSolving,empSkillInfo);
            else if("Communication".equalsIgnoreCase(empSkillInfo.getSkillName())) communication = getValue(communication,empSkillInfo);
            else tech = getValue(tech,empSkillInfo);
        }

        for(EmpExpMap map : expMap){
            years = years + map.getMonths()/12;
        }
        if (years < 3) years = years * 3;
        else if(years < 6) years = years *5;
        else years =years * 7;

        if(emp.getHighestEducation() != null && emp.getHighestEducation().equals(Education.BSC)) education = education + 5;
        if(emp.getHighestEducation() != null && emp.getHighestEducation().equals(Education.MSC)) education = education + 7;


        return analytical * 0.2
                + communication * 0.1
                + tech * 0.15
                + problemSolving * 0.05
                + years * 0.3
                + education * 0.2;

    }

    private int getValue(int value , EmpSkillInfo skill){
        value= value + skill.getPoints();
        if(skill.getLevel().equals(Level.ADVANCE)) return value*5;
        if(skill.getLevel().equals(Level.INTERMEDIATE)) return value*3;
        return value;
    }

}
