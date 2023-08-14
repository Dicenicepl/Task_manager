package com.example.task_manager.service;

import com.example.task_manager.entity.event.Event;
import com.example.task_manager.entity.event.EventRepository;
import com.example.task_manager.entity.user.User;
import com.example.task_manager.entity.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public AuthService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    private String generatorToken(Long id) {
        String[] array = new String[]{"A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
                "T", "U", "V", "W", "X", "Y", "Z"};
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            token.append(array[random.nextInt(array.length)]);
        }
        userRepository.addToken(id, String.valueOf(token));
        return token.toString();
    }

    public String login(String email, String password) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get().getId() + "_" + generatorToken(user.get().getId());
        } else if (user.isEmpty()) {
            return "We can`t find user";
        }
        return "Bad Request";
    }

    public void deleteUser(Long idUserToDelete, String token) {
        if (userRepository.findUserById(idUserToDelete).isPresent()
                && userRepository.findUserByToken(token).isPresent()) {
            userRepository.deleteById(idUserToDelete);
        }
    }

    public ResponseEntity<String> deleteEvent(Long id, String token) {
        Optional<User> user = userRepository.findUserById(id);
        if (user.isPresent() && userRepository.findUserByToken(token).isPresent()) {
            eventRepository.deleteById(id);
            return new ResponseEntity<>("User: " + user + "deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Error, user is not created or token is invalid", HttpStatus.NOT_ACCEPTABLE);
    }

    public ResponseEntity<String> saveEvent(Event event) {
        if (event.getName() != null && !eventRepository.existsEventByName(event.getName())) {
            eventRepository.save(event);
            return new ResponseEntity<>("Event has been saved", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("We can`t save event data", HttpStatus.BAD_REQUEST);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public ResponseEntity<Optional<Event>> getByIdEvent(String token, Long id) {
        if (eventRepository.findById(id).isPresent() && userRepository.findUserByToken(token).isPresent()) {
            return new ResponseEntity<>(eventRepository.findById(id), HttpStatus.OK);
        }
        return null;
    }

    public void logout(String token) {
        userRepository.logout(token);
    }
}
