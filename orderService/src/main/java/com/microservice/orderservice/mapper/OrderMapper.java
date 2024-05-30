package com.microservice.orderservice.mapper;

import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.payload.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "companySymbol", source = "companySymbol")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "type", source = "type.name")
    @Mapping(target = "status", source = "status.name")
    OrderResponse toOrderResponse(Order entity);

    List<OrderResponse> toOrderResponseList(List<Order> entities);

}
