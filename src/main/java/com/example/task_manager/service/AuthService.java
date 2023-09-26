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
            userRepository.updateExpireTokenToActive(token, new Time(System.currentTimeMillis() + 50000L));
        } catch (Exception e) {
            System.out.println("updateExpireTimeToken: " + e);
        }
    }

    private boolean isExpiredToken(String token) {
        Optional<User> user = userRepository.findUserByToken(token);
        if (user.isEmpty()) {
            System.out.println("CAUTION - TOKEN IS EXPIRED FOR" + user);
            return true;
        }
        return !(user.get().getExpireTime().getTime() + 1000L <= System.currentTimeMillis());
    }

    private boolean isEnableModifyEvents(Long id, String token) {
        Optional<User> user = userRepository.findUserByToken(token);
        if (isExpiredToken(token)) {
            return false;
        }

        Role userRole = roleRepository.findERoleByEmail(user.get().getEmail()).getRole();
        Optional<Event> event = eventRepository.findById(id);

        if (event.isEmpty()) {
            return false;
        }

        String userEmail = user.get().getEmail();
        String eventEmail = event.get().getEmail();
        updateExpireTimeToken(token);
        return userEmail.equals(eventEmail) || userRole.equals(Role.ADMIN);
    }

    private boolean isEnableModifyUsers(Long id, String token) {
        Optional<User> user = userRepository.findUserByToken(token);
        if (isExpiredToken(token)) {
            return false;
        }
        Role userRole = roleRepository.findERoleByEmail(user.get().getEmail()).getRole();
        updateExpireTimeToken(token);
        return user.get().getId().equals(id) || userRole.equals(Role.ADMIN);
    }

    private Optional<User> findUser(String data) {
        Optional<User> user = Optional.empty();
        if (data.contains("@")) {
            user = userRepository.findUserByEmail(data);
        } else if (data.length() == 10) {
            user = userRepository.findUserByToken(data);
        }
        return user;
    }

    public String login(String email, String password) {
        Optional<User> user = findUser(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            String token = generatorToken(user.get().getId());
            updateExpireTimeToken(token);
            return token;
        } else return "We can`t find user";
    }

    public ResponseEntity<String> deleteUser(String email, String token) {
        Optional<User> user = userRepository.findUserByEmail(email);
        try {
            if (isEnableModifyUsers(user.get().getId(), token)) {
                userRepository.deleteByEmail(user.get().getEmail());
                return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
            }
        }catch (Exception ignored){}
        return new ResponseEntity<>("We can`t done operation, please try check id, or update your token", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteEvent(Long idEventToDelete, String token) {
        if (isEnableModifyEvents(idEventToDelete, token)) {
            eventRepository.deleteById(idEventToDelete);
            return new ResponseEntity<>("Event has been deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Error, user is not created or token is invalid", HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<String> saveEvent(Map<String, String> json) {
        String name = json.get("name");
        Optional<User> user = userRepository.findUserByToken(json.get("token"));
        if (name.length() <= 1) {
            return new ResponseEntity<>("You need input a name for event", HttpStatus.BAD_REQUEST);
        }

        if (user.isEmpty()) {
            return new ResponseEntity<>("You need to update your token", HttpStatus.BAD_REQUEST);
        }

        if (eventRepository.existsEventByName(name)) {
            return new ResponseEntity<>("Event with the name: " + name + " is already exists", HttpStatus.BAD_REQUEST);
        }
        String description = json.get("description");
        Event event = new Event(user.get().getEmail(), name, description);
        eventRepository.save(event);
        updateExpireTimeToken(user.get().getToken());

        return new ResponseEntity<>("Event has been saved", HttpStatus.CREATED);
    }

    public List<EventDTO> getAllEvents(String token) {
        List<EventDTO> eventDTOList = new ArrayList<>();
        if (isExpiredToken(token)) {
            return new ArrayList<>();
        }
        for (Event event : eventRepository.findAll()) {
            eventDTOList.add(new EventDTO(event.getName(), event.getDescription()));
        }
        updateExpireTimeToken(token);
        return eventDTOList;
    }

    public ResponseEntity<String> getByIdEvent(String token, Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (isEnableModifyEvents(id, token)) {
            return new ResponseEntity<>("Your event:" + event.get().getName() + " description:" + event.get().getDescription(), HttpStatus.OK);
        }
        if (event.isEmpty()) {
            return new ResponseEntity<>("We can`t find any event with id:" + id, HttpStatus.OK);
        }
        else return new ResponseEntity<>("Your token is expired, please log in again", HttpStatus.OK);
    }

    public void logout(String token) {
        userRepository.updateTokenToNull(token);
    }

}
