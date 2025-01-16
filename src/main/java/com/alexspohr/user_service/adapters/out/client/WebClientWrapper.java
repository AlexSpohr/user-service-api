package com.alexspohr.user_service.adapters.out.client;

import com.alexspohr.user_service.core.domain.exception.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

public class WebClientWrapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    private WebClientWrapper() {
    }

    public static <T> T executeWebClientRequest(Supplier<Mono<T>> request, String url, String service, T defaultValue) throws ServiceUnavailableException {
        Logger logger = LoggerFactory.getLogger(WebClientWrapper.class);

        try {
            T result = request.get().block();
            if (Objects.nonNull(result)) {
                logger.debug("Request to {} returned 200 OK, response: {}", url, result);
                return result;
            }
        } catch (WebClientResponseException ex) {
            String responseError = getErrorsDetails(ex.getResponseBodyAsString(), service);

            switch (ex.getStatusCode().value()) {
                case 404:
                    throw new ResourceNotFoundException("Resourced resource was not found: " + responseError);
                case 403:
                    throw new UserNotAuthorizedException("Resourced resource was not found: " + responseError);
                case 401:
                    throw new UserNotAuthenticatedException("Resourced resource was not found: " + responseError);
                case 400:
                    throw new ServiceBadRequestException("Resourced resource was not found: " + responseError);
                default:
                    throw new ServiceUnavailableException("Could not complete this request due to an unexpected error", responseError);
            }
        } catch (IllegalArgumentException ex) {
            throw new ServiceBadRequestException("Could not connect to necessary service to complete this request",
                    "One or more request parameters are invalid in the request for service " + service);
        } catch (WebClientException ex) {
            if (ex.getCause() instanceof TimeoutException) {
                throw new ServiceTimeoutException("Could not complete the request due to a timeout thrown by " + service);
            } else {
                throw new ServiceUnavailableException("Could not connect to necessary service to complete this request",
                        service + " service isn't available to complete this request");
            }
        }

        return defaultValue;
    }

    private static String getErrorsDetails(String response, String service) {
        try {
            var responseTree = mapper.readTree(response);

            if (responseTree.has("error_description")) {
                return responseTree.get("error_description").asText();
            }
            if (responseTree.has("detail")) {
                return responseTree.get("detail").asText();
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); //TODO: ServiceBadRequestException("Could not retrieve error details from response throw by")
        }
        return "";
    }
}
