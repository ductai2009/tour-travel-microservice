package com.edo.microservices.product_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Tour {

    @Id
    ObjectId id;

    @Column(nullable = false)
    String name;

    @Column(columnDefinition = "TEXT")
    String description;

    @Column(nullable = false)
    String location;

    @Column(nullable = false)
    BigDecimal price;

    @Column(nullable = false)
    Integer duration;

    @Column(name = "image_url")
    String imageUrl;

    @Column(name = "created_at", updatable = false)
    LocalDate createdAt;
}
