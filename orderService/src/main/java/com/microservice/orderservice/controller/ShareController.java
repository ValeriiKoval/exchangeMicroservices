package com.microservice.orderservice.controller;

import com.microservice.orderservice.facade.ShareFacade;
import com.microservice.orderservice.payload.response.ShareResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shares")
@RequiredArgsConstructor
public class ShareController {

    private final ShareFacade shareFacade;

    @GetMapping
    public List<ShareResponse> getOrders(@RequestHeader final Long userId) {
        return shareFacade.getSharesByUserId(userId);
    }
}
