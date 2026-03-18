package com.gym.service;

import com.gym.model.Trainer;
import com.gym.model.Training;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainerService {
    void createTrainer(Trainer trainer);
    void updateTrainer(String username, String password, Trainer trainer);
    Optional<Trainer> getTrainer(String username, String password);
    List<Trainer> getAllTrainers();
    void changePassword(String username, String oldPassword, String newPassword);
    void activateDeactivate(String username, String password);
    List<Training> getTrainings(String username, String password, LocalDate fromDate, LocalDate toDate, String traineeName);
}
