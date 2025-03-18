package com.edo.auth_service.entity;


import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User {
		@GeneratedValue(strategy = GenerationType.UUID)
		@Id
		String id;

		@Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
		String username;
		String password;
		String firstName;
		String lastName;
		String email;
		LocalDate dob;

		@ManyToMany
		Set<Role> role;

}
