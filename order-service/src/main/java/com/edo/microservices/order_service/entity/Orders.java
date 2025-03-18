package com.edo.microservices.order_service.entity;


import com.edo.microservices.order_service.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "order_number", nullable = false, unique = true)
    Integer orderNumber;

    @Column(nullable = false)
    BigDecimal price;

    @Column(name = "customer_name", nullable = false)
    String customerName;

    @Column(name = "tour_name", nullable = false)
    String tourName;

    @Column(name = "departure_date", nullable = false)
    LocalDate departureDate;

    @Column(name = "guest_count", nullable = false)
    Integer guestCount;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    OrderStatus status = OrderStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;

}
