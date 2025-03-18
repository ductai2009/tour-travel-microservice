package com.edo.microservices.order_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {

    @Positive(message = "NOT_NULL")
    Integer orderNumber;

    @Positive(message = "NOT_NULL")
    BigDecimal price;

    @NotBlank(message = "NOT_NULL")
    String customerName;

    @NotBlank(message = "NOT_NULL")
    String tourName;
    LocalDate departureDate;
    Integer guestCount;
}
