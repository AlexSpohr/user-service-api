package com.alexspohr.user_service.adapters.in.web.controller.dto;

import com.alexspohr.user_service.core.domain.exception.MultipleConflictsException;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record RestErrorResponse(
        String title,
        String type,
        Integer status,
        String detail,
        String instance,
        LocalDateTime timestamp,
        @JsonInclude(JsonInclude.Include.NON_NULL) List<FieldErrorDetails> errors
) {
    public RestErrorResponse(String type, String title, HttpStatus status, String detail, String instance) {
        this(title, type, status.value(), detail, instance, LocalDateTime.now(), null);
    }

    public RestErrorResponse(String type, String title, HttpStatus status, String detail, String instance, BindingResult result) {
        this(title, type, status.value(), detail, instance, LocalDateTime.now(), null);
        addDetails(result);
    }


    private record FieldErrorDetails(String field, String pointer) {
    }

    private void addDetails(BindingResult bindingResult) {
        List<FieldErrorDetails> details = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            details.add(new FieldErrorDetails(fieldError.getDefaultMessage(), fieldError.getField()));
        }
        this.errors.addAll(details);
    }

    public RestErrorResponse(String type, String title, HttpStatus status, String detail, String instance, List<MultipleConflictsException.ErrorDetail> errors) {
        this(title, type, status.value(), detail, instance, LocalDateTime.now(), null);
        this.errors.addAll(convertToFielErrorDetails(errors));
    }

    public List<FieldErrorDetails> convertToFielErrorDetails(List<MultipleConflictsException.ErrorDetail> errors) {
        List<FieldErrorDetails> errorDetails = new ArrayList<>();
        errors.forEach(e -> e.getDetails().forEach(details -> errorDetails.add(new FieldErrorDetails(e.getType(), details))));
        return errorDetails;
    }
}
