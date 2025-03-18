package com.edo.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edo.auth_service.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

    boolean existsByUsername(String Username);

    Optional<User> findByUsername(String username);
}
