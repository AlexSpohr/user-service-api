package com.alexspohr.user_service.core.domain.exception;

public class ServiceTimeoutException extends RuntimeException {
    public ServiceTimeoutException(String message) {
        super(message);
    }
}
