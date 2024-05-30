package com.microservice.walletservice.security.filter;

import com.microservice.walletservice.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HeaderRoleFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeaderRoleFilter.class);

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        try {
            final Enumeration<String> headerNames = request.getHeaderNames();
            LOGGER.info("HeaderRoleFilter | doFilterInternal | headers names: {}", headerNames);
            final UserDetails userDetails = parseCustomUserDetails(request);
            LOGGER.info("HeaderRoleFilter | doFilterInternal | userDetails: {}", userDetails);
            final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            LOGGER.error("HeaderRoleFilter | doFilterInternal | Cannot set user authentication: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }


    private CustomUserDetails parseCustomUserDetails(final HttpServletRequest request) {
        final String headerAuth = request.getHeader("roles");
        final String userId = request.getHeader("userId");
        LOGGER.info("HeaderRoleFilter | parseHeader | headerAuth: {} | userId: {}", headerAuth, userId);
        if (userId == null) {
            throw new AuthenticationCredentialsNotFoundException("UserId headers not found in request.");
        }
        final List<String> roles = Optional.of(headerAuth)
                .filter(StringUtils::isNotBlank)
                .map(s -> s.split(" "))
                .map(s -> Arrays.stream(s).toList())
                .orElse(List.of());
        return new CustomUserDetails(userId, roles);
    }
}
