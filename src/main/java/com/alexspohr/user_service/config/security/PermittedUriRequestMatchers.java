package com.alexspohr.user_service.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class PermittedUriRequestMatchers {

    @Bean
    RequestMatcher permittedUris() {
        return new OrRequestMatcher(
                new AntPathRequestMatcher("api/v1/swagger-ui/**"),
                new AntPathRequestMatcher("api/v1/swagger.html"),
                new AntPathRequestMatcher("api/v1/auth/validate-token")
                );
    }

}
