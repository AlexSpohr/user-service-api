package com.alexspohr.user_service.core.domain.exception;

public class ResourceNotFoundException extends RuntimeException {
    private String title;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String title, String message) {
        super(message);
        this.title = title;
    }

    public ResourceNotFoundException(String entity, String identifier, String id) {
        super(String.format("%s not found with %s %s", entity, identifier,id));
        this.title = entity + " Not Found";
    }

    public String getTitle() {
        return title;
    }
}
