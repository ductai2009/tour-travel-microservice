package com.edo.microservices.order_service.mapper;

import com.edo.microservices.order_service.dto.request.OrderRequest;
import com.edo.microservices.order_service.dto.request.OrderUpdateRequest;
import com.edo.microservices.order_service.dto.response.OrderResponse;
import com.edo.microservices.order_service.entity.Orders;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)

    Orders toOrder(OrderRequest request);


    OrderResponse toOrderResponse(Orders order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderNumber", ignore = true)
    Orders updateOrder(@MappingTarget Orders order, OrderUpdateRequest request);
}