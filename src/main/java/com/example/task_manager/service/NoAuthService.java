package com.example.task_manager.service;

import com.example.task_manager.entity.event.Event;
import com.example.task_manager.entity.event.EventDTO;
import com.example.task_manager.entity.role.ERole;
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
    private final EventRepository eventRepository;

    public NoAuthService(UserRepository userRepository, RoleRepository roleRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.eventRepository = eventRepository;
    }
    private Optional<User> getUserByEmail(String email){
        try{
            return userRepository.findUserByEmail(email);
        }catch (NullPointerException ignored){}
        return Optional.empty();
    }

    public ResponseEntity<String> register(User user) {
        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();
        try {
            if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                userRepository.save(new User(username, email, password));
                roleRepository.save(new ERole(email, "USER"));
                return new ResponseEntity<>("User name: " + user.getUsername() + "\nUser email: " + user.getEmail() + "\nis created", HttpStatus.CREATED);
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("User with this email is already exists", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ups, we got problem" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Eeee, lol even server developer don`t know what happened", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> getByEmail(String email) {
        try {
            Optional<User> user = getUserByEmail(email);
            if (user.isPresent()) {
                return new ResponseEntity<>("Here is your user: " + new UserDTO(user.get().getUsername(), user.get().getEmail()), HttpStatus.FOUND);
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>("We can`t find user with email: " + email, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("GETBYEMAIL: We got exception on: "+e.getMessage(), HttpStatus.OK);

        }
        return new ResponseEntity<>("XD, I will not debug this code because you found somehow this error", HttpStatus.OK);
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
            return new ResponseEntity<>("We can`t find any users", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> getEventByName(String name) {
        Event event = eventRepository.findEventsByName(name);
        if (event.getName().length() > 1) {
            EventDTO sEvent = new EventDTO(event.getOwner_email(), event.getName(), event.getDescription());
            return new ResponseEntity<>("Events:" + sEvent, HttpStatus.OK);
        }
        return new ResponseEntity<>("We can`t find any event", HttpStatus.OK);

    }
}
