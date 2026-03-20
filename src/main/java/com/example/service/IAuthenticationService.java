package com.example.service;

import com.example.dto.request.LoginRequest;
import com.example.dto.request.RegisterRequest;
import com.example.entity.User2;

public interface IAuthenticationService {
    public User2 register(RegisterRequest registerRequest);
    public String login(LoginRequest loginRequest);
}
