package com.harsh.expense_tracker.service;

import com.harsh.expense_tracker.dto.AuthResponse;
import com.harsh.expense_tracker.dto.LoginRequest;
import com.harsh.expense_tracker.dto.SignupRequest;
import com.harsh.expense_tracker.model.User;
import com.harsh.expense_tracker.repository.UserRepository;
import com.harsh.expense_tracker.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public void registerUser(SignupRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new RuntimeException("Username already taken");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) throws Exception {
        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) throw new Exception("User not found");

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(), user.getPassword(), java.util.Collections.emptyList()
                )
        );
        return new AuthResponse(token);
    }
}

