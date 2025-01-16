package com.alexspohr.user_service.adapters.in.web.controller;

import com.alexspohr.user_service.adapters.in.security.JwtContent;
import com.alexspohr.user_service.adapters.in.security.JwtValidator;
import com.alexspohr.user_service.core.domain.exception.UserNotAuthenticatedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/user/v1/auth")
public class AuthController {

    private final JwtValidator jwtValidator;

    public AuthController(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @PostMapping("/validate-token")
    public ResponseEntity<JwtContent> validateToken(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(jwtValidator.validateToken(token)
                    .orElseThrow());
        } catch (NoSuchElementException e) {
            throw new UserNotAuthenticatedException("User isn't provider a access token in a valid format");
        }
    }

}
