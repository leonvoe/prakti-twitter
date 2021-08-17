package com.example.praktitwitter.repository;

import com.example.praktitwitter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
