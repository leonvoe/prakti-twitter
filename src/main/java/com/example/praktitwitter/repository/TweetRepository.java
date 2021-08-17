package com.example.praktitwitter.repository;

import com.example.praktitwitter.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
