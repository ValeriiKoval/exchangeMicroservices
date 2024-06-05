package com.microservice.authservice.controller;

import com.microservice.authservice.facade.RegisterFacade;
import com.microservice.authservice.payload.request.SignUpRequest;
import com.microservice.authservice.payload.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterFacade registerFacade;

    @PostMapping
    public MessageResponse registerUser(@RequestBody final SignUpRequest signUpRequest) {
        return registerFacade.registerUser(signUpRequest);
    }
}
