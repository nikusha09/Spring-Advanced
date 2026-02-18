package com.gym.service;

import com.gym.model.Trainee;

import java.util.List;
import java.util.Optional;

public interface TraineeService {
    void createTrainee(Trainee trainee);
    void updateTrainee(Trainee trainee);
    void deleteTrainee(Long id);
    Optional<Trainee> getTrainee(Long id);
    List<Trainee> getAllTrainees();
}
