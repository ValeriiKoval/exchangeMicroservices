package com.microservice.orderservice.facade.impl;

import com.microservice.orderservice.facade.ShareFacade;
import com.microservice.orderservice.mapper.ShareMapper;
import com.microservice.orderservice.model.Share;
import com.microservice.orderservice.payload.response.ShareResponse;
import com.microservice.orderservice.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShareFacadeImpl implements ShareFacade {

    private final ShareService shareService;
    private final ShareMapper shareMapper;

    @Override
    public List<ShareResponse> getSharesByUserId(final Long userId) {
        final List<Share> shares = shareService.findAllByUserId(userId);
        return shareMapper.toShareResponseList(shares);
    }
}
