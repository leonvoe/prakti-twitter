package com.example.praktitwitter.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Hashtag.class)
public class Hashtag {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "hashtags")
    private List<Tweet> tweets;
}
