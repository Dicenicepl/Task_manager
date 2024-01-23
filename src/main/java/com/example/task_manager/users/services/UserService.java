package com.example.task_manager.users.services;

import com.example.task_manager.roles.services.RoleService;
import com.example.task_manager.users.entities.*;
import com.example.task_manager.users.repositories.UserRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(RoleService roleService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private ProtectedUserDTO convertUserToProtectedUserData(User user){
        return new ProtectedUserDTO(user.getUsername(), user.getEmail());
    }
    public ResponseEntity<ProtectedUserDTO> getUserByEmail(String email){
        User rawUser = userRepository.findUserByEmailContaining(email);
        try{
            ProtectedUserDTO protectedUserDTO = convertUserToProtectedUserData(rawUser);
            return ResponseEntity.ok(protectedUserDTO);
        }catch (NullPointerException e) {
            return ResponseEntity.ok(null);
        }
    }
    public ResponseEntity<String> createUser(RegisterUserDTO registerUserDTO) {
        try{
            User user = new User(
                    registerUserDTO.getUsername(),
                    registerUserDTO.getEmail(),
                    passwordEncoder.encode(registerUserDTO.getPassword())
            );
            userRepository.save(user);
            roleService.addRole(registerUserDTO.getEmail());
            return new ResponseEntity<>("Saved", HttpStatus.OK);
        }catch (NullPointerException e){
            return ResponseEntity.badRequest().body("Null object");
        } catch (OptimisticLockingFailureException e){
            return ResponseEntity.badRequest().body("User with that email is already exist");
        }
    }

    public ResponseEntity<String> updateUser(UpdateUserDTO userData){
        try {
            User user = userRepository.findUserByEmail(userData.getEmail());
            user.setPassword(userData.getPassword());
            user.setUsername(userData.getUsername());
            userRepository.save(user);
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }catch (NullPointerException e ){
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    public ResponseEntity<String> deleteUser(DeleteUserDTO deleteUserDTO){
        User user = userRepository.findUserByEmail(deleteUserDTO.getEmail());
        try{
            if (user.getPassword().equals(deleteUserDTO.getPassword())) {
                userRepository.deleteById(user.getUser_id());
                return new ResponseEntity<>("Deleted", HttpStatus.OK);
            }
            return new ResponseEntity<>("Bad password", HttpStatus.BAD_REQUEST);
        }catch (NullPointerException e){
            return ResponseEntity.badRequest().body("Object not found");
        }
    }


    public User getUserFromEmail(String email){
        return userRepository.findUserByEmail(email);
    }
}
