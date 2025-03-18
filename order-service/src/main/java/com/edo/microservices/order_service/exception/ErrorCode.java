package com.edo.microservices.order_service.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    ORDER_NUMBER_EXISTED(1001, "Order number already exists", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1002, "Uncategorized error", HttpStatus.BAD_REQUEST),
    NOT_FOUND(1003,"Not found", HttpStatus.NOT_FOUND),
    ORDER_NOT_EXISTED(1004, "Order not existed", HttpStatus.NOT_FOUND),

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
