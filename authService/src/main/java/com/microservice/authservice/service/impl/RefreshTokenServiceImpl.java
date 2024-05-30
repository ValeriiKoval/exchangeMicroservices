package com.microservice.authservice.service.impl;

import com.microservice.authservice.exception.RefreshTokenException;
import com.microservice.authservice.model.RefreshToken;
import com.microservice.authservice.model.User;
import com.microservice.authservice.repository.RefreshTokenRepository;
import com.microservice.authservice.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserServiceImpl userService;
    @Value("${jwt.refreshExpireMs}")
    private Long refreshTokenDurationMs;

    @Override
    public RefreshToken findByToken(final String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RefreshTokenException(token + "Refresh refreshToken is not in database!"));
    }

    @Override
    public RefreshToken createRefreshToken(final Long userId) {

        final RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userService.findById(userId));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken deleteAndCreateNew(final RefreshToken token) {
        final User user = token.getUser();
        this.delete(token);
        return this.createRefreshToken(user.getId());
    }

    @Override
    public void deleteByUserId(final Long userId) {
        refreshTokenRepository.deleteAllByUserId(userId);
    }

    @Override
    public void delete(final RefreshToken token) {
        refreshTokenRepository.delete(token);
    }

    @Override
    public boolean verifyExpiration(final RefreshToken token) {
        return token.getExpiryDate().compareTo(Instant.now()) < 0;
    }
}
