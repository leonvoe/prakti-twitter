package com.example.praktitwitter.controller;

import com.example.praktitwitter.model.Tweet;
import com.example.praktitwitter.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    TweetService tweetService;

    @GetMapping
    public List<Tweet> getAllTweets() {
        return tweetService.getAllTweets();
    }

    @GetMapping("/{id}")
    public Tweet getTweetById(@PathVariable Long id) {
        return tweetService.getTweetById(id);
    }

    @GetMapping("/user/{id}")
    public List<Tweet> getTweetByUserId(@PathVariable Long id) {
        return tweetService.getTweetByUserId(id);
    }

    @PostMapping
    public void insertTweet(@RequestBody Tweet tweet) {
        tweetService.saveTweet(tweet);
    }

    @PutMapping
    public void updateTweet(@RequestBody Tweet tweet) {
        tweetService.saveTweet(tweet);
    }

    @DeleteMapping("/{id}")
    public void deleteTweetById(@PathVariable Long id) {
        tweetService.deleteTweetById(id);
    }
}
