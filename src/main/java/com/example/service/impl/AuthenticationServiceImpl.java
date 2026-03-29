package com.example.service.impl;

import com.example.Enum.Role;
import com.example.dto.request.LoginRequest;
import com.example.dto.request.RegisterRequest;
import com.example.entity.User2;
import com.example.exception.UserAlreadyExistsException;
import com.example.exception.UserNotFoundException;
import com.example.repository.UserRepository2;
import com.example.security.CustomUserDetailsService;
import com.example.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final UserRepository2 userRepository;
    private final CustomUserDetailsService myUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User2 register(RegisterRequest registerRequest){
        try {
            myUserDetailsService.loadUserByUsername(registerRequest.getUsername());
            throw new UserAlreadyExistsException("Username already exists !!");
        }
        catch (UsernameNotFoundException exception){
            User2 user = User2.builder()
                    .username(registerRequest.getUsername())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
            return user;
        }
    }

    @Override
    public String login(LoginRequest loginRequest){
        try {
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginRequest.getUsername());
            if (passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())){
                return "Login successful !!";
            }
            else {
                throw new UserNotFoundException("Password is incorrect !!");
            }
        }
        catch (UsernameNotFoundException exception){
            throw new UserNotFoundException("Username is incorrect !!") ;
        }
    }



}