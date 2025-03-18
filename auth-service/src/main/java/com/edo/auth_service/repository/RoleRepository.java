package com.edo.auth_service.repository;

import com.edo.auth_service.entity.Role;
import com.edo.auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{

    boolean existsByName(String name);


    List<Role> findAllByNameIn(Collection<String> name);

    Optional<Role> findByName(String name);


    void deleteByName(String name);
}
