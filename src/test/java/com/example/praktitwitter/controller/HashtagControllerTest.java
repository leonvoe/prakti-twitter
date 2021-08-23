package com.example.praktitwitter.controller;

import com.example.praktitwitter.model.*;
import com.example.praktitwitter.service.HashtagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
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

@WebMvcTest(controllers = HashtagController.class)
class HashtagControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private HashtagService hashtagService;
    private AutoCloseable autoCloseable;

    private Date helperDate;
    private User user1;
    private User user2;
    private Tweet tweet1;
    private Tweet tweet2;
    private Comment comment;
    private Hashtag hashtag1;
    private Hashtag hashtag2;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        helperDate = new Date();
        user1 = new User(1L, "Anna", "Mustermann", "annamus", "pw123", "Biography", helperDate, Gender.FEMALE, null, null);
        user2 = new User(2L, "Max", "Mustermann", "maxmus", "pw123", "Biography", helperDate, Gender.MALE, null, null);
        hashtag1 = new Hashtag(1L, "This is a hashtag", null);
        hashtag2 = new Hashtag(2L, "This is another hashtag", null);
        tweet1 = new Tweet(1L, "This is a tweet", user1, List.of(hashtag1, hashtag2), null);
        tweet2 = new Tweet(2L, "This is another tweet", user1, List.of(hashtag2), null);
        comment = new Comment(1L, "This is a comment", user2, tweet1);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllHashtags() throws Exception {
        when(hashtagService.getAllHashtags()).thenReturn(List.of(hashtag1, hashtag2));

        mockMvc.perform(get("/hashtag")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getHashtagById() throws Exception {
        when(hashtagService.getHashtagById(hashtag1.getId())).thenReturn(hashtag1);

        mockMvc.perform(get("/hashtag/{id}", hashtag1.getId())
                .contentType("appliction/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("This is a hashtag"))
                .andExpect(jsonPath("$.tweets").doesNotExist());
    }

    @Test
    void insertHashtag() throws Exception {
        mockMvc.perform(post("/hashtag")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(hashtag1)))
                .andExpect(status().isOk());

        ArgumentCaptor<Hashtag> hashtagArgumentCaptor = ArgumentCaptor.forClass(Hashtag.class);
        verify(hashtagService).saveHashtag(hashtagArgumentCaptor.capture());

        assertThat(hashtag1).isEqualTo(hashtagArgumentCaptor.getValue());
    }

    @Test
    void updateHashtag() throws Exception {
        mockMvc.perform(put("/hashtag")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(hashtag2)))
                .andExpect(status().isOk());

        ArgumentCaptor<Hashtag> hashtagArgumentCaptor = ArgumentCaptor.forClass(Hashtag.class);
        verify(hashtagService).saveHashtag(hashtagArgumentCaptor.capture());

        assertThat(hashtag2).isEqualTo(hashtagArgumentCaptor.getValue());
    }

    @Test
    void deleteHashtagById() throws Exception {
        mockMvc.perform(delete("/hashtag/{id}", hashtag1.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}