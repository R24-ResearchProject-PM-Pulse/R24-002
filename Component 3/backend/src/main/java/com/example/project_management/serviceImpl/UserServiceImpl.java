package com.example.project_management.serviceImpl;

import com.example.project_management.config.PasswordEncryptor;
import com.example.project_management.model.User;
import com.example.project_management.repo.UserRepo;
import com.example.project_management.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public User createUser(User user) {
        if(user.getUsername().isBlank() || user.getUsername().isEmpty()){
            return null;
        }
        user.setPassword(PasswordEncryptor.encryptPassword(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return null;
    }

    @Override
    public User login(User user) {
        User returnedUser =  userRepo.findByUsername(user.getUsername()).orElseThrow(EntityNotFoundException::new);
        boolean status  = PasswordEncryptor.verifyPassword(user.getPassword(), returnedUser.getPassword());
        if(status){
            return returnedUser;
        }else{
            return null;
        }
    }

    @Override
    public User updateUser(String username) {
        return null;
    }
}
