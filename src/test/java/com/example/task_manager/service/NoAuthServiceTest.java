package com.example.task_manager.service;

import com.example.task_manager.entity.user.UserDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class NoAuthServiceTest {
    @Autowired
    private NoAuthService noAuthService;

    @Test
    @Disabled
    @DisplayName("TEST METHOD: REGISTER")
    void testRegister() {
        UserDTO user = new UserDTO(null, "eee", "ppp");
        assertTrue(noAuthService.register(user).getStatusCode().is4xxClientError());
    }

    @Test
    @Disabled
    @DisplayName("TEST METHOD: GET-BY-EMAIL")
    void testGetByEmail() {
        boolean normalTest = noAuthService.getByEmail("dicenicepl@gmail.com").getStatusCode().is2xxSuccessful();
        assertTrue(normalTest);
        boolean catchTestNullException = noAuthService.getByEmail(null).getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND);
        assertTrue(catchTestNullException);
    }

    @Test
    @Disabled
    @DisplayName("TEST METHOD: GET-ALL")
    void testGetAll() {
        boolean normalTest = noAuthService.getAll().getStatusCode().isSameCodeAs(HttpStatus.OK);
        assertTrue(normalTest);
        boolean catchTestNullException = noAuthService.getAll().getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND);
        assertFalse(catchTestNullException); //In database must be 0 records for true
    }

    @Test
    @DisplayName("TEST METHOD: GET-EVENT-BY-NAME")
    void testGetEventByName() {
        boolean normalTest = noAuthService.getEventByName("event").getStatusCode().isSameCodeAs(HttpStatus.OK);
        assertTrue(normalTest);
        boolean notFoundTest = noAuthService.getEventByName(null).getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND);
        assertTrue(notFoundTest);
    }
}
