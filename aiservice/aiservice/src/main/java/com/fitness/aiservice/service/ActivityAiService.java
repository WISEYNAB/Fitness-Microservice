package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ActivityAiService {
    private GeminiService geminiService;
    public String generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(prompt);
        log.info("RESPONSE FROM AI: {}", aiResponse);
        return aiResponse;
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
