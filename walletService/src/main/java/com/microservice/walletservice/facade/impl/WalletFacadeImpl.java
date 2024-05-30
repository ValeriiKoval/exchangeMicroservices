package com.microservice.walletservice.facade.impl;

import com.microservice.walletservice.exception.ChangeException;
import com.microservice.walletservice.exception.CreationException;
import com.microservice.walletservice.facade.WalletFacade;
import com.microservice.walletservice.mapper.WalletMapper;
import com.microservice.walletservice.model.Wallet;
import com.microservice.walletservice.payload.request.BalanceChangeRequest;
import com.microservice.walletservice.payload.response.WalletResponse;
import com.microservice.walletservice.service.WalletService;
import com.microservice.walletservice.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletFacadeImpl implements WalletFacade {

    private final WalletService walletService;
    private final WalletMapper walletMapper;

    @Override
    public void createWallet(final Long userId) {
        if (walletService.existsByUserId(userId)) {
            throw new CreationException("Error: UserId is already taken!");
        }
        walletService.createNewWallet(userId);
    }

    @Override
    public WalletResponse topUp(final Long userId, final BalanceChangeRequest request) {
        final Wallet wallet = walletService.findByUserId(userId);
        walletService.topUp(wallet, request);
        return walletMapper.toWalletResponse(wallet);
    }

    @Override
    public WalletResponse withdraw(final Long userId, final BalanceChangeRequest request) {
        final Wallet wallet = walletService.findByUserId(userId);
        if (wallet.getBalance() < UtilService.dollarsToCents(request.getAmount())) {
            throw new ChangeException("Not enough money to withdraw");
        }
        walletService.withdraw(wallet, request);
        return walletMapper.toWalletResponse(wallet);
    }

    @Override
    public WalletResponse getByUser(final Long userId) {
        return walletMapper.toWalletResponse(walletService.findByUserId(userId));
    }
}
