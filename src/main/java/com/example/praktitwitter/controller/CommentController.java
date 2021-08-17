package com.example.praktitwitter.controller;

import com.example.praktitwitter.model.Comment;
import com.example.praktitwitter.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @PostMapping
    public void insertComment(@RequestBody Comment comment) {
        commentService.saveComment(comment);
    }

    @PutMapping
    public void updateComment(@RequestBody Comment comment) {
        commentService.saveComment(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteCommentById(@PathVariable Long id) {
        commentService.deleteCommentById(id);
    }
}
