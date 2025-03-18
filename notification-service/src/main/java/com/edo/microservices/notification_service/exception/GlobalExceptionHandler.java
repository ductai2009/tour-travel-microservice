package com.edo.microservices.notification_service.exception;


import com.edo.microservices.notification_service.dto.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


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




}
