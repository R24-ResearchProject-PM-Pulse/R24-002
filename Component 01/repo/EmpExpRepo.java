package com.example.project_management.repo;

import com.example.project_management.model.EmpExpMap;
import com.example.project_management.model.EmpSkillMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpExpRepo extends JpaRepository<EmpExpMap,Long> {
    List<EmpExpMap> findAllByEmpId(long id);
    @Query("SELECT DISTINCT e FROM EmpExpMap e WHERE e.domainId IN :domain")
    List<EmpExpMap> findDistinctByEmployeeExpDomainIdIn(@Param("domain") List<Long> domain);
    void deleteAllByEmpId(long id);
}
