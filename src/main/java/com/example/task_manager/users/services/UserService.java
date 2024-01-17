package com.example.task_manager.users.services;

import com.example.task_manager.users.entities.*;
import com.example.task_manager.users.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
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
        if (!readyUsers.isEmpty()) {
            return new ResponseEntity<>(readyUsers, HttpStatus.OK);
        }
        return ResponseEntity.ok(null);

    }

    public ResponseEntity<ProtectedUserData> getUserByEmail(String email){
        User rawUser = userRepository.findUserByEmailContaining(email);
        if (rawUser != null){
            ProtectedUserData protectedUserData = new ProtectedUserData(rawUser.getUsername(), rawUser.getEmail());
            return ResponseEntity.ok(protectedUserData);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

        public ResponseEntity<String> createUser(RegisterUserData registerData) {
        try{
            if (registerData.getEmail() == null || registerData.getPassword() == null){
                throw new NullPointerException();
            }
            User user = new User(registerData.getUsername(), registerData.getEmail(), registerData.getPassword());
            userRepository.save(user);
            return new ResponseEntity<>("Saved", HttpStatus.OK);
        }catch (NullPointerException e){
            return ResponseEntity.badRequest().body("Wrong object");
        }catch (DataIntegrityViolationException e){
            return ResponseEntity.badRequest().body("User with that email is already exist");
        }

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
