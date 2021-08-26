package com.example.praktitwitter.controller;

import com.example.praktitwitter.model.*;
import com.example.praktitwitter.service.CommentService;
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

@WebMvcTest(controllers = CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private CommentService commentService;
    private AutoCloseable autoCloseable;

    private Date helperDate;
    private User user1;
    private User user2;
    private Tweet tweet1;
    private Tweet tweet2;
    private Comment comment1;
    private Comment comment2;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        helperDate = new Date();
        user1 = new User(1L, "Anna", "Mustermann", "annamus", "pw123", "Biography", helperDate, Gender.FEMALE, null, null);
        user2 = new User(2L, "Max", "Mustermann", "maxmus", "pw123", "Biography", helperDate, Gender.MALE, null, null);
        tweet1 = new Tweet(1L, "This is a tweet", user1, "Football", null);
        tweet2 = new Tweet(2L, "This is a tweet", user1, "Weather", null);
        comment1 = new Comment(1L, "This is a comment", user2, tweet1);
        comment2 = new Comment(2L, "This is another comment", user2, tweet2);

    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllComments() throws Exception {
        when(commentService.getAllComments()).thenReturn(List.of(comment1));

        mockMvc.perform(get("/comment")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getCommentById() throws Exception {
        when(commentService.getCommentById(comment1.getId())).thenReturn(comment1);

        mockMvc.perform(get("/comment/{id}", comment1.getId())
                .contentType("appliction/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("This is a comment"))
                .andExpect(jsonPath("$.user.id").value(2))
                .andExpect(jsonPath("$.user.firstName").value("Max"))
                .andExpect(jsonPath("$.user.lastName").value("Mustermann"))
                .andExpect(jsonPath("$.user.username").value("maxmus"))
                .andExpect(jsonPath("$.user.password").value("pw123"))
                .andExpect(jsonPath("$.user.biography").value("Biography"))
                .andExpect(jsonPath("$.user.dob").exists())
                .andExpect(jsonPath("$.user.gender").value("MALE"))
                .andExpect(jsonPath("$.user.tweets").doesNotExist())
                .andExpect(jsonPath("$.user.comments").doesNotExist())
                .andExpect(jsonPath("$.tweet.id").value(1))
                .andExpect(jsonPath("$.tweet.text").value("This is a tweet"))
                .andExpect(jsonPath("$.tweet.user.id").value(1))
                .andExpect(jsonPath("$.tweet.hashtag").value("Football"));
    }

    @Test
    void insertComment() throws Exception {
        mockMvc.perform(post("/comment")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(comment1)))
                .andExpect(status().isOk());

        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentService).saveComment(commentArgumentCaptor.capture());

        assertThat(comment1).isEqualTo(commentArgumentCaptor.getValue());
    }

    @Test
    void updateComment() throws Exception{
        mockMvc.perform(post("/comment")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(comment2)))
                .andExpect(status().isOk());

        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentService).saveComment(commentArgumentCaptor.capture());

        assertThat(comment2).isEqualTo(commentArgumentCaptor.getValue());

    }

    @Test
    void deleteCommentById() throws Exception {
        mockMvc.perform(delete("/comment/{id}", comment1.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}