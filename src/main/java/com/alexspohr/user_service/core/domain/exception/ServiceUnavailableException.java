package com.alexspohr.user_service.core.domain.exception;

public class ServiceUnavailableException extends RuntimeException {
    public String title;

    public ServiceUnavailableException(String message) {
        super(message);
    }

    public ServiceUnavailableException(String title,String message) {
        super(message);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
