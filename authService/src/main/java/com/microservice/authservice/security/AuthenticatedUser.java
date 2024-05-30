package com.microservice.authservice.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * This class allows to authenticate the user.
 */
@Component
public class AuthenticatedUser {

    public CustomUserDetails getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) &&
            authentication.getPrincipal() instanceof CustomUserDetails
        ) {
            return (CustomUserDetails) authentication.getPrincipal();
        } else {
            return null;
        }
    }
}
