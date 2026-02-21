package com.gym.service;

import com.gym.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerService {
    void createTrainer(Trainer trainer);
    void updateTrainer(Trainer trainer);
    Optional<Trainer> getTrainer(Long id);
    List<Trainer> getAllTrainers();
}
