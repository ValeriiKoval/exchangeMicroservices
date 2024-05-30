package com.microservice.authservice.security;

import com.microservice.authservice.model.User;
import com.microservice.authservice.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        return new CustomUserDetails(user);
    }

    public UserDetails loadUserById(Long userId) {
        User user = userService.findById(userId);
        return new CustomUserDetails(user);
    }
}
