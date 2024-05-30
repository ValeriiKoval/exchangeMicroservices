package com.microservice.authservice.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.authservice.exception.error.ApiError;
import com.microservice.authservice.model.RefreshToken;
import com.microservice.authservice.payload.request.LoginRequest;
import com.microservice.authservice.payload.response.JWTResponse;
import com.microservice.authservice.security.CustomUserDetails;
import com.microservice.authservice.service.JwtUtils;
import com.microservice.authservice.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        LoginRequest loginRequest = getLoginRequest(req);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()
        );

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication auth
    ) throws IOException {
        final CustomUserDetails userDetails = this.getUserDetails(auth);
        final String jwt = jwtUtils.generateJwtToken(userDetails.getUsername());

        final List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        refreshTokenService.deleteByUserId(userDetails.getId());
        final RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        final JWTResponse jwtResponse = new JWTResponse();
        jwtResponse.setEmail(userDetails.getEmail());
        jwtResponse.setUsername(userDetails.getUsername());
        jwtResponse.setId(userDetails.getId());
        jwtResponse.setToken(jwt);
        jwtResponse.setRefreshToken(refreshToken.getToken());
        jwtResponse.setRoles(roles);

        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        response.getOutputStream().write(objectMapper.writeValueAsBytes(jwtResponse));
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException {
        final List<String> details = new ArrayList<>();
        details.add(failed.getMessage());

        final ApiError err = new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, LocalDateTime.now(),
                "Failed login request", details);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        response.getOutputStream().write(objectMapper.writeValueAsBytes(err));
    }

    private LoginRequest getLoginRequest(HttpServletRequest req) {
        try {
            LoginRequest loginRequest = objectMapper.readValue(req.getInputStream(), LoginRequest.class);
            if (!isValid(loginRequest)) {
                throw new BadCredentialsException("Bad or empty credentials, check username and password");
            }
            return loginRequest;
        } catch (IOException e) {
            throw new HttpMessageNotReadableException("Message not readable", e);
        }
    }

    private boolean isValid(LoginRequest loginRequest) {
        return !(StringUtils.isEmpty(loginRequest.getUsername()) || StringUtils.isEmpty(loginRequest.getPassword()));
    }

    private CustomUserDetails getUserDetails(Authentication authentication) {
        return (CustomUserDetails) authentication.getPrincipal();
    }

}
