package com.alexspohr.user_service.adapters.in.web.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(
        @NotBlank(message = "Name cant be blank")
        String name,
        String email) {
}
