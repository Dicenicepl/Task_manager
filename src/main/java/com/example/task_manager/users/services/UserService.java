package com.example.task_manager.users.services;

import com.example.task_manager.users.entities.*;
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

    private ProtectedUserData convertUserToProtectedUserData(User user){
        return new ProtectedUserData(user.getUsername(), user.getEmail());
    }

    public ResponseEntity<List<ProtectedUserData>> getAllUsers(){
        List<User> usersFromDB = userRepository.findAll();
        List<ProtectedUserData> readyUsers = new ArrayList<>();
        for (User rawUser: usersFromDB){
            readyUsers.add(convertUserToProtectedUserData(rawUser));
        }
        if (readyUsers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(readyUsers, HttpStatus.OK);
    }

    public ResponseEntity<ProtectedUserData> getUserByEmail(String email){
        User rawUser = userRepository.findUserByEmail(email);
        if (rawUser == null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(convertUserToProtectedUserData(rawUser), HttpStatus.OK);
    }

        public ResponseEntity<String> createUser(RegisterData registerData){
        User user = new User(registerData.getUsername(), registerData.getEmail(), registerData.getPassword());
        userRepository.save(user);
        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }

    public ResponseEntity<String> updateUser(UpdateUserData userData){
        User user = userRepository.findUserByEmail(userData.getEmail());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(userData.getPassword());
        user.setUsername(userData.getUsername());
        userRepository.save(user);
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteUser(DeleteUserData deleteUserData){
        User user = userRepository.findUserByEmail(deleteUserData.getEmail());
        if (user.getPassword().equals(deleteUserData.getPassword())) {
            userRepository.deleteById(user.getUser_id());
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Bad password", HttpStatus.BAD_REQUEST);
    }
}
