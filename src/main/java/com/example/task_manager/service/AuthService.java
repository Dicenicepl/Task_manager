package com.example.task_manager.service;

import com.example.task_manager.entity.event.Event;
import com.example.task_manager.entity.event.EventDTO;
import com.example.task_manager.entity.event.EventRepository;
import com.example.task_manager.entity.role.Role;
import com.example.task_manager.entity.role.RoleRepository;
import com.example.task_manager.entity.token.Token;
import com.example.task_manager.entity.token.TokenRepository;
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
    private final TokenRepository tokenRepository;

    // TODO: 05.10.2023 check NULLPOINTEDEXCEPTION in toSTRING
    public AuthService(EventRepository eventRepository, UserRepository userRepository, RoleRepository roleRepository, TokenRepository tokenRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
    }

    private String generatorToken(String email) {
        String[] array = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            token.append(array[random.nextInt(array.length)]);
        }
        tokenRepository.addToken(email, String.valueOf(token));
        return token.toString();
    }

    private void updateExpireTimeToken(String generatedToken) {
        try {
            tokenRepository.updateExpireTokenToActive(generatedToken, new Time(System.currentTimeMillis() + 50000L));
        } catch (Exception e) {
            System.out.println("updateExpireTimeToken: " + e);
        }
    }

    private boolean isExpiredToken(String tokenToCheck) {
        Token token = tokenRepository.findTokenByGeneratedToken(tokenToCheck);
        if (token.toString().isEmpty()) {
            System.out.println("CAUTION - TOKEN IS EXPIRED");
            return true;
        }
        return !(token.getExpireTime().getTime() + 1000L <= System.currentTimeMillis());
    }

    private String getEmailFromToken(String generatedToken) {
        Token token = tokenRepository.findTokenByGeneratedToken(generatedToken);
        if (token != null) {
            return token.getEmail();
        }
        return "0";
    }

    private boolean isEnableModifyEvents(Optional<Event> event, String generatedToken) {
        if (isExpiredToken(generatedToken)) {
            return false;
        }
        Optional<User> user = userRepository.findUserByEmail(getEmailFromToken(generatedToken));
        if (event.isEmpty()) {
            return false;
        }
        Role userRole = roleRepository.findERoleByEmail(user.get().getEmail()).getRole();
        String userEmail = user.get().getEmail();
        String eventOwnerEmail = event.get().getOwner_email();
        updateExpireTimeToken(generatedToken);
        return userEmail.equals(eventOwnerEmail) || userRole.equals(Role.ADMIN);
    }

    private boolean isEnableModifyUsers(Optional<User> user, String token) {
        if (isExpiredToken(token)) {
            return false;
        }
        if (user.isEmpty()) {
            return false;
        }
        Role userRole = roleRepository.findERoleByEmail(user.get().getEmail()).getRole();
        updateExpireTimeToken(token);
        return user.get().getEmail().equals(getEmailFromToken(token)) || userRole.equals(Role.ADMIN);
    }


    public String login(String email, String password) {
        Optional<User> user = userRepository.findUserByEmail(email);
        String token;
        if (user.isEmpty()) {
            return "We can`t find user with that email";
        }
        if (!user.get().getPassword().equals(password)) {
            return "Password is incorrect";
        }
        token = generatorToken(email);
        updateExpireTimeToken(token);
        return token;
    }
    public void logout(String token) {
        tokenRepository.updateTokenToNull(token);
    }

    public ResponseEntity<String> deleteUser(String email, String token) {
        Optional<User> user = userRepository.findUserByEmail(email);
        try {
            if (isEnableModifyUsers(user, token)) {
                userRepository.deleteByEmail(email);
                return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
            }
        } catch (Exception ignored) {
        }
        return new ResponseEntity<>("We can`t done operation, please try check email, or update your token", HttpStatus.OK);
    }

    public ResponseEntity<String> updateUser(Map<String, String> json) {
        String email = json.get("email");
        String username = json.get("username");
        String password = json.get("password");
        String token = json.get("token");
        Optional<User> previouslyUser = userRepository.findUserByEmail(email);
        if (previouslyUser.isEmpty()) {
            return new ResponseEntity<>("User with that email is not found please check for mistakes", HttpStatus.NOT_FOUND);
        }
        if (isEnableModifyUsers(previouslyUser, token)) {
            User userToSave = new User(username, email, password);
            userToSave.setUser_id(previouslyUser.get().getUser_id());
            userRepository.save(userToSave);
            return new ResponseEntity<>("Successfully updated user data", HttpStatus.OK);
        }
        return new ResponseEntity<>("You have no permission to change user data", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteEvent(String name, String token) {
        Event event = eventRepository.findEventsByName(name);
        try {
            if (isEnableModifyEvents(Optional.of(event), token)) {
                eventRepository.deleteByName(name);
                return new ResponseEntity<>("Event has been deleted", HttpStatus.OK);
            }
        }catch (NullPointerException e){
            return new ResponseEntity<>("No event with that name", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("You have no permission to delete event", HttpStatus.BAD_REQUEST);
    }

    // TODO: 05.10.2023 test that
    public ResponseEntity<String> saveEvent(Map<String, String> json) {
        String generatedToken = json.get("token");
        String owner_email = getEmailFromToken(generatedToken);
        String name = json.get("name");
        String description = json.get("description");
        Event event;
        if (eventRepository.existsEventByName(name)) {
            return new ResponseEntity<>("Event with the name: " + name + " is already exists", HttpStatus.BAD_REQUEST);
        }
        try {
            event = new Event(owner_email, name, description);
            eventRepository.save(event);
        }catch (NullPointerException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        updateExpireTimeToken(generatedToken);
        return new ResponseEntity<>("Event has been saved", HttpStatus.CREATED);
    }

    // TODO: 05.10.2023 change returned variable for more information about what`s going on
    public List<EventDTO> getAllEvents(String token) {
        List<EventDTO> eventDTOList = new ArrayList<>();
        if (isExpiredToken(token)) {
            return new ArrayList<>();
        }
        for (Event event : eventRepository.findAll()) {
            eventDTOList.add(new EventDTO(event.getOwner_email(), event.getName(), event.getDescription()));
        }
        updateExpireTimeToken(token);
        return eventDTOList;
    }
    public ResponseEntity<String> updateEvent(Map<String, String> json){
        String name = json.get("name");
        String token = json.get("token");
        String description = json.get("description");
        Optional<Event> event;
        try {
            event = Optional.of(eventRepository.findEventsByName(name));
        }catch (NullPointerException e){
            return new ResponseEntity<>("Event with that name didn`t exist", HttpStatus.NOT_FOUND);
        }
        if (isEnableModifyEvents(event, token)){
            Event eventToSave = new Event(event.get().getEvent_id(), event.get().getOwner_email(), name, description);
            eventRepository.save(eventToSave);
            return new ResponseEntity<>("Event is updated so you now you can chill", HttpStatus.OK);
        }
        if (isExpiredToken(token)){
            return new ResponseEntity<>("Your token is expired, please update your token", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("You don`t have permission to update this event", HttpStatus.UNAUTHORIZED);
    }





//    public ResponseEntity<String> addUserToProject(Map<String, String> json) {
//        if (json.isEmpty()) {
//            return new ResponseEntity<>("Body is empty, please fill up a body", HttpStatus.BAD_REQUEST);
//        }
//        String token = json.get("token");
//        if (isExpiredToken(token)) {
//            return new ResponseEntity<>("Token is expired, please log in again", HttpStatus.BAD_REQUEST);
//        }
//        String owner_email = json.get("owner_email");
//        Optional<User> user = userRepository.findUserByEmail(owner_email);
////        if (!user.get().getToken().equals(token)) {
////            return new ResponseEntity<>("You have no permission to add a new person to project", HttpStatus.NOT_ACCEPTABLE);
////        }
//        String name = json.get("name");
//        String user_email = json.get("user_email");
//        Event event = eventRepository.findEventsByName(name);
//
//        eventRepository.save(event);
//
//        return new ResponseEntity<>("OK", HttpStatus.OK);
//    }
//
//    public ResponseEntity<String> deleteUserFromProject(Map<String, String> json) {
//        return null;
//    }
//
//    public ResponseEntity<String> getAllUsersFromProject(String name, String token) {
//        return null;
//    }
}
