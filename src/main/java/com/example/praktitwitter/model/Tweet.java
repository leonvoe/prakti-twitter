package com.example.praktitwitter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tweet {
    @Id
    @GeneratedValue
    private long id;
    private String text;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tweet_hashtag",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private List<Hashtag> hashtags;
    @OneToMany(mappedBy = "tweet")
    private List<Comment> comments;
}
