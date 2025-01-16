package com.alexspohr.user_service.adapters.in.security.authprovider.google;

public record GoogleResponse(
        String id,           // ID no Google
        String email,        // Email
        boolean verifiedEmail, // Indica se o email foi verificado
        String name,         // Nome completo do usuário
        String givenName,    // Primeiro nome (Given Name)
        String familyName,   // Sobrenome (Family Name)
        String picture,      // URL da foto de perfil do usuário
        String locale        // Idioma preferido do usuário
) {
}
