package com.example.project_management.service;

import com.example.project_management.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User createUser(User user);
    List<User> findAllUsers();
    User login(User user);
    User updateUser(String username);
}
