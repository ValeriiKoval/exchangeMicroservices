package com.microservice.orderservice.external.client;

import com.microservice.orderservice.config.FeignConfig;
import com.microservice.orderservice.payload.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "AUTH-SERVICE/users", configuration = FeignConfig.class)
public interface UserService {

    @GetMapping("/{userId}")
    UserResponse getUserInfo(@PathVariable Long userId);

}
