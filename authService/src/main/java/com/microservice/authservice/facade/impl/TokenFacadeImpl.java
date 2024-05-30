package com.microservice.authservice.facade.impl;

import com.microservice.authservice.exception.RefreshTokenException;
import com.microservice.authservice.facade.TokenFacade;
import com.microservice.authservice.model.RefreshToken;
import com.microservice.authservice.model.User;
import com.microservice.authservice.payload.request.TokenRequest;
import com.microservice.authservice.payload.response.TokenRefreshResponse;
import com.microservice.authservice.service.JwtUtils;
import com.microservice.authservice.service.impl.RefreshTokenServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenFacadeImpl implements TokenFacade {

    private final RefreshTokenServiceImpl refreshTokenService;

    private final JwtUtils jwtUtils;

    @Override
    public TokenRefreshResponse refresh(final TokenRequest request) {
        final String requestRefreshToken = request.getToken();
        final RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken);
        if (refreshTokenService.verifyExpiration(refreshToken)) {
            refreshTokenService.delete(refreshToken);
            throw new RefreshTokenException(requestRefreshToken + "Refresh refreshToken is expired!");
        }
        final RefreshToken newRefreshToken = refreshTokenService.deleteAndCreateNew(refreshToken);
        final User userRefreshToken = newRefreshToken.getUser();
        final String newToken = jwtUtils.generateTokenFromUsername(userRefreshToken.getUsername());
        return new TokenRefreshResponse(newToken, newRefreshToken.getToken());
    }
}
