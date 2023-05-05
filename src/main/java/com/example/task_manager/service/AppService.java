package com.example.task_manager.service;

import com.example.task_manager.entity.AppCalendary;
import com.example.task_manager.entity.AppUser;
import com.example.task_manager.repository.CalendaryRepository;
import com.example.task_manager.repository.UserRepository;
import com.example.task_manager.service.compont.RepeatTask;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

@Service
public class AppService {
    private final CalendaryRepository calendaryRepository;
    private final UserRepository userRepository;

    public AppService(CalendaryRepository calendaryRepository, UserRepository userRepository) {
        this.calendaryRepository = calendaryRepository;
        this.userRepository = userRepository;
    }
    public void runSender() throws GeneralSecurityException {
        new RepeatTask(calendaryRepository).settingRepeater(new Date());
    }

    public void saveEventToDatabase(AppCalendary appCalendary) {
        calendaryRepository.save(appCalendary); }
    public void saveUserToDatabase(AppUser appUser){
        if (checkUserData(appUser)) {
            userRepository.save(appUser);
        }else System.out.println("SAVEUSER: METHOD RETURNED EMAIL DIDNT CONTAIN '@'");
    }

    public List<AppUser> showAllUsers(){
        return userRepository.findAll();
    }

    public AppUser showProfileByEmail(String email){
        return userRepository.findAppUserByEmail(email);
    }

    @Transactional
    public void deleteUser(String email){
        if (checkUserExisting(email)){
            userRepository.deleteAppUserByEmail(email);
        }else System.out.println("DELETE USER: METHOD RETURNED USER NOT FOUND");
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
