package com.gym.service;

import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.Training;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TraineeService {
    void createTrainee(Trainee trainee);
    void updateTrainee(String username, String password, Trainee trainee);
    void deleteByUsername(String username, String password);
    Optional<Trainee> getTrainee(String username, String password);
    List<Trainee> getAllTrainees();
    void changePassword(String username, String oldPassword, String newPassword);
    void activateDeactivate(String username, String password);
    List<Training> getTrainings(String username, String password, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType);
    List<Trainer> getUnassignedTrainers(String username, String password);
    void updateTrainers(String username, String password, List<Trainer> trainers);
}
