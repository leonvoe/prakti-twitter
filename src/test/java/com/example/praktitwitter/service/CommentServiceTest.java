package com.example.praktitwitter.service;

import com.example.praktitwitter.model.Comment;
import com.example.praktitwitter.repository.CommentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentService commentService;
    AutoCloseable autoCloseable;
    Comment testComment;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        testComment = new Comment(1L, "This is a comment", null, null);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllComments() {
        commentService.getAllComments();
        verify(commentRepository).findAll();
    }

    @Test
    void getCommentById() {
        commentService.getCommentById(testComment.getId());
        verify(commentRepository).getById(testComment.getId());
    }

    @Test
    void saveComment() {
        commentService.saveComment(testComment);
        verify(commentRepository).save(testComment);
    }

    @Test
    void deleteCommentById() {
        commentService.deleteCommentById(testComment.getId());
        verify(commentRepository).deleteById(testComment.getId());
    }
}