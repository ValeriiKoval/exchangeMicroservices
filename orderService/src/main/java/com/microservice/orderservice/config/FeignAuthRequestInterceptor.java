package com.microservice.orderservice.config;

import com.microservice.orderservice.security.AuthenticatedUser;
import com.microservice.orderservice.security.CustomUserDetails;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeignAuthRequestInterceptor implements RequestInterceptor {

    private final AuthenticatedUser authenticatedUser;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        final CustomUserDetails user = authenticatedUser.getLoggedInUser();
        if (user != null) {
            requestTemplate.header("userId", user.getUserId());
            requestTemplate.header("roles", user.getRoles());
        }
    }
}
