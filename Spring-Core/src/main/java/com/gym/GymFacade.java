package com.gym;

import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.Training;
import com.gym.model.TrainingType;
import com.gym.service.TraineeService;
import com.gym.service.TrainerService;
import com.gym.service.TrainingService;
import com.gym.service.TrainingTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class GymFacade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final TrainingTypeService trainingTypeService;

    @Autowired
    public GymFacade(TraineeService traineeService,
                     TrainerService trainerService,
                     TrainingService trainingService,
                     TrainingTypeService trainingTypeService) {
        this.traineeService      = traineeService;
        this.trainerService      = trainerService;
        this.trainingService     = trainingService;
        this.trainingTypeService = trainingTypeService;
    }

    // Trainee
    public void createTrainee(Trainee trainee) {
        log.debug("Facade: createTrainee");
        traineeService.createTrainee(trainee);
    }

    public void updateTrainee(String username, String password, Trainee trainee) {
        log.debug("Facade: updateTrainee — {}", username);
        traineeService.updateTrainee(username, password, trainee);
    }

    public void deleteTrainee(String username, String password) {
        log.debug("Facade: deleteTrainee — {}", username);
        traineeService.deleteByUsername(username, password);
    }

    public Optional<Trainee> getTrainee(String username, String password) {
        log.debug("Facade: getTrainee — {}", username);
        return traineeService.getTrainee(username, password);
    }

    public List<Trainee> getAllTrainees() {
        log.debug("Facade: getAllTrainees");
        return traineeService.getAllTrainees();
    }

    public void changeTraineePassword(String username, String oldPassword, String newPassword) {
        log.debug("Facade: changeTraineePassword — {}", username);
        traineeService.changePassword(username, oldPassword, newPassword);
    }

    public void activateDeactivateTrainee(String username, String password) {
        log.debug("Facade: activateDeactivateTrainee — {}", username);
        traineeService.activateDeactivate(username, password);
    }

    public List<Training> getTraineeTrainings(String username, String password, LocalDate fromDate, LocalDate toDate,
                                              String trainerName, String trainingType) {
        log.debug("Facade: getTraineeTrainings — {}", username);
        return traineeService.getTrainings(username, password, fromDate, toDate,
                trainerName, trainingType);
    }

    public List<Trainer> getUnassignedTrainers(String username, String password) {
        log.debug("Facade: getUnassignedTrainers — {}", username);
        return traineeService.getUnassignedTrainers(username, password);
    }

    public void updateTraineeTrainers(String username, String password, List<Trainer> trainers) {
        log.debug("Facade: updateTraineeTrainers — {}", username);
        traineeService.updateTrainers(username, password, trainers);
    }

    // Trainer
    public void createTrainer(Trainer trainer) {
        log.debug("Facade: createTrainer");
        trainerService.createTrainer(trainer);
    }

    public void updateTrainer(String username, String password, Trainer trainer) {
        log.debug("Facade: updateTrainer — {}", username);
        trainerService.updateTrainer(username, password, trainer);
    }

    public Optional<Trainer> getTrainer(String username, String password) {
        log.debug("Facade: getTrainer — {}", username);
        return trainerService.getTrainer(username, password);
    }

    public List<Trainer> getAllTrainers() {
        log.debug("Facade: getAllTrainers");
        return trainerService.getAllTrainers();
    }

    public void changeTrainerPassword(String username, String oldPassword, String newPassword) {
        log.debug("Facade: changeTrainerPassword — {}", username);
        trainerService.changePassword(username, oldPassword, newPassword);
    }

    public void activateDeactivateTrainer(String username, String password) {
        log.debug("Facade: activateDeactivateTrainer — {}", username);
        trainerService.activateDeactivate(username, password);
    }

    public List<Training> getTrainerTrainings(String username, String password,
                                              LocalDate fromDate, LocalDate toDate,
                                              String traineeName) {
        log.debug("Facade: getTrainerTrainings — {}", username);
        return trainerService.getTrainings(username, password, fromDate, toDate, traineeName);
    }

    // Training
    public void addTraining(String username, String password, Training training) {
        log.debug("Facade: addTraining");
        trainingService.addTraining(username, password, training);
    }

    public Optional<Training> getTraining(Long id) {
        log.debug("Facade: getTraining — id: {}", id);
        return trainingService.getTraining(id);
    }

    public List<Training> getAllTrainings() {
        log.debug("Facade: getAllTrainings");
        return trainingService.getAllTrainings();
    }

    // Training Type
    public Optional<TrainingType> getTrainingTypeByName(String name) {
        log.debug("Facade: getTrainingTypeByName — {}", name);
        return trainingTypeService.findByName(name);
    }

    public List<TrainingType> getAllTrainingTypes() {
        log.debug("Facade: getAllTrainingTypes");
        return trainingTypeService.findAll();
    }
}
