package com.alexspohr.user_service.adapters.in.web.exception;

import com.alexspohr.user_service.adapters.in.web.controller.dto.RestErrorResponse;
import com.alexspohr.user_service.core.domain.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        logger.error(ex.getMessage(), ex);

        var status = HttpStatus.NOT_FOUND;
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RestErrorResponse("about:blank", getExceptionTitle(ex.getTitle(), status), status, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(ResourceNotValidException.class)
    public ResponseEntity<RestErrorResponse> handleResourceNotValidException(ResourceNotValidException ex, HttpServletRequest request) {
        logger.error(ex.getMessage(), ex);

        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RestErrorResponse("about:blank", getExceptionTitle(ex.getTitle(), status), status, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(ServiceBadRequestException.class)
    public ResponseEntity<RestErrorResponse> handleServiceBadRequestException(ServiceBadRequestException ex, HttpServletRequest request) {
        logger.error(ex.getMessage(), ex);

        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RestErrorResponse("about:blank", getExceptionTitle(ex.getTitle(), status), status, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(ResourceConflictsException.class)
    public ResponseEntity<RestErrorResponse> handleResourceConflictsException(ResourceConflictsException ex, HttpServletRequest request) {
        logger.error(ex.getMessage(), ex);

        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RestErrorResponse("about:blank", "Resource conflicts with existing data", status, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<RestErrorResponse> handleUserNotAuthenticatedException(UserNotAuthenticatedException ex, HttpServletRequest request) {
        logger.error(ex.getMessage(), ex);

        var status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RestErrorResponse("about:blank", getExceptionTitle(ex.getTitle(), status), status, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<RestErrorResponse> handleUserNotAuthorizedException(UserNotAuthorizedException ex, HttpServletRequest request) {
        logger.error(ex.getMessage(), ex);

        var status = HttpStatus.FORBIDDEN;
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RestErrorResponse("about:blank", getExceptionTitle(ex.getTitle(), status), status, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<RestErrorResponse> handleServiceUnavailableException(ServiceUnavailableException ex, HttpServletRequest request) {
        logger.error(ex.getMessage(), ex);

        var status = HttpStatus.SERVICE_UNAVAILABLE;
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RestErrorResponse("about:blank", getExceptionTitle(ex.getTitle(), status), status, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<RestErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        logger.error(ex.getMessage(), ex);

        var status = HttpStatus.METHOD_NOT_ALLOWED;
        var message = "HTTP method not supported: " + ex.getMethod();
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RestErrorResponse("about:blank", "Method Not Supported", status, message, request.getRequestURI()));

    }

    //TODO: IllegalArgument
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        logger.error(ex.getMessage(), ex);

        var status = HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new RestErrorResponse("about:blank", "Invalid Argument", status, ex.getMessage(), request.getRequestURI()));
    }

    private String getExceptionTitle(String title, HttpStatus status) {
        return Optional.ofNullable(title)
                .orElse(status.getReasonPhrase());
    }
}
