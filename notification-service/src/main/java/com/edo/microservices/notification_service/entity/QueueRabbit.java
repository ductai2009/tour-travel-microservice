package com.edo.microservices.notification_service.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QueueRabbit {
    String queue;
    String message;

}
