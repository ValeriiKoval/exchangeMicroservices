package com.microservice.authservice.service.impl;

import com.microservice.authservice.exception.UserNotFoundException;
import com.microservice.authservice.model.Role;
import com.microservice.authservice.model.RoleEnum;
import com.microservice.authservice.model.User;
import com.microservice.authservice.payload.request.SignUpRequest;
import com.microservice.authservice.repository.UserRepository;
import com.microservice.authservice.service.RoleService;
import com.microservice.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User id " + userId + " not found"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User Name " + username + " not found"));
    }

    @Override
    public User findByToken(final String token) {
        return userRepository.findByVerifyToken(token)
                .orElseThrow(() -> new UserNotFoundException("User by token " + token + " not found"));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User registerNewUser(final SignUpRequest signUpRequest) {
        final String username = signUpRequest.getUsername();
        final String email = signUpRequest.getEmail();
        final String password = signUpRequest.getPassword();
        final Set<String> strRoles = signUpRequest.getRoles();

        final Set<Role> roles = strRoles.stream()
                .map(RoleEnum::valueOf)
                .map(roleService::findByName)
                .collect(Collectors.toSet());
        final User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setIsActive(true);
        user.setIsLocked(true);
        user.setRoles(roles);
        user.setVerifyToken(UUID.randomUUID().toString());

        return this.saveUser(user);
    }

    @Override
    public void unlock(final User user) {
        user.setIsLocked(false);
        this.saveUser(user);
    }
}
