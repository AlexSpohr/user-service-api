package com.alexspohr.user_service.core.domain.exception;

public class ResourceConflictsException extends RuntimeException {
    public ResourceConflictsException(String entity, String conflict) {
        super(String.format("Resource %s conflicts with %s", entity, conflict));
    }
}
