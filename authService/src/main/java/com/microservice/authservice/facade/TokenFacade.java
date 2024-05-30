package com.microservice.authservice.facade;

import com.microservice.authservice.payload.request.TokenRequest;
import com.microservice.authservice.payload.response.TokenRefreshResponse;

public interface TokenFacade {
    TokenRefreshResponse refresh(TokenRequest request);
}
