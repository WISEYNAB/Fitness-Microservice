package com.fitness.activityservice.controller;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/activities/")
public class ActivityController {
    private final WebClient webClient;

    private final ActivityService activityService;
    @PostMapping
    public ResponseEntity<ActivityResponse>  trackActivity(@RequestBody ActivityRequest request){
        return ResponseEntity.ok(activityService.trackActivity(request));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivity(@RequestHeader("x-user-id") String userId){
        return ResponseEntity.ok(activityService.getUserActivity(userId));
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> getActivity(@PathVariable String activityId){
        return ResponseEntity.ok(activityService.getActivity(activityId));
    }

    // Temporary test endpoint in ActivityController
    @GetMapping("/test-connection")
    public Mono<String> testConnection() {
        return webClient.get()
                .uri("http://USER-SERVICE/api/users/health")
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> "Connection successful: " + response)
                .onErrorResume(e -> Mono.just("Connection failed: " + e.getMessage()));
    }

}
