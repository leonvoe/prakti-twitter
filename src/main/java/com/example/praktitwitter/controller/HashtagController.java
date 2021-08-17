package com.example.praktitwitter.controller;
import com.example.praktitwitter.model.Hashtag;
import com.example.praktitwitter.service.HashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hashtag")
public class HashtagController {

    @Autowired
    HashtagService hashtagService;

    @GetMapping
    public List<Hashtag> getAllHashtags() {
        return hashtagService.getAllHashtags();
    }

    @GetMapping("/{id}")
    public Hashtag getHashtagById(@PathVariable Long id) {
        return hashtagService.getHashtagById(id);
    }

    @PostMapping
    public void insertHashtag(@RequestBody Hashtag hashtag) {
        hashtagService.saveHashtag(hashtag);
    }

    @PutMapping
    public void updateHashtag(@RequestBody Hashtag hashtag) {
        hashtagService.saveHashtag(hashtag);
    }

    @DeleteMapping("/{id}")
    public void deleteHashtagById(@PathVariable Long id) {
        hashtagService.deleteHashtagById(id);
    }
}
