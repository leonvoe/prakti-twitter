package com.example.praktitwitter.service;

import com.example.praktitwitter.model.Tweet;
import com.example.praktitwitter.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {

    @Autowired
    TweetRepository tweetRepository;

    public List<Tweet> getAllTweets() {
        return tweetRepository.findAll();
    }

    public Tweet getTweetById(Long id) {
        return tweetRepository.getById(id);
    }

    public List<Tweet> getTweetByUserId(Long id) {
        return tweetRepository.findTweetByUserId(id);
    }

    public void saveTweet(Tweet tweet) {
        tweetRepository.save(tweet);
    }

    public void deleteTweetById(Long id) {
        tweetRepository.deleteById(id);
    }
}
