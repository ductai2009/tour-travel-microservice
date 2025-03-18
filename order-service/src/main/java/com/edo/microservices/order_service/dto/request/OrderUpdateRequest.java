package com.edo.microservices.order_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class OrderUpdateRequest {
    Integer orderNumber;
    BigDecimal price;
    String customerName;
    String tourName;
    LocalDate departureDate;
    Integer guestCount;
}
