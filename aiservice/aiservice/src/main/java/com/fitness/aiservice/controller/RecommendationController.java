package com.fitness.aiservice.controller;

import com.fitness.aiservice.model.Reccomendation;
import com.fitness.aiservice.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/recommendation")
public class RecommendationController {
    private RecommendationService recommendationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reccomendation>> getUserRecommendations(@PathVariable String userId){
        return ResponseEntity.ok(recommendationService.getUserRecommendation(userId));
    }

    @GetMapping("/user/{activityId}")
    public ResponseEntity<Reccomendation> getActivityRecommendations(@PathVariable String activityId){
        return ResponseEntity.ok(recommendationService.getActivityRecommendation(activityId));
    }
}
