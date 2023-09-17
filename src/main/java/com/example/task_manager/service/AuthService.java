package com.example.task_manager.service;

import com.example.task_manager.entity.event.Event;
import com.example.task_manager.entity.event.EventDTO;
import com.example.task_manager.entity.event.EventRepository;
import com.example.task_manager.entity.role.Role;
import com.example.task_manager.entity.role.RoleRepository;
import com.example.task_manager.entity.user.User;
import com.example.task_manager.entity.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.*;

@Service
public class AuthService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthService(EventRepository eventRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    private String generatorToken(Long id) {
        String[] array = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            token.append(array[random.nextInt(array.length)]);
        }
        userRepository.addToken(id, String.valueOf(token));
        return token.toString();
    }

    private void updateExpireTimeToken(String token) {
        try {
            userRepository.updateExpireTokenToActive(token, new Time(System.currentTimeMillis() + 30000L));
        } catch (Exception e) {
            System.out.println("updateExpireTimeToken: " + e);
        }
    }

    private boolean isEnableModifyEvents(Long id, String token) {
        Optional<User> user = userRepository.findUserByToken(token);
        if (user.isEmpty()) {
            System.out.println("ISENABLEMODIFY: NULL");
            return false;
        }

        Role userRole = roleRepository.findERoleByEmail(user.get().getEmail()).getRole();
        Optional<Event> event = eventRepository.findById(id);

        if (event.isEmpty()) {
            return false;
        }

        String userEmail = user.get().getEmail();
        String eventEmail = event.get().getEmail();

        return userEmail.equals(eventEmail) || userRole.equals(Role.ADMIN);
    }
    private boolean isEnableModifyUsers(Long id, String token) {
        Optional<User> user = userRepository.findUserByToken(token);
        if (user.isEmpty()) {
            System.out.println("ISENABLEMODIFY: NULL");
            return false;
        }
        Role userRole = roleRepository.findERoleByEmail(user.get().getEmail()).getRole();
        return user.get().getId().equals(id) || userRole.equals(Role.ADMIN);
    }

    public String login(String email, String password) {
        User user = userRepository.findUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            String token = generatorToken(user.getId());
            updateExpireTimeToken(token);
            return token;
        } else return "We can`t find user";
    }

    public ResponseEntity<String> deleteUser(Long idUserToDelete, String token) {
        updateExpireTimeToken(token);
        if (isEnableModifyUsers(idUserToDelete,token)) {
            userRepository.deleteById(idUserToDelete);
            return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("We can`t done operation, please try check id, or update your token", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteEvent(Long id, String token) {
        updateExpireTimeToken(token);
        if (isEnableModifyEvents(id, token)) {
            eventRepository.deleteById(id);
            return new ResponseEntity<>("Event has been deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Error, user is not created or token is invalid", HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<String> saveEvent(Map<String, String> json) {
        String token = json.get("token");
        updateExpireTimeToken(token);
        Event event = new Event(json.get("name"), json.get("description"));
        if (userRepository.findUserByToken(token).isPresent() && event.getName() != null && !eventRepository.existsEventByName(event.getName())) {
            eventRepository.save(event);
            return new ResponseEntity<>("Event has been saved", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("We can`t save event data", HttpStatus.BAD_REQUEST);
    }

    public List<EventDTO> getAllEvents(String token) {
        updateExpireTimeToken(token);
        List<EventDTO> eventDTOList = new ArrayList<>();
        for (Event event : eventRepository.findAll()) {
            eventDTOList.add(new EventDTO(event.getName(), event.getDescription()));
        }
        return eventDTOList;

    }

    public ResponseEntity<String> getByIdEvent(String token, Long id) {
        updateExpireTimeToken(token);
        if (isEnableModifyEvents(id, token)) {
            return new ResponseEntity<>("Your event:" + eventRepository.findById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>("We can`t find any events", HttpStatus.OK);
    }

    public void logout(String token) {
        userRepository.updateTokenToNull(token);
    }
}
