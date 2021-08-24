package com.example.praktitwitter.service;

import com.example.praktitwitter.model.Hashtag;
import com.example.praktitwitter.repository.HashtagRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class HashtagServiceTest {

    @Mock
    HashtagRepository hashtagRepository;

    @InjectMocks
    HashtagService hashtagService;
    AutoCloseable autoCloseable;
    Hashtag testHashtag;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        testHashtag = new Hashtag(1L, "Hashtag Name", null);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
        hashtagRepository.deleteAll();
    }

    @Test
    void getAllHashtags() {
        hashtagService.getAllHashtags();
        verify(hashtagRepository).findAll();
    }

    @Test
    void getHashtagById() {
        hashtagService.getHashtagById(testHashtag.getId());
        verify(hashtagRepository).getById(testHashtag.getId());
    }

    @Test
    void saveHashtag() {
        hashtagService.saveHashtag(testHashtag);
        verify(hashtagRepository).save(testHashtag);
    }

    @Test
    void deleteHashtagById() {
        hashtagService.deleteHashtagById(testHashtag.getId());
        verify(hashtagRepository).deleteById(testHashtag.getId());
    }
}