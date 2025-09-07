package com.fitness.aiservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Reccomendation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ActivityAiService {
    private final GeminiService geminiService;

    public String generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(prompt);
        log.info("RESPONSE FROM AI: {}", aiResponse);

        // Process and build recommendation
        Reccomendation rec = processAiActivity(activity, aiResponse);

        if (rec != null) {
            log.info("=== Final Recommendation Object ===");
            log.info("{}", rec);
            // You can persist it here if you have a repository
            // recRepository.save(rec);
        }

        return aiResponse;
    }

    private Reccomendation processAiActivity(Activity activity, String aiResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Parse AI response
            JsonNode root = mapper.readTree(aiResponse);

            // Extract recommendation
            JsonNode recommendationNode = root.path("recommendation");
            String summary = recommendationNode.path("summary").asText();
            String reasoning = recommendationNode.path("reasoning").asText();

            // Extract suggestions list
            List<String> suggestions = new ArrayList<>();
            JsonNode suggestionsNode = root.path("suggestions");
            if (suggestionsNode.isArray()) {
                suggestionsNode.forEach(node -> suggestions.add(node.asText()));
            }

            // Extract improvements list
            List<String> improvements = new ArrayList<>();
            JsonNode improvementsNode = root.path("improvements");
            if (improvementsNode.isArray()) {
                improvementsNode.forEach(node -> improvements.add(node.asText()));
            }

            // Extract safety list
            List<String> safety = new ArrayList<>();
            JsonNode safetyNode = root.path("safety");
            if (safetyNode.isArray()) {
                safetyNode.forEach(node -> safety.add(node.asText()));
            }

            // Build recommendation object
            Reccomendation rec = Reccomendation.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .recommendation(summary + " | Reasoning: " + reasoning)
                    .suggestions(suggestions)
                    .improvements(improvements)
                    .safety(safety)
                    .createdAt(LocalDateTime.now())
                    .build();

            return rec;

        } catch (Exception e) {
            log.error("Error processing AI activity response", e);
            return null;
        }
    }

    private String createPromptForActivity(Activity activity) {
        return String.format(
                "You are a personalized fitness AI assistant. " +
                        "Analyze the user’s activity data and provide actionable insights in JSON format only. " +
                        "Do not include explanations outside JSON.\n\n" +

                        "Activity details (JSON):\n" +
                        "{\n" +
                        "  \"id\": \"%s\",\n" +
                        "  \"userId\": \"%s\",\n" +
                        "  \"duration\": %d,\n" +
                        "  \"caloriesBurned\": %d,\n" +
                        "  \"startTime\": \"%s\",\n" +
                        "  \"additionalMetrics\": %s,\n" +
                        "  \"createdAt\": \"%s\",\n" +
                        "  \"updatedAt\": \"%s\"\n" +
                        "}\n\n" +

                        "Return the response ONLY as valid JSON with this nested structure:\n" +
                        "{\n" +
                        "  \"recommendation\": { \"summary\": \"string\", \"reasoning\": \"string\" },\n" +
                        "  \"suggestions\": [\"string\"],\n" +
                        "  \"improvements\": [\"string\"],\n" +
                        "  \"safety\": [\"string\"],\n" +
                        "  \"suggestedNextActivity\": { \"activityType\": \"string\", \"durationMinutes\": number, \"intensity\": \"Low | Moderate | High\" },\n" +
                        "  \"estimatedCaloriesTarget\": { \"value\": number, \"note\": \"string\" },\n" +
                        "  \"intensityLevel\": { \"level\": \"Low | Moderate | High\", \"explanation\": \"string\" },\n" +
                        "  \"hydrationReminder\": { \"message\": \"string\", \"amountLiters\": number, \"frequency\": \"string\" },\n" +
                        "  \"recoveryTip\": { \"tip\": \"string\", \"targetMuscles\": \"string\", \"durationMinutes\": number },\n" +
                        "  \"motivationQuote\": { \"quote\": \"string\", \"author\": \"string\" },\n" +
                        "  \"potentialHealthRisk\": { \"riskDescription\": \"string\", \"severity\": \"Low | Moderate | High | None\" }\n" +
                        "}\n\n" +
                        "Ensure the output is strictly valid JSON and provide meaningful nested details rather than simple strings.",
                activity.getId(),
                activity.getUserId(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getStartTime(),
                activity.getAdditionalMetrics(),
                activity.getCreatedAt(),
                activity.getUpdatedAt()
        );
    }
}
