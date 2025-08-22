package com.fitness.aiservice.repository;

import com.fitness.aiservice.model.Reccomendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationRepository extends MongoRepository<Reccomendation,String> {

    List<Reccomendation> findByUserId(String userId);

    Optional<Reccomendation> findByActivityId(String activityId);
}
