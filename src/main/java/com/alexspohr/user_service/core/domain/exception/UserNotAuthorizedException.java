package com.alexspohr.user_service.core.domain.exception;

public class UserNotAuthorizedException extends RuntimeException {
    public String title;

    public UserNotAuthorizedException(String message) {
        super(message);
    }

    public UserNotAuthorizedException(String title,String message) {
        super(message);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
