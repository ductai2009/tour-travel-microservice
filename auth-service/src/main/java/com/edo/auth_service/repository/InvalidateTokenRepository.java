package com.edo.auth_service.repository;

import com.edo.auth_service.entity.InvalidateToken;
import com.edo.auth_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvalidateTokenRepository extends JpaRepository<InvalidateToken, String>{


}
