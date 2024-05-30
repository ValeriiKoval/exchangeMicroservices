package com.microservice.walletservice.mapper;

import com.microservice.walletservice.model.Wallet;
import com.microservice.walletservice.payload.response.WalletResponse;
import com.microservice.walletservice.util.UtilService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "balance", source = "balance", qualifiedByName = "convertBalance")
    WalletResponse toWalletResponse(Wallet entity);

    @Named("convertBalance")
    static double convertBalance(Long balance) {
        if (balance == null) {
            return 0D;
        }
        return UtilService.centsToDollars(balance);
    }
}
