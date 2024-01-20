package com.example.task_manager.users.services;

import com.example.task_manager.roles.services.RoleService;
import com.example.task_manager.users.entities.DeleteUserDTO;
import com.example.task_manager.users.entities.ProtectedUserDTO;
import com.example.task_manager.users.entities.UpdateUserDTO;
import com.example.task_manager.users.entities.User;
import com.example.task_manager.users.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;

    public UserService(RoleService roleService, UserRepository userRepository) {
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    private ProtectedUserDTO convertUserToProtectedUserData(User user){
        return new ProtectedUserDTO(user.getUsername(), user.getEmail());
    }

    public ResponseEntity<List<ProtectedUserDTO>> getAllUsers(){
        List<User> usersFromDB = userRepository.findAll();
        List<ProtectedUserDTO> readyUsers = new ArrayList<>();
        for (User rawUser: usersFromDB){
            readyUsers.add(convertUserToProtectedUserData(rawUser));
        }
        if (!readyUsers.isEmpty()) {
            return new ResponseEntity<>(readyUsers, HttpStatus.OK);
        }
        return ResponseEntity.ok(null);

    }

    public ResponseEntity<ProtectedUserDTO> getUserByEmail(String email){
        User rawUser = userRepository.findUserByEmailContaining(email);
        if (rawUser != null){
            ProtectedUserDTO protectedUserDTO = new ProtectedUserDTO(rawUser.getUsername(), rawUser.getEmail());
            return ResponseEntity.ok(protectedUserDTO);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }
        //todo create a better data checker
        public ResponseEntity<String> createUser(User user) {
        try{
            userRepository.save(user);
            roleService.addRole(user.getEmail());
            return new ResponseEntity<>("Saved", HttpStatus.OK);
        }catch (NullPointerException e){
            return ResponseEntity.badRequest().body("Wrong object");
        }catch (DataIntegrityViolationException e){
            return ResponseEntity.badRequest().body("User with that email is already exist");
        }

    }

    public ResponseEntity<String> updateUser(UpdateUserDTO userData){
        User user = userRepository.findUserByEmail(userData.getEmail());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(userData.getPassword());
        user.setUsername(userData.getUsername());
        userRepository.save(user);
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteUser(DeleteUserDTO deleteUserDTO){
        User user = userRepository.findUserByEmail(deleteUserDTO.getEmail());
        if (user.getPassword().equals(deleteUserDTO.getPassword())) {
            userRepository.deleteById(user.getUser_id());
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Bad password", HttpStatus.BAD_REQUEST);
    }
    public User getUserFromEmail(String email){
        return userRepository.findUserByEmail(email);
    }
}
