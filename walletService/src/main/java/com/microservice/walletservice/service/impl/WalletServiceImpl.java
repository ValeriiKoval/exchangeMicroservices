package com.microservice.walletservice.service.impl;

import com.microservice.walletservice.exception.WalletNotFoundException;
import com.microservice.walletservice.model.Wallet;
import com.microservice.walletservice.payload.request.BalanceChangeRequest;
import com.microservice.walletservice.repository.WalletRepository;
import com.microservice.walletservice.service.WalletService;
import com.microservice.walletservice.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public Wallet findByUserId(final Long userId) {
        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet with user id " + userId + " not found"));
    }

    @Override
    public boolean existsByUserId(final Long userId) {
        return walletRepository.existsByUserId(userId);
    }

    @Override
    public void saveWallet(final Wallet wallet) {
        walletRepository.save(wallet);
    }

    @Override
    public void createNewWallet(final Long userId) {
        final Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setBalance(0L);
        this.saveWallet(wallet);
    }

    @Override
    public void topUp(final Wallet wallet, final BalanceChangeRequest request) {
        final Long balance = wallet.getBalance();
        wallet.setBalance(balance + UtilService.dollarsToCents(request.getAmount()));
        this.saveWallet(wallet);
    }

    @Override
    public void withdraw(final Wallet wallet, final BalanceChangeRequest request) {
        final Long balance = wallet.getBalance();
        wallet.setBalance(balance - UtilService.dollarsToCents(request.getAmount()));
        this.saveWallet(wallet);
    }

}
