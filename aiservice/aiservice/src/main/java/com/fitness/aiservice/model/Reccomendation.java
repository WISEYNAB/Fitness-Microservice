package com.fitness.aiservice.model;

import jdk.jfr.DataAmount;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
@Document(collection = "fitnessreccomendations")
@Data
@Builder
public class Reccomendation {
    @Id
    private String id;
    private String activityId;
    private String userId;
    private String activityType;
    private String recommendation;
    private List<String> suggestions;
    private List<String> improvements;
    private List<String> safety;
    private LocalDateTime createdAt;

}
