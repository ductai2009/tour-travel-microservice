package com.edo.microservices.order_service.dto.response;

import com.edo.microservices.order_service.enums.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Integer orderNumber;
    BigDecimal price;
    String customerName;
    String tourName;
    LocalDate departureDate;
    int guestCount;
    OrderStatus status;
    LocalDateTime createdAt;
}
