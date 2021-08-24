package com.example.praktitwitter.service;

import com.example.praktitwitter.model.Tweet;
import com.example.praktitwitter.repository.TweetRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class TweetServiceTest {

    @Mock
    TweetRepository tweetRepository;
    @InjectMocks
    TweetService tweetService;
    AutoCloseable autoCloseable;
    Tweet testTweet;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        testTweet = new Tweet(1L, "This is a tweet", null, null, null);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
        tweetRepository.deleteAll();
    }

    @Test
    void getAllTweets() {
        tweetService.getAllTweets();
        verify(tweetRepository).findAll();
    }

    @Test
    void getTweetById() {
        tweetService.getTweetById(testTweet.getId());
        verify(tweetRepository).getById(testTweet.getId());
    }

    @Test
    void saveTweet() {
        tweetService.saveTweet(testTweet);
        verify(tweetRepository).save(testTweet);
    }

    @Test
    void deleteTweetById() {
        tweetService.deleteTweetById(testTweet.getId());
        verify(tweetRepository).deleteById(testTweet.getId());
    }
}