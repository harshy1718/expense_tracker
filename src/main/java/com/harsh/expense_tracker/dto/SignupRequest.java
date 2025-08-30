package com.harsh.expense_tracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SignupRequest {
    private String username;
    private String password;
    private String email;
    private String name;
}
