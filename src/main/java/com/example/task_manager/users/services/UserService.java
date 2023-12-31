package com.example.task_manager.users.services;

import com.example.task_manager.users.entities.ProtectedUser;
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

    public ResponseEntity<List<ProtectedUser>> getAllUsers(){
        List<User> usersFromDB = userRepository.findAll();
        List<ProtectedUser> readyUsers = new ArrayList<>();
        for (User rawUser: usersFromDB){
            readyUsers.add(new ProtectedUser(rawUser.getUsername(), rawUser.getEmail()));
        }
        return new ResponseEntity<>(readyUsers, HttpStatus.OK);
    }

    //todo check isBlank
    public ResponseEntity<ProtectedUser> getUserByEmail(String email){
        User rawUser = userRepository.findUserByEmail(email);
        return new ResponseEntity<>(new ProtectedUser(rawUser.getUsername(), rawUser.getEmail()), HttpStatus.OK);
    }
}
