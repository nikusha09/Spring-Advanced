package com.gym.storage;

import com.gym.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InMemoryStorage {

    private Map<Long, Trainee>  traineeStorage;
    private Map<Long, Trainer>  trainerStorage;
    private Map<Long, Training> trainingStorage;

    @Autowired
    public void setTraineeStorage(@Qualifier("traineeStorage") Map<Long, Trainee> traineeStorage) {
        this.traineeStorage = traineeStorage;
    }

    @Autowired
    public void setTrainerStorage(@Qualifier("trainerStorage") Map<Long, Trainer> trainerStorage) {
        this.trainerStorage = trainerStorage;
    }

    @Autowired
    public void setTrainingStorage(@Qualifier("trainingStorage") Map<Long, Training> trainingStorage) {
        this.trainingStorage = trainingStorage;
    }

    public Map<Long, Trainee>  getTraineeStorage()  { return traineeStorage; }
    public Map<Long, Trainer>  getTrainerStorage()  { return trainerStorage; }
    public Map<Long, Training> getTrainingStorage() { return trainingStorage; }
}