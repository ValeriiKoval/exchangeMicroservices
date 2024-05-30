package com.microservice.authservice.repository;

import com.microservice.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByVerifyToken(String verifyToken);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findById(Long userId);
}
