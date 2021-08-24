package com.example.praktitwitter.repository;

import com.example.praktitwitter.model.Gender;
import com.example.praktitwitter.model.Tweet;
import com.example.praktitwitter.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TweetRepositoryTest {

    @Autowired
    TweetRepository tweetRepository;

    @Autowired UserRepository userRepository;

    @Test
    void findTweetByUserId() {
        User user = new User(1L, "Max", "Mustermann", "maxmus", "pw123", "Biography", new Date(), Gender.MALE, null, null);
        Tweet tweet = new Tweet(2L, "This is my tweet", user, null, null);

        userRepository.save(user);
        tweetRepository.save(tweet);

        Tweet foundTweet = tweetRepository.findTweetByUserId(user.getId()).stream().findFirst().get();

        assertThat(foundTweet).isEqualTo(tweet);
    }
}