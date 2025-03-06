package com.alexspohr.user_service.adapters.in.web.filter;

import com.alexspohr.user_service.adapters.in.security.JwtValidator;
import com.alexspohr.user_service.adapters.in.web.controller.dto.RestErrorResponse;
import com.alexspohr.user_service.core.domain.exception.ServiceTimeoutException;
import com.alexspohr.user_service.core.domain.exception.ServiceUnavailableException;
import com.alexspohr.user_service.core.port.in.VerifyAndPersistUserInputPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final RequestMatcher permittedUriRequestMatchers;
    private final ObjectMapper objectMapper;
    private final JwtValidator jwtValidator;
    private final VerifyAndPersistUserInputPort verifyAndPersistUserInputPort;

    public AuthenticationFilter(RequestMatcher permittedUriRequestMatchers,
                                ObjectMapper objectMapper,
                                JwtValidator jwtValidator,
                                VerifyAndPersistUserInputPort verifyAndPersistUserInputPort) {
        this.permittedUriRequestMatchers = permittedUriRequestMatchers;
        this.objectMapper = objectMapper;
        this.jwtValidator = jwtValidator;
        this.verifyAndPersistUserInputPort = verifyAndPersistUserInputPort;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (permittedUriRequestMatchers.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            var authHeader = request.getHeader("Authorization");

            var jwtContent = jwtValidator.validateToken(authHeader)
                    .orElseThrow();

            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

            Authentication authentication = new UsernamePasswordAuthenticationToken(jwtContent.getEmail(), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            verifyAndPersistUserInputPort.verifyAndPersistUser(jwtContent);

        } catch (RuntimeException ex) {
            response.setStatus(401);
            var w = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if (ex instanceof NoSuchElementException) {
                w.print(objectMapper.writeValueAsString(new RestErrorResponse("about:blank",
                        "User isn't authenticated to access this resource",
                        HttpStatus.UNAUTHORIZED,
                        "User didn't provided a bearer token in a valid format",
                        request.getRequestURI())));
            } else if (ex instanceof ServiceUnavailableException) {
                response.setStatus(503);
                w.print(objectMapper.writeValueAsString(new RestErrorResponse("about:blank",
                        "Service Unavailable: Unable complete this request",
                        HttpStatus.SERVICE_UNAVAILABLE,
                        ex.getMessage(),
                        request.getRequestURI())));
            } else if (ex instanceof ServiceTimeoutException) {
                response.setStatus(408);
                w.print(objectMapper.writeValueAsString(new RestErrorResponse("about:blank",
                        "Service Timeout: Request timed out",
                        HttpStatus.SERVICE_UNAVAILABLE,
                        ex.getMessage(),
                        request.getRequestURI())));
            } else {
                w.print(objectMapper.writeValueAsString(new RestErrorResponse("about:blank",
                        "User isn't authenticated to access this resource",
                        HttpStatus.UNAUTHORIZED,
                        ex.getMessage(),
                        request.getRequestURI())));
            }

            w.flush();
            return;
        }

        filterChain.doFilter(request, response);
    }
}