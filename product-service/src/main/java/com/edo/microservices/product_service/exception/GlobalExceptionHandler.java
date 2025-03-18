package com.edo.microservices.product_service.exception;

import com.edo.microservices.product_service.dto.response.ResponseData;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String ATTRIBUTE_ALERT = "alert";

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ResponseData<String>> handlingRuntimeException(RuntimeException exception) {
        log.info("Exception ", exception);
        ResponseData<String> responseData = new ResponseData<String>();

        responseData.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        responseData.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(responseData);
    }
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ResponseData<String>> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        ResponseData<String> responseData = new ResponseData<String>();
        responseData.setCode(errorCode.getCode());
        responseData.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(responseData);

    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    ResponseEntity<ResponseData<String>> handleNotValid(MethodArgumentNotValidException exception) {

        ResponseData<String> response = new ResponseData<>();

        String numKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();

        ErrorCode errorCode = null;

        try{
            errorCode = ErrorCode.valueOf(numKey);

        }catch (Exception e){
            errorCode = ErrorCode.INVALID_KEY;
        }
        response.setCode(errorCode.getCode());

        response.setMessage(errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(response);
    }


}
