package com.microservice.authservice.controller;

import com.microservice.authservice.facade.VerifyFacade;
import com.microservice.authservice.payload.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verify")
@RequiredArgsConstructor
public class VerifyController {

    private final VerifyFacade verifyFacade;

    @GetMapping
    public ResponseEntity<MessageResponse> refresh(@RequestParam final String token) {
        return ResponseEntity.ok(verifyFacade.verify(token));
    }
}
