package com.example.praktitwitter.controller;

import com.example.praktitwitter.model.*;
import com.example.praktitwitter.service.TweetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TweetController.class)
class TweetControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private TweetService tweetService;
    private AutoCloseable autoCloseable;

    private Date helperDate;
    private User user1;
    private User user2;
    private Tweet tweet1;
    private Tweet tweet2;
    private Comment comment;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        helperDate = new Date();
        user1 = new User(1L, "Anna", "Mustermann", "annamus", "pw123", "Biography", helperDate, Gender.FEMALE, null, null);
        user2 = new User(2L, "Max", "Mustermann", "maxmus", "pw123", "Biography", helperDate, Gender.MALE, null, null);
        tweet1 = new Tweet(1L, "This is a tweet", user1, "Football", null);
        tweet2 = new Tweet(2L, "This is a tweet", user1, "Weather", null);
        comment = new Comment(1L, "This is a comment", user2, tweet1);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllTweets() throws Exception {
        when(tweetService.getAllTweets()).thenReturn(List.of(tweet1, tweet2));

        mockMvc.perform(get("/tweet")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getTweetById() throws Exception {
        when(tweetService.getTweetById(tweet1.getId())).thenReturn(tweet1);

        mockMvc.perform(get("/tweet/{id}", tweet1.getId())
                .contentType("appliction/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("This is a tweet"))
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.hashtag").value("Football"))
                .andExpect(jsonPath("$.comments").doesNotExist());
    }

    @Test
    void insertTweet() throws Exception {
        mockMvc.perform(post("/tweet")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(tweet1)))
                .andExpect(status().isOk());

        ArgumentCaptor<Tweet> tweetArgumentCaptor = ArgumentCaptor.forClass(Tweet.class);
        verify(tweetService).saveTweet(tweetArgumentCaptor.capture());

        assertThat(tweet1).isEqualTo(tweetArgumentCaptor.getValue());
    }

    @Test
    void updateTweet() throws Exception {
        mockMvc.perform(put("/tweet")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(tweet2)))
                .andExpect(status().isOk());

        ArgumentCaptor<Tweet> tweetArgumentCaptor = ArgumentCaptor.forClass(Tweet.class);
        verify(tweetService).saveTweet(tweetArgumentCaptor.capture());

        assertThat(tweet2).isEqualTo(tweetArgumentCaptor.getValue());
    }

    @Test
    void deleteTweetById() throws Exception{
        mockMvc.perform(delete("/tweet/{id}", tweet1.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}