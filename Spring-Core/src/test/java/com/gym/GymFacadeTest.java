package com.gym;

import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.Training;
import com.gym.service.TraineeService;
import com.gym.service.TrainerService;
import com.gym.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GymFacadeTest {

    @Mock private TraineeService traineeService;
    @Mock private TrainerService trainerService;
    @Mock private TrainingService trainingService;

    private GymFacade facade;

    @BeforeEach
    void setUp() {
        facade = new GymFacade(traineeService, trainerService, trainingService);
    }

    // --- Trainee ---

    @Test
    void createTrainee_delegatesToService() {
        Trainee trainee = new Trainee();
        facade.createTrainee(trainee);
        verify(traineeService).createTrainee(trainee);
    }

    @Test
    void updateTrainee_delegatesToService() {
        Trainee trainee = new Trainee();
        facade.updateTrainee(trainee);
        verify(traineeService).updateTrainee(trainee);
    }

    @Test
    void deleteTrainee_delegatesToService() {
        facade.deleteTrainee(1L);
        verify(traineeService).deleteTrainee(1L);
    }

    @Test
    void getTrainee_delegatesToService() {
        Trainee trainee = new Trainee();
        when(traineeService.getTrainee(1L)).thenReturn(Optional.of(trainee));

        Trainee result = facade.getTrainee(1L);

        assertNotNull(result);
        verify(traineeService).getTrainee(1L);
    }

    @Test
    void getAllTrainees_delegatesToService() {
        when(traineeService.getAllTrainees()).thenReturn(List.of(new Trainee()));

        List<Trainee> result = facade.getAllTrainees();

        assertEquals(1, result.size());
        verify(traineeService).getAllTrainees();
    }

    // --- Trainer ---

    @Test
    void createTrainer_delegatesToService() {
        Trainer trainer = new Trainer();
        facade.createTrainer(trainer);
        verify(trainerService).createTrainer(trainer);
    }

    @Test
    void updateTrainer_delegatesToService() {
        Trainer trainer = new Trainer();
        facade.updateTrainer(trainer);
        verify(trainerService).updateTrainer(trainer);
    }

    @Test
    void getTrainer_delegatesToService() {
        Trainer trainer = new Trainer();
        when(trainerService.getTrainer(1L)).thenReturn(Optional.of(trainer));

        Trainer result = facade.getTrainer(1L);

        assertNotNull(result);
        verify(trainerService).getTrainer(1L);
    }

    @Test
    void getAllTrainers_delegatesToService() {
        when(trainerService.getAllTrainers()).thenReturn(List.of(new Trainer()));

        List<Trainer> result = facade.getAllTrainers();

        assertEquals(1, result.size());
        verify(trainerService).getAllTrainers();
    }

    // --- Training ---

    @Test
    void createTraining_delegatesToService() {
        Training training = new Training();
        facade.createTraining(training);
        verify(trainingService).createTraining(training);
    }

    @Test
    void getTraining_delegatesToService() {
        Training training = new Training();
        when(trainingService.getTraining(1L)).thenReturn(Optional.of(training));

        Training result = facade.getTraining(1L);

        assertNotNull(result);
        verify(trainingService).getTraining(1L);
    }

    @Test
    void getAllTrainings_delegatesToService() {
        when(trainingService.getAllTrainings()).thenReturn(List.of(new Training()));

        List<Training> result = facade.getAllTrainings();

        assertEquals(1, result.size());
        verify(trainingService).getAllTrainings();
    }
}
