package com.cognizant.authorizationService.controller;

import com.cognizant.authorizationService.model.User;
import com.cognizant.authorizationService.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    public void testRegisterNewUser_Success() {
        User testUser = new User("testUser", "firstName", "lastName", "test@test.com", "password123", "password123", "1234567890", new HashSet<>());
        when(userService.registerNewUser(testUser)).thenReturn(HttpStatus.OK);
        assertEquals(HttpStatus.OK, userController.registerNewUser(testUser));
    }

    @Test
    public void testRegisterNewUser_Fail() {
        User testUser = new User("testUser", "firstName", "lastName", "test@test.com", "password123", "password123", "1234567890", new HashSet<>());
        when(userService.registerNewUser(testUser)).thenReturn(HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST, userController.registerNewUser(testUser));
    }
}
