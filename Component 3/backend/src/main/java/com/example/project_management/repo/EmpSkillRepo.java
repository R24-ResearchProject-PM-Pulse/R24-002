package com.example.project_management.repo;

import com.example.project_management.model.EmpSkillMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpSkillRepo extends JpaRepository<EmpSkillMap,Long> {
    List<EmpSkillMap> findAllByEmpId(long empId);
    @Query("SELECT DISTINCT e FROM EmpSkillMap e LEFT JOIN EmpProjMap p ON p.empId = e.empId WHERE (p IS NULL OR p.isCompleted = true) AND e.skillId IN :skillIds")
    List<EmpSkillMap> findDistinctByEmployeeSkillsSkillIdIn(@Param("skillIds") List<Long> skillIds);

    void deleteAllByEmpId(long id);
}
