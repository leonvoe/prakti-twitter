package com.example.praktitwitter.controller;

import com.example.praktitwitter.model.Gender;
import com.example.praktitwitter.model.User;
import com.example.praktitwitter.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    private Date helperDate;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        helperDate = new Date();
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllUsers() throws Exception {
        User updatedUser = new User(1L, "Anna", "Mustermann", "annamus", "pw123", "Biography", helperDate, Gender.FEMALE, null, null);
        when(userService.getAllUsers()).thenReturn(List.of(updatedUser));

        mockMvc.perform(get("/user")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getUserById() throws Exception {
        User updatedUser = new User(1L, "Anna", "Mustermann", "annamus", "pw123", "Biography", helperDate, Gender.FEMALE, null, null);
        when(userService.getUserById(updatedUser.getId())).thenReturn(updatedUser);

        mockMvc.perform(get("/user/{id}", 1L)
                .contentType("appliction/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Anna"))
                .andExpect(jsonPath("$.lastName").value("Mustermann"))
                .andExpect(jsonPath("$.username").value("annamus"))
                .andExpect(jsonPath("$.password").value("pw123"))
                .andExpect(jsonPath("$.biography").value("Biography"))
                .andExpect(jsonPath("$.dob").exists())
                .andExpect(jsonPath("$.gender").value("FEMALE"))
                .andExpect(jsonPath("$.tweets").doesNotExist())
                .andExpect(jsonPath("$.comments").doesNotExist());
    }

    @Test
    void insertUser() throws Exception {
        User user = new User(1L, "Max", "Mustermann", "maxmus", "pw123", "Biography", helperDate, Gender.MALE, null, null);

        mockMvc.perform(post("/user")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isOk());

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).saveUser(userArgumentCaptor.capture());

        assertThat(user).isEqualTo(userArgumentCaptor.getValue());
    }

    @Test
    void updateUser() throws Exception {
        User updatedUser = new User(1L, "Anna", "Mustermann", "annamus", "pw123", "Biography", helperDate, Gender.FEMALE, null, null);

        mockMvc.perform(put("/user")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(updatedUser)))
        .andExpect(status().isOk());

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).saveUser(userArgumentCaptor.capture());

        assertThat(updatedUser).isEqualTo(userArgumentCaptor.getValue());
    }

    @Test
    void deleteUserById() throws Exception {
        mockMvc.perform(delete("/user/{id}", 1L)
        .contentType("application/json"))
        .andExpect(status().isOk());
    }
}