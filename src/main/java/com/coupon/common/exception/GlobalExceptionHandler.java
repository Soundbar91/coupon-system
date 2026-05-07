package com.coupon.common.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.coupon.common.dto.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomException(
        CustomException customException, HttpServletRequest request
    ) {
        HttpStatus httpStatus = customException.getHttpStatus();
        String message = customException.getMessage();

        requestLogging(request, httpStatus.value(), message);

        return ResponseEntity.status(httpStatus.value()).body(ApiResponse.fail(message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception exception, HttpServletRequest request) {
        ErrorCode serverError = ErrorCode.SERVER_ERROR;
        HttpStatus httpStatus = serverError.getHttpStatus();
        String message = serverError.getMessage();

        requestLogging(request, httpStatus.value(), exception.getMessage());

        return ResponseEntity.status(httpStatus.value()).body(ApiResponse.fail(message));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
    ) {
        String message = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));

        requestLogging(getHttpServletRequest(request), status.value(), message);

        return ResponseEntity.status(status).body(ApiResponse.fail(message));
    }

    private void requestLogging(HttpServletRequest request, int httpStatus, String errorMessage) {
        log.warn("[{}] {} | request={} {}", httpStatus, errorMessage, request.getMethod(), request.getRequestURI());
    }

    private HttpServletRequest getHttpServletRequest(WebRequest request) {
        if (request instanceof ServletWebRequest servletWebRequest) {
            return servletWebRequest.getRequest();
        }
        return null;
    }

}
