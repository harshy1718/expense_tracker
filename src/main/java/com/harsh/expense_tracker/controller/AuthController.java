package com.harsh.expense_tracker.controller;

import com.harsh.expense_tracker.dto.AuthResponse;
import com.harsh.expense_tracker.dto.LoginRequest;
import com.harsh.expense_tracker.dto.SignupRequest;
import com.harsh.expense_tracker.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody SignupRequest request) {
        try{
            authService.registerUser(request);
        }
        catch(Exception e){
            return e.getMessage();
        }
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) throws Exception {
        return authService.login(request);
    }
}

