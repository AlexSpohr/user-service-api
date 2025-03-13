package com.alexspohr.user_service.adapters.in.security.authprovider.google;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleResponse(
        String email,
        String name,
        @JsonProperty("given_name")
        String givenName,
        @JsonProperty("family_name")
        String familyName,
        String picture
) {
}
