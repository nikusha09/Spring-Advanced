package com.gym.controllers;

import com.gym.dto.request.AddTrainingRequest;
import com.gym.mapper.TrainingMapper;
import com.gym.model.Training;
import com.gym.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainings")
@Tag(name = "Training", description = "Training management endpoints")
@Slf4j
public class TrainingController {

    private TrainingService trainingService;
    private TrainingMapper trainingMapper;

    @Autowired
    public void setTrainingService(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Autowired
    public void setTrainingMapper(TrainingMapper trainingMapper) {
        this.trainingMapper = trainingMapper;
    }

    @PostMapping
    @Operation(summary = "Add training", description = "Create a new training session")
    @ApiResponse(responseCode = "200", description = "Training added successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "401", description = "Authentication failed")
    @ApiResponse(responseCode = "404", description = "Trainee or trainer not found")
    public ResponseEntity<Void> addTraining(
            @Valid @RequestBody AddTrainingRequest request) {
        log.info("Add training request | traineeUsername={} trainerUsername={} name={}",
                request.getTraineeUsername(),
                request.getTrainerUsername(),
                request.getTrainingName());

        Training training = trainingMapper.toTraining(request);

        trainingService.addTraining(training);

        log.info("Training added | name={}", request.getTrainingName());
        return ResponseEntity.ok().build();
    }
}
