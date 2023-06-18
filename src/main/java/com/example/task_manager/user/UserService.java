package com.example.task_manager.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> getById(Long id) {
        User user = userRepository.findUserById(id);
        try {
            return new ResponseEntity<>("Here is your user: "
                    + user, HttpStatus.FOUND);
        } catch (NullPointerException e) {
            return new ResponseEntity<>("We can`t find user with id:" + id, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> getAll() {
        List<User> users = userRepository.findAll();
        if (users.size() != 0) {
            return new ResponseEntity<>("Here is your users: "
                    + users, HttpStatus.OK);
        }
        return new ResponseEntity<>("We can`t find any users", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> save(User user) {
        try {
            if (!user.getUsername().isBlank() || !user.getEmail().isBlank() || !user.getPassword().isBlank()) {
                userRepository.save(user);
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Ups, we got exception" + e, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User name: " + user.getUsername()
                + "\nUser email: " + user.getEmail() + "\nis created", HttpStatus.CREATED);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
