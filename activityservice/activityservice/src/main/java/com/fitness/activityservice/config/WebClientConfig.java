package com.fitness.activityservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .filter(logRequest())
                .filter(logResponse())
                .filter(errorHandler());
    }

    @Bean
    public WebClient userServiceWebClient(WebClient.Builder webClientBuilder) {
        try {
            WebClient webClient = webClientBuilder
                    .baseUrl("http://USER-SERVICE")
                    .build();

            log.info("Successfully created WebClient for USER-SERVICE");
            return webClient;

        } catch (Exception e) {
            log.error("Failed to create WebClient for USER-SERVICE. Reason: {}", e.getMessage());
            log.debug("Stack trace:", e);
            throw new IllegalStateException("Failed to initialize WebClient for USER-SERVICE", e);
        }
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            log.debug("Headers: {}", clientRequest.headers());
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("Response status: {}", clientResponse.statusCode());
            return Mono.just(clientResponse);
        });
    }

    private ExchangeFilterFunction errorHandler() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().is5xxServerError()) {
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> {
                            log.error("Server error: {} - {}", clientResponse.statusCode(), errorBody);
                            return Mono.error(new RuntimeException("Service unavailable: " + errorBody));
                        });
            } else if (clientResponse.statusCode().is4xxClientError()) {
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> {
                            log.warn("Client error: {} - {}", clientResponse.statusCode(), errorBody);
                            return Mono.error(new RuntimeException("Invalid request: " + errorBody));
                        });
            }
            return Mono.just(clientResponse);
        });
    }
}