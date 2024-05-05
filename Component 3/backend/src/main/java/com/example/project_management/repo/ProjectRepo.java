package com.example.project_management.repo;

import com.example.project_management.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Project,Long> {
    List<Project> findAllByIsAccept(boolean accept);
    List<Project> findAllByIsComplete(boolean complete);
}
