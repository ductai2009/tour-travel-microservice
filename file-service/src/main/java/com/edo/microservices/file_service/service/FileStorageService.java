package com.edo.microservices.file_service.service;

import com.edo.microservices.file_service.dto.response.ResponseData;
import com.edo.microservices.file_service.entity.File;
import com.edo.microservices.file_service.exception.AppException;
import com.edo.microservices.file_service.exception.ErrorCode;
import com.edo.microservices.file_service.repository.FileRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileStorageService {


    final Path fileStorageLocation;

    @Autowired
    FileRepository fileRepository;


    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            log.error(ex.toString());
            throw new RuntimeException("Fail create folder upload file!", ex);
        }
    }


    public ResponseData<String> storeFile(MultipartFile file) {
        try {
            Path targetLocation = this.fileStorageLocation.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            File fileStore = File.builder()
                    .urlFile(file.getOriginalFilename())
                    .fileName(file.getOriginalFilename())
                    .build();
            fileRepository.save(fileStore);
            return ResponseData.<String>builder()
                    .result("File save successfully: " + targetLocation.toString())
                    .build();
        } catch (IOException ex) {
            log.error(ex.toString());
            throw new AppException(ErrorCode.ERROR_SAVE);
        }
    }


    public byte[] readFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            return Files.readAllBytes(filePath);
        } catch (IOException ex) {
            log.error(ex.toString());
            throw new AppException(ErrorCode.ERROR_READ_FILE);
        }
    }


    public ResponseData<String> deleteFile(String fileName) {
        try {

            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

            if(!Files.deleteIfExists(filePath))
                throw new AppException(ErrorCode.ERROR_DELETE_FILE);

            File fileStore = fileRepository.findByFileName(fileName)
                    .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_EXISTED));

            fileRepository.deleteById(fileStore.getId());

            return ResponseData.<String>builder()
                    .result("Delete successfully: " + fileName)
                    .build();
        } catch (IOException ex) {
            log.error(ex.toString());
            throw new RuntimeException("Error delete file: " + fileName, ex);
        }
    }
}
