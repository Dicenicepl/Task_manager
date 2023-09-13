package com.example.task_manager.service;

import com.example.task_manager.entity.user.User;
import com.example.task_manager.entity.event.EventRepository;
import com.example.task_manager.entity.user.UserDTO;
import com.example.task_manager.entity.user.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoAuthService {
    private final UserRepository userRepository;

    public NoAuthService(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> register(User user) {
        try {
            if (!user.getUsername().isBlank() || !user.getEmail().isBlank() || !user.getPassword().isBlank()) {
                userRepository.save(user);
                return new ResponseEntity<>("User name: " + user.getUsername() + "\nUser email: " + user.getEmail() + "\nis created", HttpStatus.CREATED);
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("User with this email is already exists", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ups, we got problem", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Eeee, lol even server developer don`t know what hapenned", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> getById(Long id) {
        try {
            User user = userRepository.findUserById(id);
            return new ResponseEntity<>("Here is your user: " + new UserDTO(user.getUsername(), user.getEmail(), null), HttpStatus.FOUND);
        } catch (NullPointerException e){
            return new ResponseEntity<>("We can`t find user with id: " + id, HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<String> getAll(){
        try {
            List<User> users = userRepository.findAll();
            List<UserDTO> userDTOList = new ArrayList<>();
            for (User user : users) {
                userDTOList.add(new UserDTO(user.getUsername(), user.getEmail(), null));
            }
            return new ResponseEntity<>("Here is your users:" + userDTOList, HttpStatus.OK);
        } catch (NullPointerException ignored){}
        return new ResponseEntity<>("We can`t find any users", HttpStatus.NOT_FOUND);

    }
}
