package com.edo.microservices.file_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_NOT_EXISTED(1001, "File not existed exists", HttpStatus.NOT_FOUND),
    INVALID_KEY(1002, "Uncategorized error", HttpStatus.BAD_REQUEST),
    NOT_FOUND(1003,"Not found", HttpStatus.NOT_FOUND),
    ERROR_SAVE(1004,"Error save file", HttpStatus.SERVICE_UNAVAILABLE),
    ERROR_READ_FILE(1005,"Error read file", HttpStatus.SERVICE_UNAVAILABLE),
    ERROR_DELETE_FILE(1005,"Error delete file", HttpStatus.SERVICE_UNAVAILABLE),
    ;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatus statusCode;


}
