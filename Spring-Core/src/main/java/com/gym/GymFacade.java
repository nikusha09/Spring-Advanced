package com.gym;

import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.Training;
import com.gym.service.TraineeService;
import com.gym.service.TrainerService;
import com.gym.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GymFacade {

    private static final Logger log = LoggerFactory.getLogger(GymFacade.class);

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    @Autowired
    public GymFacade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    // Trainee operations
    public void createTrainee(Trainee trainee) {
        log.debug("Creating trainee through facade: {}", trainee);
        traineeService.createTrainee(trainee);
        log.debug("Trainee created successfully through facade: {}", trainee);
    }

    public void updateTrainee(Trainee trainee) {
        log.debug("Updating trainee through facade: {}", trainee);
        traineeService.updateTrainee(trainee);
        log.debug("Trainee updated successfully through facade: {}", trainee);
    }

    public void deleteTrainee(Long id) {
        log.debug("Deleting trainee with ID {} through facade", id);
        traineeService.deleteTrainee(id);
        log.debug("Trainee with ID {} deleted successfully through facade", id);
    }

    public Trainee getTrainee(Long id) {
        log.debug("Retrieving trainee with ID {} through facade", id);
        return traineeService.getTrainee(id).orElse(null);
    }

    public List<Trainee> getAllTrainees() {
        log.debug("Retrieving all trainees through facade");
        return traineeService.getAllTrainees();
    }

    // Trainer operations
    public void createTrainer(Trainer trainer) {
        log.debug("Creating trainer through facade: {}", trainer);
        trainerService.createTrainer(trainer);
        log.debug("Trainer created successfully through facade: {}", trainer);
    }

    public void updateTrainer(Trainer trainer) {
        log.debug("Updating trainer through facade: {}", trainer);
        trainerService.updateTrainer(trainer);
        log.debug("Trainer updated successfully through facade: {}", trainer);
    }

    public Trainer getTrainer(Long id) {
        log.debug("Retrieving trainer with ID {} through facade", id);
        return trainerService.getTrainer(id).orElse(null);
    }

    public List<Trainer> getAllTrainers() {
        log.debug("Retrieving all trainers through facade");
        return trainerService.getAllTrainers();
    }

    // Training operations
    public void createTraining(Training training) {
        log.debug("Creating training through facade: {}", training);
        trainingService.createTraining(training);
        log.debug("Training created successfully through facade: {}", training);
    }

    public Training getTraining(Long id) {
        log.debug("Retrieving training with ID {} through facade", id);
        return trainingService.getTraining(id).orElse(null);
    }

    public List<Training> getAllTrainings() {
        log.debug("Retrieving all trainings through facade");
        return trainingService.getAllTrainings();
    }
}
