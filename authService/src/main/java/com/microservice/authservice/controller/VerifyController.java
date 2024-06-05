package com.microservice.authservice.controller;

import com.microservice.authservice.facade.VerifyFacade;
import com.microservice.authservice.payload.response.MessageResponse;
import lombok.RequiredArgsConstructor;
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
    public MessageResponse refresh(@RequestParam final String token) {
        return verifyFacade.verify(token);
    }
}
