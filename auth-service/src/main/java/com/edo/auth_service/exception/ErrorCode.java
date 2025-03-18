package com.edo.auth_service.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "Username already exists", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1002, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1004, "Uncategorized error", HttpStatus.BAD_REQUEST),
    NOT_FOUND(1005, "Not found", HttpStatus.NOT_FOUND),
    USER_NOT_EXISTED(1006, "Username not exists", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1007, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008, "You do not have permission", HttpStatus.FORBIDDEN),
    ROLE_EXISTED(1009, "Role already exists", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(1010, "Permission already exists", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(10011, "Role not exists", HttpStatus.NOT_FOUND),
    PERMISSION_NOT_EXISTED(10012, "Permission not exists", HttpStatus.NOT_FOUND),
    PERMISSION_IN_ROLE_EXISTED(10013, "Permission in create role is require", HttpStatus.BAD_REQUEST),
    DATA_DELETE_ERROR(10014, "Not delete data", HttpStatus.BAD_REQUEST),
    INVALID_DOB(10015, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
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
