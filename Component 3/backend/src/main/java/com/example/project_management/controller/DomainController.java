package com.example.project_management.controller;

import com.example.project_management.model.Domain;
import com.example.project_management.repo.DomainRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/domain")
public class DomainController {

    @Autowired
    private DomainRepo domainRepo;

    @GetMapping
    public ResponseEntity<List<Domain>> getDomains(){
        return new ResponseEntity<>(domainRepo.findAll(), HttpStatus.OK);
    }

}
