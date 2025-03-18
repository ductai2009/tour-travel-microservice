package com.edo.microservices.product_service.dto.request;


import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourRequest {


    @NotBlank(message = "NOT_NULL")
    String name;
    String description;

    @NotBlank(message = "LOCATION_NULL")
    String location;

    @Positive(message = "PRICE_NULL")
    BigDecimal price;

    @Positive(message = "DURATION_NULL")
    Integer duration;

    String imageUrl;

}
