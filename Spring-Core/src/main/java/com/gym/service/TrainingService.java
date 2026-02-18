package com.gym.service;

import com.gym.model.Training;

import java.util.List;
import java.util.Optional;

public interface TrainingService {
    void createTraining(Training training);
    Optional<Training> getTraining(Long id);
    List<Training> getAllTrainings();
}
