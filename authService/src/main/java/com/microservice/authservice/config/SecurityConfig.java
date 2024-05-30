package com.microservice.authservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.authservice.security.CustomUserDetailsService;
import com.microservice.authservice.security.filter.AuthTokenFilter;
import com.microservice.authservice.security.filter.LoginFilter;
import com.microservice.authservice.security.jwt.JWTAccessDeniedHandler;
import com.microservice.authservice.security.jwt.JwtAuthenticationEntryPoint;
import com.microservice.authservice.service.JwtUtils;
import com.microservice.authservice.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAccessDeniedHandler accessDeniedHandler;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;
    private final RefreshTokenService refreshTokenService;
    private final ObjectMapper objectMapper;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .headers()
                .frameOptions().disable()
                .and()
                .csrf().disable()
                .cors().disable()
                .authorizeRequests(auth -> auth.anyRequest().permitAll())
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(loginFilter())
                .addFilterBefore(authenticationJwtTokenFilter(jwtUtils, customUserDetailsService), LoginFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/authenticate/signup", "/authenticate/login", "/token/refresh");
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(JwtUtils jwtUtils, CustomUserDetailsService customUserDetailsService) {
        return new AuthTokenFilter(jwtUtils, customUserDetailsService);
    }

    private LoginFilter loginFilter() throws Exception {
        LoginFilter filter = new LoginFilter(
                jwtUtils,
                refreshTokenService,
                objectMapper
        );
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }
}
