package com.edo.auth_service.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class IntrospectResponse {
    boolean valid;
}
