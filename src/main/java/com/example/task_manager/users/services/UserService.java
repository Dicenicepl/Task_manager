package com.example.task_manager.users.services;

import com.example.task_manager.users.entities.ProtectedUserData;
import com.example.task_manager.users.entities.RegisterData;
import com.example.task_manager.users.entities.User;
import com.example.task_manager.users.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<List<ProtectedUserData>> getAllUsers(){
        List<User> usersFromDB = userRepository.findAll();
        List<ProtectedUserData> readyUsers = new ArrayList<>();
        for (User rawUser: usersFromDB){
            readyUsers.add(new ProtectedUserData(rawUser.getUsername(), rawUser.getEmail()));
        }
        return new ResponseEntity<>(readyUsers, HttpStatus.OK);
    }

    //todo check isBlank
    public ResponseEntity<ProtectedUserData> getUserByEmail(String email){
        User rawUser = userRepository.findUserByEmail(email);
        return new ResponseEntity<>(new ProtectedUserData(rawUser.getUsername(), rawUser.getEmail()), HttpStatus.OK);
    }

    public ResponseEntity<String> createUser(RegisterData registerData){
        return null;
    }
}
