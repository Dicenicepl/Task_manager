package com.example.task_manager.service;

import com.example.task_manager.entity.task.Task;
import com.example.task_manager.entity.task.TaskDTO;
import com.example.task_manager.entity.task.TaskRepository;
import com.example.task_manager.entity.role.ERole;
import com.example.task_manager.entity.role.RoleRepository;
import com.example.task_manager.entity.user.User;
import com.example.task_manager.entity.user.UserDTO;
import com.example.task_manager.entity.user.UserRepository;
import com.example.task_manager.security.PasswordHasher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final TaskRepository taskRepository;

    public NoAuthService(UserRepository userRepository, RoleRepository roleRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.taskRepository = taskRepository;
    }
    private Optional<User> getUserByEmail(String email){
        try{
            return userRepository.findUserByEmail(email);
        }catch (NullPointerException ignored){}
        return Optional.empty();
    }

    public ResponseEntity<String> register(UserDTO user) {
        String username = user.getUsername();
        String email = user.getEmail();
        String password = new PasswordHasher().Hasher(user.getPassword());
        try {
            if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                userRepository.save(new User(username, email, password));
                roleRepository.save(new ERole(email, "USER"));
                return new ResponseEntity<>("User name: " + user.getUsername() + "\nUser email: " + user.getEmail() + "\nis created", HttpStatus.OK);
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("User with this email is already exists", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ups, we got problem" + e.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Eeee, lol even server developer don`t know what happened", HttpStatus.OK);
    }

    public ResponseEntity<String> getByEmail(String email) {
        Optional<User> user = getUserByEmail(email);
        try {
            if (user.isPresent()) {
                UserDTO userDTO = new UserDTO(user.get().getUsername(), user.get().getEmail());
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(userDTO);
                return new ResponseEntity<>(json, HttpStatus.OK);
            }
        }catch (JsonProcessingException e) {
            System.out.println("GETBYEMAIL: We got exception on: "+e.getMessage());
            return new ResponseEntity<>("Error in translating to json", HttpStatus.OK);
        }
        return new ResponseEntity<>("We can`t find user with email: " + email, HttpStatus.OK);
    }
    public ResponseEntity<String> getAll(){
        try {
            List<User> users = userRepository.findAll();
            List<UserDTO> userDTOList = new ArrayList<>();
            for (User user : users) {
                userDTOList.add(new UserDTO(
                        user.getUsername(),
                        user.getEmail()));
            }
            return new ResponseEntity<>("Here is your users:" + userDTOList, HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>("We can`t find any users", HttpStatus.OK);
        }
    }

    public ResponseEntity<String> getEventByName(String name) {
        Task task = taskRepository.findEventsByName(name);
        try {
            if (task.getName().length() > 1) {
                TaskDTO sEvent = new TaskDTO(task.getOwner_email(), task.getName(), task.getDescription());
                return new ResponseEntity<>("Events:" + sEvent, HttpStatus.OK);
            }
        }catch (NullPointerException ignored){}
        return new ResponseEntity<>("We can`t find any event", HttpStatus.OK);
    }
}
