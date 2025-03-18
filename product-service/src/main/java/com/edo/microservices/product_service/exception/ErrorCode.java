package com.edo.microservices.product_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_DATA(1001, "invalid data must be at least 5 characters", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1002, "Invalid key error", HttpStatus.BAD_REQUEST),
    NAME_NULL(1003, "Name is require", HttpStatus.BAD_REQUEST),
    LOCATION_NULL(1004, "Location is require", HttpStatus.BAD_REQUEST),
    PRICE_NULL(1005, "Price is require", HttpStatus.BAD_REQUEST),
    DURATION_NULL(1006, "Duration is require", HttpStatus.BAD_REQUEST),
    TOUR_NOT_EXISTED(1007, "Tour not existed", HttpStatus.NOT_FOUND),
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
