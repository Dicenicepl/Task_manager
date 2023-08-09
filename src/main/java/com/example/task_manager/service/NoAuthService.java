package com.example.task_manager.service;

import com.example.task_manager.entity.user.User;
import com.example.task_manager.entity.event.EventRepository;
import com.example.task_manager.entity.user.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoAuthService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public NoAuthService(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public ResponseEntity<String> register(User user) {
        try {
            if (!user.getUsername().isBlank() || !user.getEmail().isBlank() || !user.getPassword().isBlank()) {
                userRepository.save(user);
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Ups, we got exception" + e, HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("User with this email is already exists", HttpStatus.OK);
        }
        return new ResponseEntity<>("User name: " + user.getUsername()
                + "\nUser email: " + user.getEmail() + "\nis created", HttpStatus.CREATED);
    }

    public ResponseEntity<String> getById(Long id) {
        Optional<User> user = userRepository.findUserById(id);
        if (user.isPresent()){
            return new ResponseEntity<>("Here is your user: "
                    + user.get(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>("We can`t find user with id:" + id, HttpStatus.NOT_FOUND);
    }
    public ResponseEntity<String> getAll(){
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()){
            return new ResponseEntity<>("Here is your users:" + users, HttpStatus.OK);
        }
        return new ResponseEntity<>("We can`t find any users", HttpStatus.NOT_FOUND);
    }
}
