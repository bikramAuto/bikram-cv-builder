package com.c.project.openCV.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.c.project.openCV.repository.UserRepository;
import com.c.project.openCV.user.User;
import com.c.project.openCV.exception.UserNotFoundException;
import com.c.project.openCV.exception.UsernameAlreadyTakenException;
import com.c.project.openCV.dto.ResponseDTO;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register a new user
    public ResponseDTO registerUser(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            throw new UsernameAlreadyTakenException("Username is already taken!");
        }

        // Encrypt the user's password
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);

        return new ResponseDTO("User registered successfully!", true);
    }

    // Login user
    public ResponseDTO loginUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }

        User user = userOpt.get();
        if (passwordEncoder.matches(password, user.getPassword())) {
            return new ResponseDTO("Login successful!", true);
        } else {
            throw new IllegalArgumentException("Incorrect password!");
        }
    }
}
