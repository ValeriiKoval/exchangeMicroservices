package com.microservice.authservice.facade.impl;

import com.microservice.authservice.facade.VerifyFacade;
import com.microservice.authservice.model.User;
import com.microservice.authservice.payload.response.MessageResponse;
import com.microservice.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VerifyFacadeImpl implements VerifyFacade {

    private final UserService userService;

    @Override
    public MessageResponse verify(final String token) {
        final User user = userService.findByToken(token);
        userService.unlock(user);
        return new MessageResponse("Email was successfully verified");
    }
}
