package com.microservice.authservice.config;

import com.microservice.authservice.external.decoder.CustomErrorDecoder;
import com.microservice.authservice.security.AuthenticatedUser;
import com.microservice.authservice.security.CustomUserDetails;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {

    private final AuthenticatedUser authenticatedUser;

    @Bean
    ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        final CustomUserDetails user = authenticatedUser.getLoggedInUser();
        return requestTemplate -> {
            if (user != null) {
                requestTemplate.header("userId", user.getId().toString());
                requestTemplate.header("roles", user.getStringAuthorities());
            }
        };
    }
}
