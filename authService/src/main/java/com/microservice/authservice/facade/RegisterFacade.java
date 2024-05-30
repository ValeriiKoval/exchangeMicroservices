package com.microservice.authservice.facade;

import com.microservice.authservice.payload.request.SignUpRequest;
import com.microservice.authservice.payload.response.MessageResponse;

public interface RegisterFacade {
    MessageResponse registerUser(SignUpRequest signUpRequest);
}
