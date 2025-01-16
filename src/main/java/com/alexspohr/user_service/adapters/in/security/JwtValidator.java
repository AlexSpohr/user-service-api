package com.alexspohr.user_service.adapters.in.security;

import java.util.Optional;

public interface JwtValidator {
    Optional<JwtContent> validateToken(String jwtToken);
}
