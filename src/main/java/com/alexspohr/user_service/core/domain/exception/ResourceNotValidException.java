package com.alexspohr.user_service.core.domain.exception;

public class ResourceNotValidException extends RuntimeException {
    private String title;
    public ResourceNotValidException(String message) {
        super(message);
    }

    public ResourceNotValidException(String title, String message) {
        super(message);
        this.title = title;
    }

    public ResourceNotValidException(String title, String expectedTypeOrValue, String actualTypeOrValue) {
        super(String.format("%s: expected %s, got %s", title, expectedTypeOrValue, actualTypeOrValue));
    }

    public String getTitle() {
        return title;
    }
}
