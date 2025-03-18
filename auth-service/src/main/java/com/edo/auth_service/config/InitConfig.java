package com.edo.auth_service.config;


import com.edo.auth_service.entity.Role;
import com.edo.auth_service.entity.User;

import com.edo.auth_service.repository.RoleRepository;
import com.edo.auth_service.repository.UserRepository;
import com.edo.auth_service.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;



@Slf4j
@Configuration
public class InitConfig {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Bean
    ApplicationRunner applicationRunner(){
         return args -> {

             if (!userRepository.existsByUsername("admin")) {
                 Role role;


                 if (!roleRepository.existsByName("ADMIN")) {
                     role = Role.builder()
                             .name("ADMIN")
                             .build();

                     role = roleRepository.save(role);
                     log.warn("Created role ADMIN");
                 } else {
                     role = roleRepository.findByName("ADMIN")
                             .orElseThrow(() -> new Exception("No role ADMIN found"));
                 }

                 HashSet<Role> roles = new HashSet<>();
                 roles.add(role);

                 User user = User.builder()
                         .username("admin")
                         .password(passwordEncoder.encode("admin"))
                         .role(roles)
                         .build();

                 userRepository.save(user);

                 log.warn("Created user admin");
             }


         };
    }
}
