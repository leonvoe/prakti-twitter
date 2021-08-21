package com.example.praktitwitter.service;

import com.example.praktitwitter.model.Gender;
import com.example.praktitwitter.model.User;
import com.example.praktitwitter.repository.CommentRepository;
import com.example.praktitwitter.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;
    AutoCloseable autoCloseable;
    User testUser;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        testUser = new User(1L, "Max", "Mustermann", "maxmus", "pw", "This is my biography", new Date(), Gender.MALE, null, null);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
        userRepository.deleteAll();
    }

    @Test
    void getAllUsers() {
        userService.getAllUsers();
        verify(userRepository).findAll();
    }

    @Test
    void getUserById() {
        userService.getUserById(testUser.getId());
        verify(userRepository).getById(testUser.getId());
    }

    @Test
    void saveUser() {
        userService.saveUser(testUser);
        verify(userRepository).save(testUser);
    }

    @Test
    void deleteUserById() {
        userService.deleteUserById(testUser.getId());
        verify(userRepository).deleteById(testUser.getId());
    }
}