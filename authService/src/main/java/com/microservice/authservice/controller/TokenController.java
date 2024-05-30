package com.microservice.authservice.controller;

import com.microservice.authservice.facade.TokenFacade;
import com.microservice.authservice.payload.request.TokenRequest;
import com.microservice.authservice.payload.response.TokenRefreshResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenFacade tokenFacade;

    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> refresh(@RequestBody @Valid final TokenRequest request) {
        return ResponseEntity.ok(tokenFacade.refresh(request));
    }
}
