package com.microservice.authservice.controller;

import com.microservice.authservice.facade.UserFacade;
import com.microservice.authservice.payload.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> registerUser(@PathVariable final Long userId) {
        return ResponseEntity.ok(userFacade.getUserInfo(userId));
    }
}
