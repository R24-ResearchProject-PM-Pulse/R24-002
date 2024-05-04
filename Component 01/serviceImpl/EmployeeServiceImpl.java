package com.example.project_management.serviceImpl;

import com.example.project_management.dto.EmployeeDto;
import com.example.project_management.mapper.Mapper;
import com.example.project_management.model.EmpSkillMap;
import com.example.project_management.model.Employee;
import com.example.project_management.model.Skill;
import com.example.project_management.repo.EmpExpRepo;
import com.example.project_management.repo.EmpSkillRepo;
import com.example.project_management.repo.EmployeeRepo;
import com.example.project_management.service.EmployeeService;
import com.example.project_management.util.KpiGenerationUtil;
import com.example.project_management.util.Role;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private EmpSkillRepo empSkillRepo;
    @Autowired
    private EmpExpRepo empExpRepo;
    @Autowired
    private KpiGenerationUtil kpi;
    @Autowired
    private Mapper mapper;

    @Override
    public Employee createEmployee(EmployeeDto employeeDto) {
        Employee emp = mapper.EmpDtoToEntity(employeeDto);
        emp = employeeRepo.save(emp);
        long id = emp.getEmpId();
        if(employeeDto.getSkills() != null && !employeeDto.getSkills().isEmpty()){
            employeeDto.getSkills().forEach(empSkillMap -> {
                empSkillMap.setEmpId(id);
            });
            empSkillRepo.saveAll(employeeDto.getSkills());
        }
        if(employeeDto.getExperience() != null && !employeeDto.getExperience().isEmpty()){
            employeeDto.getExperience().forEach(empExpMap -> {
                empExpMap.setEmpId(id);
            });
            empExpRepo.saveAll(employeeDto.getExperience());
        }
        return emp;
    }

    @Override
    public Employee generateKpi(long empId) {
        Employee emp = employeeRepo.findById(empId).orElseThrow(()-> new EntityNotFoundException("Employee Not Found"));
        emp.setKpi(kpi.generateKPI(emp.getEmpId()));
        return employeeRepo.save(emp);
    }

    @Override
    public Employee updateEmployee(EmployeeDto employeeDto) {
        Employee emp = mapper.EmpDtoToEntity(employeeDto);
        emp = employeeRepo.save(emp);
        long id = emp.getEmpId();
        if(employeeDto.getSkills() != null && !employeeDto.getSkills().isEmpty()){
            empSkillRepo.deleteAllByEmpId(employeeDto.getId());
            employeeDto.getSkills().forEach(empSkillMap -> {
                empSkillMap.setEmpId(id);
            });
            empSkillRepo.saveAll(employeeDto.getSkills());
        }
        if(employeeDto.getExperience() != null && !employeeDto.getExperience().isEmpty()){
            empExpRepo.deleteAllByEmpId(employeeDto.getId());
            employeeDto.getExperience().forEach(empExpMap -> {
                empExpMap.setEmpId(id);
            });
            empExpRepo.saveAll(employeeDto.getExperience());
        }
        return emp;
    }

    @Override
    public Boolean deleteEmployee(Long id) {
        try{
            employeeRepo.deleteById(id);
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    @Override
    public List<Employee> getEmployees(String role) {
        if(Role.valueOf(role).equals(Role.ALL)){
            return employeeRepo.findAll();
        }
        return employeeRepo.findAllByRole(Role.valueOf(role));
    }
}
