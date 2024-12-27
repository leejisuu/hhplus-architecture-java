package io.hhplus.architecture.interfaces.api;

import io.hhplus.architecture.support.exception.CustomException;
import io.hhplus.architecture.support.exception.ErrorCode;
import io.hhplus.architecture.support.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(
                errorCode.getMessage(),
                errorCode.getStatus().value()
        );
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }
}
