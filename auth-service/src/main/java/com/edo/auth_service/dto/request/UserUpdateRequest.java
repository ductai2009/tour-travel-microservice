package com.edo.auth_service.dto.request;

import com.edo.auth_service.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
     String username;
     String password;
     String firstName;
     String lastName;
     String email;
     LocalDate dob;
     Set<String> role;


}
