package com.alexspohr.user_service.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Bean
    public static SslContext sslContext() throws Exception {
        return SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
    }

    @Profile("authprovider-google")
    @Bean("google")
    public static WebClient webClient(WebClient.Builder builder, SslContext sslContext) {
        return builder.clone().baseUrl("https://oauth2.googleapis.com")
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .secure(t -> t.sslContext(sslContext))
                )).build();
    }
}
