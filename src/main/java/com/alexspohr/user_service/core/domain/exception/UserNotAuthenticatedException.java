package com.alexspohr.user_service.core.domain.exception;

public class UserNotAuthenticatedException extends RuntimeException {
    public String title;

    public UserNotAuthenticatedException(String message) {
        super(message);
    }

    public UserNotAuthenticatedException(String title,String message) {
        super(message);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
