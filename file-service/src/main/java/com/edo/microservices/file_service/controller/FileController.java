package com.edo.microservices.file_service.controller;

import com.edo.microservices.file_service.dto.response.ResponseData;
import com.edo.microservices.file_service.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }


    @PostMapping("/upload")
    public ResponseData<String> uploadFile(@RequestParam("file") MultipartFile file) {
        ResponseEntity.ok(fileStorageService.storeFile(file));

        return ResponseData.<String>builder()
                .result("File save successfully")
                .build();
    }


    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        byte[] fileData = fileStorageService.readFile(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(fileData);
    }


    @DeleteMapping("/delete/{fileName}")
    public ResponseData<String> deleteFile(@PathVariable String fileName) {

        ResponseEntity.ok(fileStorageService.deleteFile(fileName));
        return ResponseData.<String>builder()
                .result("Delete successfully")
                .build();
    }
}
