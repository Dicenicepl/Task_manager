package com.example.task_manager.service;

import com.example.task_manager.entity.AppCalendary;
import com.example.task_manager.entity.AppUser;
import com.example.task_manager.repository.CalendaryRepository;
import com.example.task_manager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppService {
    private final CalendaryRepository calendaryRepository;
    private final UserRepository userRepository;

    public AppService(CalendaryRepository calendaryRepository, UserRepository userRepository) {
        this.calendaryRepository = calendaryRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> saveEventToDatabase(AppCalendary appCalendary){
        try {
            if (!appCalendary.getEventName().isBlank() && !appCalendary.getDate().toString().isBlank() && !appCalendary.getEventOwnerEmail().isBlank()) {
                calendaryRepository.save(appCalendary);
                return new ResponseEntity<>("Event created", HttpStatus.OK);
            } else return new ResponseEntity<>("Some of data is blank", HttpStatus.BAD_REQUEST);
        }catch (NullPointerException message){
            System.out.println("SAVE EVENT TO DATABASE: ERROR - " + message.getMessage());
            return new ResponseEntity<>("You missing something cause we have Null here", HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<String> saveUserToDatabase(AppUser appUser){
        try {
            if (!appUser.getEmail().isBlank() && !appUser.getPassword().isBlank()){
                userRepository.save(appUser);
                return new ResponseEntity<>("User created", HttpStatus.OK);
            }else return new ResponseEntity<>("Some of data is blank", HttpStatus.BAD_REQUEST);
        }catch (NullPointerException message){
            System.out.println("SAVE USER TO DATABASE: ERROR - " + message.getMessage());
            return new ResponseEntity<>("You missing something cause we have Null here", HttpStatus.BAD_REQUEST);
        }
    }

    public List<AppUser> showAllUsers(){
        return userRepository.findAll();
    }

    public AppUser showProfileByEmail(String email){
        return userRepository.findAppUserByEmail(email);
    }

    @Transactional
    public Boolean deleteUser(String email){
        if (checkUserExisting(email)){
            userRepository.deleteAppUserByEmail(email);
            return true;
        }else return false;
    }

    private boolean checkUserExisting(String email){
        return userRepository.findAppUserByEmail(email) != null;
    }

    private boolean checkUserData(AppUser user){
        if (!user.getEmail().contains("@")){
            return false;
        } else return !user.getFirstName().isBlank() || !user.getLastName().isBlank();
    }
}
