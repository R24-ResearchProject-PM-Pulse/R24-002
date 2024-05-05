package com.example.project_management.repo;

import com.example.project_management.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainRepo extends JpaRepository<Domain,Long> {
}
