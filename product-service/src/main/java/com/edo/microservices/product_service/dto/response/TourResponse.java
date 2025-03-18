package com.edo.microservices.product_service.dto.response;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourResponse {
    String id;
    String name;
    String description;
    String location;
    BigDecimal price;
    int duration;
    String imageUrl;
}
