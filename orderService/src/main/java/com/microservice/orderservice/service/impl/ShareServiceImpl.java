package com.microservice.orderservice.service.impl;

import com.microservice.orderservice.exception.InvalidOperationException;
import com.microservice.orderservice.exception.ResourceNotFoundException;
import com.microservice.orderservice.model.Share;
import com.microservice.orderservice.repository.ShareRepository;
import com.microservice.orderservice.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShareServiceImpl implements ShareService {

    private final ShareRepository shareRepository;

    @Override
    public List<Share> findAllByUserId(final Long userId) {
        return shareRepository.findAllByUserIdAndAmountGreaterThan(userId, 0L);
    }

    @Override
    public Share findByUserIdAndCompanySymbol(final Long userId, final String companySymbol) {
        return shareRepository.findByUserIdAndCompanySymbol(userId, companySymbol)
                .orElseThrow(() -> new ResourceNotFoundException("Share with user id " + userId + " " +
                                                                 "and company symbol " + companySymbol + " not found"));
    }

    @Override
    public Share addOrCreate(final Long userId, final String companySymbol, final Long amount) {
        final Share userShare = this.findByUserIdAndCompanySymbolWithoutException(userId, companySymbol);
        if (userShare == null) {
            return this.create(userId, companySymbol, amount);
        }
        return this.increaseAmount(userShare, amount);
    }

    @Override
    public Share create(final Long userId, final String companySymbol, final Long amount) {
        final Share share = new Share();
        share.setUserId(userId);
        share.setCompanySymbol(companySymbol);
        share.setAmount(amount);
        return this.save(share);
    }

    @Override
    public Share increaseAmount(final Share share, final Long amount) {
        final Long currentAmount = share.getAmount();
        share.setAmount(currentAmount + amount);
        return this.save(share);
    }

    @Override
    public Share reduceAmount(final Share share, final Long amount) {
        final Long currentAmount = share.getAmount();
        if (currentAmount < amount) {
            throw new InvalidOperationException("Don't have enough shares to create order");
        }
        share.setAmount(currentAmount - amount);
        return this.save(share);
    }

    @Override
    public Share save(final Share share) {
        return shareRepository.save(share);
    }

    private Share findByUserIdAndCompanySymbolWithoutException(final Long userId, final String companySymbol) {
        return shareRepository.findByUserIdAndCompanySymbol(userId, companySymbol)
                .orElse(null);
    }
}
