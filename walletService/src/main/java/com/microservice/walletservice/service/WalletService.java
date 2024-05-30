package com.microservice.walletservice.service;

import com.microservice.walletservice.model.Wallet;
import com.microservice.walletservice.payload.request.BalanceChangeRequest;

public interface WalletService {
    Wallet findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    void saveWallet(Wallet wallet);

    void createNewWallet(Long userId);

    void topUp(Wallet wallet, BalanceChangeRequest request);

    void withdraw(Wallet wallet, BalanceChangeRequest request);
}
