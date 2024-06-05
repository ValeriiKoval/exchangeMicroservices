package com.microservice.apigateway.configuration;

import com.microservice.apigateway.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtAuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("AUTH-SERVICE", r -> r.path("/signup",
                                "/token/**",
                                "/verify/**",
                                "/login",
                                "/users/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://AUTH-SERVICE"))
                .route("WALLET-SERVICE", r -> r.path("/wallet/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://WALLET-SERVICE"))
                .route("EXCHANGE-SERVICE", r -> r.path("/exchange/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://EXCHANGE-SERVICE"))
                .route("ORDER-SERVICE", r -> r.path("/orders/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://ORDER-SERVICE"))
                .build();
    }
}
