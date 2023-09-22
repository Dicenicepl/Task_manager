package com.example.task_manager.service;

import com.example.task_manager.entity.role.ERole;
import com.example.task_manager.entity.role.Role;
import com.example.task_manager.entity.role.RoleRepository;
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
    private final RoleRepository roleRepository;

    public NoAuthService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<String> register(User user) {
        try {
            if (user.getUsername() != null && user.getEmail() != null && user.getPassword() != null) {
                userRepository.save(new User(user.getUsername(), user.getEmail(), user.getPassword()));
                roleRepository.save(new ERole(user.getEmail(), "USER"));
                return new ResponseEntity<>("User name: " + user.getUsername() + "\nUser email: " + user.getEmail() + "\nis created", HttpStatus.CREATED);
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("User with this email is already exists", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ups, we got problem", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Eeee, lol even server developer don`t know what happened", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> getByEmail(String email) {
        try {
            Optional<User> user = userRepository.findUserByEmail(email);
            return new ResponseEntity<>("Here is your user: " + new UserDTO(user.get().getUsername(), user.get().getEmail(), roleRepository.findERoleByEmail(email).getRole()), HttpStatus.FOUND);
        } catch (NullPointerException e){
            return new ResponseEntity<>("We can`t find user with email: " + email, HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<String> getAll(){
        try {
            List<User> users = userRepository.findAll();
            List<UserDTO> userDTOList = new ArrayList<>();
            for (User user : users) {
                userDTOList.add(new UserDTO(
                        user.getUsername(),
                        user.getEmail(),
                        roleRepository.findERoleByEmail(user.getEmail()).getRole()));
            }
            return new ResponseEntity<>("Here is your users:" + userDTOList, HttpStatus.OK);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("We can`t find any users", HttpStatus.NOT_FOUND);
        }
    }
}
