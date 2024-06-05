package com.microservice.orderservice.service;

import com.microservice.orderservice.model.Share;

import java.util.List;

public interface ShareService {
    List<Share> findAllByUserId(Long userId);

    Share findByUserIdAndCompanySymbol(Long userId, String companySymbol);

    Share addOrCreate(Long userId, String companySymbol, Long amount);

    Share create(Long userId, String companySymbol, Long amount);

    Share increaseAmount(Share share, Long amount);

    Share reduceAmount(Share share, Long amount);

    Share save(Share share);
}
