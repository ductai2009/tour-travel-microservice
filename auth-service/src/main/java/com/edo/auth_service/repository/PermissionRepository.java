package com.edo.auth_service.repository;

import com.edo.auth_service.entity.Permission;
import com.edo.auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String>{

    boolean existsByName(String name);

    Optional<Permission> findByName(String name);


    void deleteByName(String name);
}
