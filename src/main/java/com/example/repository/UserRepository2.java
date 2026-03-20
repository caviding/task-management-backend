package com.example.repository;

import com.example.entity.User2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository2 extends JpaRepository<User2, Long> {
    Optional<User2> findByUsername(String username);
}
