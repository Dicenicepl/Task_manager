package com.example.task_manager.service;

import com.example.task_manager.entity.event.Event;
import com.example.task_manager.entity.event.EventDTO;
import com.example.task_manager.entity.event.EventRepository;
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

    public AuthService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
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

    public String login(String email, String password) {
        User user = userRepository.findUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            String token = generatorToken(user.getId());
            updateExpireTimeToken(token);
            return token;
        } else return "We can`t find user";
    }

    public void deleteUser(Long idUserToDelete, String token) {
        if (userRepository.findUserById(idUserToDelete) != null
                && userRepository.findUserByToken(token).isPresent()) {
            userRepository.deleteById(idUserToDelete);
        }
    }

    public ResponseEntity<String> deleteEvent(Long id, String token) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent() && userRepository.findUserByToken(token).isPresent()) {
            updateExpireTimeToken(token);
            eventRepository.deleteById(id);
            return new ResponseEntity<>("Event: " + new EventDTO(event.get().getName(), event.get().getDescription()) + "deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Error, user is not created or token is invalid", HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<String> saveEvent(Map<String, String> json) {
        String token = json.get("token");
        Event event = new Event(json.get("name"), json.get("description"));
        if (userRepository.findUserByToken(token).isPresent() && event.getName() != null && !eventRepository.existsEventByName(event.getName())) {
            updateExpireTimeToken(token);
            eventRepository.save(event);
            return new ResponseEntity<>("Event has been saved", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("We can`t save event data", HttpStatus.BAD_REQUEST);
    }

    public List<EventDTO> getAllEvents(String token) {
        List<EventDTO> eventDTOList = new ArrayList<>();
        updateExpireTimeToken(token);
        for (Event event : eventRepository.findAll()) {
            eventDTOList.add(new EventDTO(event.getName(), event.getDescription()));
        }
        return eventDTOList;

    }

    public ResponseEntity<EventDTO> getByIdEvent(String token, Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent() && userRepository.findUserByToken(token).isPresent()) {
            updateExpireTimeToken(token);
            return new ResponseEntity<>(new EventDTO(event.get().getName(), event.get().getDescription()), HttpStatus.OK);
        }
        return null;
    }

    public void logout(String token) {
        userRepository.updateTokenToNull(token);
    }
}
