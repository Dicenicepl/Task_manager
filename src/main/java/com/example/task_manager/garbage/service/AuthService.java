package com.example.task_manager.garbage.service;

import com.example.task_manager.garbage.entity.AddTaskObject;
import com.example.task_manager.garbage.entity.task.Task;
import com.example.task_manager.garbage.entity.task.TaskDTO;
import com.example.task_manager.garbage.entity.task.TaskRepository;
import com.example.task_manager.garbage.entity.project.Project;
import com.example.task_manager.garbage.entity.project.ProjectRepository;
import com.example.task_manager.garbage.entity.role.Role;
import com.example.task_manager.garbage.entity.role.RoleRepository;
import com.example.task_manager.garbage.entity.token.Token;
import com.example.task_manager.garbage.entity.token.TokenRepository;
import com.example.task_manager.garbage.entity.user.User;
import com.example.task_manager.garbage.entity.user.UserRepository;
import com.example.task_manager.garbage.security.PasswordHasher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.*;

@Service
public class AuthService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final ProjectRepository projectRepository;

    public AuthService(TaskRepository taskRepository, UserRepository userRepository, RoleRepository roleRepository, TokenRepository tokenRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
        this.projectRepository = projectRepository;
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

    private void updateExpireTimeToken(String generatedToken, boolean force) { //temporary solution for not updating previous expireTime
        try {
            if (force){
                tokenRepository.updateExpireTokenToActive(generatedToken, new Time(System.currentTimeMillis() + 50000L));
            }
            if (!isExpiredToken(generatedToken)){
                tokenRepository.updateExpireTokenToActive(generatedToken, new Time(System.currentTimeMillis() + 50000L));
            }
        } catch (NullPointerException e) {
            System.out.println("updateExpireTimeToken: " + e);
        }
    }

    private boolean isExpiredToken(String tokenToCheck) {
        Token token = tokenRepository.findTokenByGeneratedToken(tokenToCheck);
        if (token != null) {
            return token.getExpireTime().getTime() + 1000L <= System.currentTimeMillis();
        }
        return true;
    }


    private String getEmailFromToken(String generatedToken) {
        Token token = tokenRepository.findTokenByGeneratedToken(generatedToken);
        if (isExpiredToken(generatedToken)) {
            return token.getEmail();
        }
        return null;
    }

    private boolean isEnableModifyEvents(Optional<Task> event, String generatedToken) {
        Optional<User> user;
        Role userRole;
        String userEmail;
        String eventOwnerEmail;
        if (isExpiredToken(generatedToken)) {
            return false;
        }
        user = userRepository.findUserByEmail(getEmailFromToken(generatedToken));
        if (event.isPresent() && user.isPresent()) {
            userEmail = user.get().getEmail();
            userRole = roleRepository.findERoleByEmail(userEmail).getRole();
            eventOwnerEmail = event.get().getOwner_email();
            return userEmail.equals(eventOwnerEmail) || userRole.equals(Role.ADMIN);
        }
        return false;
    }

    private boolean isEnableModifyUsers(Optional<User> user, String token) {
        Role userRole;
        String userEmail;
        if (isExpiredToken(token)) {
            return false;
        }
        if (user.isPresent()) {
            userEmail = user.get().getEmail();
            userRole = roleRepository.findERoleByEmail(userEmail).getRole();
            return userEmail.equals(getEmailFromToken(token)) || userRole.equals(Role.ADMIN);
        }
        return false;
    }

    private void saveTokenToDatabase(String email, String generatedToken) {
        Token token = new Token(email, generatedToken);
        try {
            tokenRepository.save(token);
        } catch (Exception ignored) {
            //Catch when unique key (email) values is already exists on table 'tokens'
        }
    }


    public ResponseEntity<String> login(Map<String, String> json) {
        String email = json.get("email");
        String password = new PasswordHasher().Hasher(json.get("password"));
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            return new ResponseEntity<>("We can`t find user with that email", HttpStatus.OK);
        }
        if (!user.get().getPassword().equals(password)) {
            return new ResponseEntity<>("Password is incorrect", HttpStatus.OK);
        }
        String token = generatorToken(email);
        saveTokenToDatabase(email, token);
        updateExpireTimeToken(token, true);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    public void logout(String email, String generatedToken) {
        Token token = tokenRepository.findTokenByGeneratedToken(generatedToken);
        try {
            if (token.getEmail().equals(email)) {
                tokenRepository.deleteExpiredToken(generatedToken);
            }
        } catch (NullPointerException e) {
            System.out.println("LOGOUT: NULL");
        }
    }

    public ResponseEntity<String> deleteUser(Map<String, String> json) {
        String email = json.get("email");
        String token = json.get("token");
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            return new ResponseEntity<>("We can`t user with that email", HttpStatus.OK);
        }
        if (isEnableModifyUsers(user, token)) {
            userRepository.deleteByEmail(email);
            updateExpireTimeToken(token, false);
            return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("We can`t done operation update your token", HttpStatus.OK);
    }

    public ResponseEntity<String> updateUser(Map<String, String> json) {
        String email = json.get("email");
        String username = json.get("username");
        String password = json.get("password");
        String token = json.get("token");
        Optional<User> previouslyUser = userRepository.findUserByEmail(email);
        if (previouslyUser.isEmpty()) {
            return new ResponseEntity<>("User with that email is not found please check for mistakes", HttpStatus.OK);
        }
        if (isEnableModifyUsers(previouslyUser, token)) {
            User userToSave = new User(username, email, password);
            userToSave.setUser_id(previouslyUser.get().getUser_id());
            updateExpireTimeToken(token, false);
            userRepository.save(userToSave);
            return new ResponseEntity<>("Successfully updated user data", HttpStatus.OK);
        }
        return new ResponseEntity<>("You have no permission to change user data", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteEvent(String name, String token) {
        Task task = taskRepository.findTaskByName(name);
        try {
            if (isEnableModifyEvents(Optional.of(task), token)) {
                updateExpireTimeToken(token, false);
                taskRepository.deleteByName(name);
                return new ResponseEntity<>("Event has been deleted", HttpStatus.OK);
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>("No event with that name", HttpStatus.OK);
        }
        return new ResponseEntity<>("You have no permission to delete event", HttpStatus.OK);
    }


    public ResponseEntity<String> saveTask(Map<String, String> json) {
        String generatedToken = json.get("token");
        String owner_email = getEmailFromToken(generatedToken);
        String name = json.get("name");
        String description = json.get("description");
        Task task;
        if (name == null) {
            return new ResponseEntity<>("Retry put name", HttpStatus.OK);
        }
        if (owner_email != null) {
            task = new Task(owner_email, name, description, null, null, projectRepository.findProjectByName("Jira"));
            taskRepository.save(task);
            return new ResponseEntity<>("Event has been saved", HttpStatus.OK);
        }
        updateExpireTimeToken(generatedToken, false);
        return new ResponseEntity<>("Check user token", HttpStatus.OK);
    }

    // TODO: 05.10.2023 change returned variable for more information about what`s going on
    public List<TaskDTO> getAllEvents(String token) {
        List<TaskDTO> taskDTOList = new ArrayList<>();
        if (isExpiredToken(token)) {
            return new ArrayList<>();
        }
        for (Task task : taskRepository.findAll()) {
            taskDTOList.add(new TaskDTO(task.getOwner_email(), task.getName(), task.getDescription()));
        }
        updateExpireTimeToken(token, false);
        return taskDTOList;
    }
    //todo rechange
//    public ResponseEntity<String> updateEvent(Map<String, String> json) {
//        String name = json.get("name");
//        String token = json.get("token");
//        String description = json.get("description");
//        Optional<Task> task;
//        try {
//            task = Optional.of(taskRepository.findEventsByName(name));
//        } catch (NullPointerException e) {
//            return new ResponseEntity<>("Event with that name didn`t exist", HttpStatus.OK);
//        }
//        if (isEnableModifyEvents(task, token)) {
//            Task taskToSave = new Task();
//            updateExpireTimeToken(token, false);
//            taskRepository.save(taskToSave);
//            return new ResponseEntity<>("Event is updated so you now you can chill", HttpStatus.OK);
//        }
//        if (isExpiredToken(token)) {
//            return new ResponseEntity<>("Your token is expired, please update your token", HttpStatus.OK);
//        }
//        return new ResponseEntity<>("You don`t have permission to update this task", HttpStatus.OK);
//    }

    // TODO: 01.12.2023 create api for Project class
    public ResponseEntity<String> createProject(Map<String, String> json){
        String token = json.get("token");
        String owner_email = getEmailFromToken(token);
        String name = json.get("name");
        String description = json.get("description");
        if (!isExpiredToken(token) ) {
            return new ResponseEntity<>("Update your token", HttpStatus.OK);
        }
        if (name.isEmpty()){
            return new ResponseEntity<>("Missing name, please try again", HttpStatus.OK);
        }
        projectRepository.save(new Project(owner_email, name, description));
        return new ResponseEntity<>("Project is saved", HttpStatus.OK);
    }
    //{
    //  "name": "Nazwa_obiektu",
    //  "tasks": [
    //    {
    //      "firstName": "Imię_1",
    //      "lastName": "Nazwisko_1"
    //    },
    //    {
    //      "firstName": "Imię_2",
    //      "lastName": "Nazwisko_2"
    //    },
    //    {
    //      "firstName": "Imię_3",
    //      "lastName": "Nazwisko_3"
    //    }
    //  ]
    //}
    public ResponseEntity<String> addTasksToProject(AddTaskObject addTaskObject){
//        if (json.isEmpty()){
//            return new ResponseEntity<>("Json is empty", HttpStatus.OK);
//        }
//        String token = json.get("token");
//        if (isExpiredToken(token)){
//            return new ResponseEntity<>("Token is expired", HttpStatus.OK);
//        }
//        Project project = projectRepository.findProjectByName(json.get("name"));
//        List<Task> tasks;
//        try{
//            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonTasks = json.get("tasks");
//            tasks = objectMapper.readValue(jsonTasks, new TypeReference<>() {
//            });
//        } catch (Exception e){
//            return new ResponseEntity<>("Error while converting JSON to local variables", HttpStatus.OK);
//        }
//        for (Task task: tasks){
//            task.setProject(project);
//            taskRepository.save(task);
//        }
        return new ResponseEntity<>("Tasks is saved and linked to project", HttpStatus.OK);
    }
    public ResponseEntity<String> updateProject(Map<String, String> json) {
        if (json.isEmpty()){
            return new ResponseEntity<>("Json is empty", HttpStatus.OK);
        }
        String token = json.get("token");
        String owner_email = getEmailFromToken(token);
        String projectName = json.get("name");
        String description = json.get("description");
        Project oldProject = projectRepository.findProjectByName(projectName);
        if (!isExpiredToken(token) ) {
            return new ResponseEntity<>("Your token is expired." +
                    " Use /login to ", HttpStatus.OK);
        }
        if (projectName.isEmpty()){
            return new ResponseEntity<>("Missing name, please try again", HttpStatus.OK);
        }
        if (!oldProject.getOwner_email().equals(owner_email)){
            return new ResponseEntity<>("You have no permission to edit", HttpStatus.OK);
        }
        Project newProject = new Project(
                oldProject.getOwner_email(),
                oldProject.getName(),
                oldProject.getDescription());
        projectRepository.save(newProject);
        return new ResponseEntity<>("Done", HttpStatus.OK);
    }
}