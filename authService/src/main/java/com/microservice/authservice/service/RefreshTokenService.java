package com.microservice.authservice.service;

import com.microservice.authservice.model.RefreshToken;

public interface RefreshTokenService {
    RefreshToken findByToken(String token);

    RefreshToken createRefreshToken(Long userId);

    RefreshToken deleteAndCreateNew(RefreshToken token);

    void deleteByUserId(Long userId);

    void delete(RefreshToken token);

    boolean verifyExpiration(RefreshToken token);
}
