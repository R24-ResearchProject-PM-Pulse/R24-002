package com.example.project_management.repo;

import com.example.project_management.model.EmpProjMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpProjMapRepo extends JpaRepository<EmpProjMap,Long> {
    List<EmpProjMap> findAllByProjectId(long id);
}
