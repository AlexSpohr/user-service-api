package com.alexspohr.user_service.adapters.in.security;

import com.alexspohr.user_service.core.port.out.AuthorizationContextOutputPort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationContextAdapter implements AuthorizationContextOutputPort {
    @Override
    public String getAuthorizationUser() {
        var userAuth = SecurityContextHolder.getContext().getAuthentication();
        return userAuth.getName();
    }
}
