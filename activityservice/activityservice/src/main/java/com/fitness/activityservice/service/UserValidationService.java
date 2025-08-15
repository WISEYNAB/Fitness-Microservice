package com.fitness.activityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
@Service
@Slf4j
@RequiredArgsConstructor
public class UserValidationService {
    private final WebClient userServiceWebClient;

    public boolean validateUser(String userId) {
        log.info("Validating user with ID: {}", userId);
        try {
            Boolean isValid = userServiceWebClient.get()
                    .uri("/api/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            if (isValid == null) {
                log.warn("Validation returned null for user {}", userId);
                return false;
            }
            return isValid;
        } catch (WebClientResponseException.NotFound e) {
            log.error("User not found: {}", userId);
            throw new RuntimeException("User Not Found: " + userId);
        } catch (WebClientResponseException e) {
            log.error("Validation error for user {}: {}", userId, e.getMessage());
            throw new RuntimeException("Validation error for user: " + userId);
        } catch (Exception e) {
            log.error("Unexpected validation error", e);
            throw new RuntimeException("Service unavailable - please try again later");
        }
    }
}