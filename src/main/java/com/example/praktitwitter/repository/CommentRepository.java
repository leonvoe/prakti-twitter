package com.example.praktitwitter.repository;

import com.example.praktitwitter.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
