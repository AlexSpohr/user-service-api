package com.alexspohr.user_service.core.domain.exception;

import java.util.List;

public class MultipleConflictsException extends RuntimeException {
    private List<ErrorDetail> errors;

    public MultipleConflictsException(String message) {
        super(message);
    }

    public MultipleConflictsException(List<String> notFound, List<String> alreadyExists) {
        super("Multiple conflicts in request");
        this.errors = List.of(new ErrorDetail("Not Found", notFound), new ErrorDetail("Already exists", alreadyExists));
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public static class ErrorDetail {
        private String type;
        private List<String> details;

        public ErrorDetail(String type, List<String> details) {
            this.type = type;
            this.details = details;
        }

        public String getType() {
            return type;
        }

        public List<String> getDetails() {
            return details;
        }
    }
}
