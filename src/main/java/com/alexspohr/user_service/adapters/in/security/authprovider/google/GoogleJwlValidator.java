package com.alexspohr.user_service.adapters.in.security.authprovider.google;

import com.alexspohr.user_service.adapters.in.security.JwtContent;
import com.alexspohr.user_service.adapters.in.security.JwtValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Supplier;

import static com.alexspohr.user_service.adapters.out.client.WebClientWrapper.executeWebClientRequest;

@Component
@Profile("authprovider-google")
public class GoogleJwlValidator implements JwtValidator {
    private final Logger logger = LoggerFactory.getLogger(GoogleJwlValidator.class);
    private final WebClient webClient;

    public GoogleJwlValidator(@Qualifier("google") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Optional<JwtContent> validateToken(String jwtToken) {
        if (jwtToken == null || !jwtToken.startsWith("Bearer ")) return Optional.empty();

        logger.info("Calling Google to validate jwt token");

        String idToken = jwtToken.substring(7);
        String googleUri = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;

        // Faz a requisição para validar o token
        Supplier<Mono<GoogleResponse>> request = () -> webClient.get().uri(googleUri)
                .retrieve()
                .bodyToMono(GoogleResponse.class);

        GoogleResponse response = executeWebClientRequest(request, "google", "google", null);

        var content = new JwtContent(response.name(), response.email());

        return Optional.of(content);
    }
}
