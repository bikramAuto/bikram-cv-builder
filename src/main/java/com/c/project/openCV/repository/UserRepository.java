package com.c.project.openCV.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.c.project.openCV.user.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
