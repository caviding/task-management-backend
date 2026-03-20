package com.example.security;

import com.example.repository.UserRepository2;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository2 userRepository;

    public CustomUserDetailsService(UserRepository2 userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

         return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found !!"));
    }
}
