package com.edo.auth_service.exception;

import com.edo.auth_service.dto.request.ResponseData;
import com.edo.auth_service.entity.User;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ResponseData<User>> handlingRuntimeException(RuntimeException exception) {
        log.info("Exception ", exception);
        ResponseData<User> responseData = new ResponseData<User>();

        responseData.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        responseData.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(responseData);
    }


    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ResponseData<User>> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        ResponseData<User> responseData = new ResponseData<User>();
        responseData.setCode(errorCode.getCode());
        responseData.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(responseData);

    }
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ResponseData<User>> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ResponseData.<User>builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    ResponseEntity<ResponseData<User>> handlingDataIntegrityViolationException(DataIntegrityViolationException exception) {
        ErrorCode errorCode = ErrorCode.DATA_DELETE_ERROR;

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ResponseData.<User>builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    ResponseEntity<ResponseData<User>> handleNotValid(MethodArgumentNotValidException exception) {

        ResponseData<User> response = new ResponseData<>();

        String numKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();

        ErrorCode errorCode = null;

        Map<String, Object> attributes = null;

        try{
            errorCode = ErrorCode.valueOf(numKey);

            var constraintViolation = exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();

            log.info(attributes.toString());

        }catch (Exception e){
            errorCode = ErrorCode.INVALID_KEY;
        }
        response.setCode(errorCode.getCode());

        response.setMessage(Objects.nonNull(attributes)
                ? mapAttribute(errorCode.getMessage(), attributes)
                : errorCode.getMessage());
        

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(response);
    }
    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }



}
