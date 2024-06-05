package com.microservice.orderservice.facade;

import com.microservice.orderservice.payload.response.ShareResponse;

import java.util.List;

public interface ShareFacade {
    List<ShareResponse> getSharesByUserId(Long userId);
}
