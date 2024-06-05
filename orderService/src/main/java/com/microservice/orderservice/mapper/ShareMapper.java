package com.microservice.orderservice.mapper;

import com.microservice.orderservice.model.Share;
import com.microservice.orderservice.payload.response.ShareResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShareMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "companySymbol", source = "companySymbol")
    @Mapping(target = "amount", source = "amount")
    ShareResponse toShareResponse(Share entity);

    List<ShareResponse> toShareResponseList(List<Share> entities);

}
