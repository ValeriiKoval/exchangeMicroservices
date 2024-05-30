package com.microservice.authservice.facade.impl;

import com.microservice.authservice.exception.RegisterException;
import com.microservice.authservice.external.client.WalletService;
import com.microservice.authservice.facade.RegisterFacade;
import com.microservice.authservice.model.Role;
import com.microservice.authservice.model.RoleEnum;
import com.microservice.authservice.model.User;
import com.microservice.authservice.payload.request.SignUpRequest;
import com.microservice.authservice.payload.response.MessageResponse;
import com.microservice.authservice.service.UserService;
import com.microservice.authservice.service.VerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterFacadeImpl implements RegisterFacade {

    private final UserService userService;
    private final VerifyService verifyService;
    private final WalletService walletService;

    @Override
    public MessageResponse registerUser(final SignUpRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            throw new RegisterException("Error: Username is already taken!");
        }
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            throw new RegisterException("Error: Email is already taken!");
        }

        final User user = userService.registerNewUser(signUpRequest);

        final Map<String, String> headers = new HashMap<>();
        headers.put("userId", user.getId().toString());
        headers.put("roles", user.getRoles().stream()
                .map(Role::getName)
                .map(RoleEnum::name)
                .collect(Collectors.joining(" ")));

        walletService.createWallet(headers);
        verifyService.sendVerificationEmail(user);

        return new MessageResponse("User registered successfully!");
    }
}
