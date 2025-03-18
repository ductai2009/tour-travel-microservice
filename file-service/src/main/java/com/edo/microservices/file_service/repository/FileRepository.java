package com.edo.microservices.file_service.repository;


import com.edo.microservices.file_service.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, String> {

    Optional<File> findByFileName(String fileName);
}
