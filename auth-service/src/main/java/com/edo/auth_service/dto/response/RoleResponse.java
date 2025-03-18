package com.edo.auth_service.dto.response;

import com.edo.auth_service.entity.Permission;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
    String name;
    String description;
    Set<Permission> permissions;
}
