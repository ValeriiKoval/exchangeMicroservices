package com.microservice.authservice.facade;

import com.microservice.authservice.payload.response.MessageResponse;

public interface VerifyFacade {
    MessageResponse verify(String token);
}
