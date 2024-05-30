package com.microservice.walletservice.facade;

import com.microservice.walletservice.payload.request.BalanceChangeRequest;
import com.microservice.walletservice.payload.response.WalletResponse;

public interface WalletFacade {
    void createWallet(Long userId);

    WalletResponse topUp(Long userId, BalanceChangeRequest request);

    WalletResponse withdraw(Long userId, BalanceChangeRequest request);

    WalletResponse getByUser(Long userId);
}
