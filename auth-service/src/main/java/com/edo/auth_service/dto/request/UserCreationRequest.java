package com.edo.auth_service.dto.request;

import com.edo.auth_service.entity.Role;
import com.edo.auth_service.validator.DateConstrainValidator;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

	@Size(min = 3, message = "INVALID_USERNAME")
	 String username;

	@Size(min = 4, message = "INVALID_PASSWORD")
	 String password;
	 String firstName;
	 String lastName;
	 String email;
	 @DateConstrainValidator(min = 16, message = "INVALID_DOB")
	 LocalDate dob;
	 Set<String> role;
}
