package com.alexspohr.user_service.core.domain.exception;

public class ServiceBadRequestException extends RuntimeException {
    public String title;
    public ServiceBadRequestException(String message) {
        super(message);
    }

    public ServiceBadRequestException(String title ,String message) {
        super(message);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
