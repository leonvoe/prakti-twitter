package com.example.praktitwitter.service;

import com.example.praktitwitter.model.Hashtag;
import com.example.praktitwitter.repository.HashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HashtagService {

    @Autowired
    HashtagRepository hashtagRepository;

    public List<Hashtag> getAllHashtags() {
        return hashtagRepository.findAll();
    }

    public Hashtag getHashtagById(Long id) {
        return hashtagRepository.getById(id);
    }

    public void saveHashtag(Hashtag hashtag) {
        hashtagRepository.save(hashtag);
    }

    public void deleteHashtagById(Long id) {
        hashtagRepository.deleteById(id);
    }
}
