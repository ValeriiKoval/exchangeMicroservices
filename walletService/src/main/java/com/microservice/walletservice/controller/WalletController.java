package com.microservice.walletservice.controller;

import com.microservice.walletservice.facade.WalletFacade;
import com.microservice.walletservice.payload.request.BalanceChangeRequest;
import com.microservice.walletservice.payload.response.WalletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletFacade walletFacade;

    @PostMapping
    public ResponseEntity<Void> createWallet(@RequestHeader final Long userId) {
        walletFacade.createWallet(userId);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public ResponseEntity<WalletResponse> get(@RequestHeader final Long userId) {
        return ResponseEntity.ok(walletFacade.getByUser(userId));
    }

    @PutMapping("/topup")
    public ResponseEntity<WalletResponse> topUp(@RequestHeader final Long userId,
                                                @RequestBody @Valid final BalanceChangeRequest request) {
        return ResponseEntity.ok(walletFacade.topUp(userId, request));
    }

    @PutMapping("/withdraw")
    public ResponseEntity<WalletResponse> withdraw(@RequestHeader final Long userId,
                                                   @RequestBody @Valid final BalanceChangeRequest request) {
        return ResponseEntity.ok(walletFacade.withdraw(userId, request));
    }
}
