package com.gym;

import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.Training;
import com.gym.service.TraineeService;
import com.gym.service.TrainerService;
import com.gym.service.TrainingService;
import com.gym.service.TrainingTypeService;
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

    @Mock
    private TraineeService  traineeService;

    @Mock
    private TrainerService  trainerService;

    @Mock
    private TrainingService trainingService;

    @Mock
    private TrainingTypeService trainingTypeService;

    private GymFacade facade;

    @BeforeEach
    void setUp() {
        facade = new GymFacade(traineeService, trainerService, trainingService, trainingTypeService);
    }

    @Test
    void createTrainee_delegates() {
        Trainee trainee = new Trainee();
        facade.createTrainee(trainee);
        verify(traineeService).createTrainee(trainee);
    }

    @Test
    void updateTrainee_delegates() {
        Trainee trainee = new Trainee();
        facade.updateTrainee("user", "pass", trainee);
        verify(traineeService).updateTrainee("user", "pass", trainee);
    }

    @Test
    void deleteTrainee_delegates() {
        facade.deleteTrainee("user", "pass");
        verify(traineeService).deleteByUsername("user", "pass");
    }

    @Test
    void getTrainee_delegates() {
        when(traineeService.getTrainee("user", "pass"))
                .thenReturn(Optional.of(new Trainee()));
        Optional<Trainee> result = facade.getTrainee("user", "pass");
        assertTrue(result.isPresent());
        verify(traineeService).getTrainee("user", "pass");
    }

    @Test
    void getAllTrainees_delegates() {
        when(traineeService.getAllTrainees()).thenReturn(List.of(new Trainee()));
        assertEquals(1, facade.getAllTrainees().size());
        verify(traineeService).getAllTrainees();
    }

    @Test
    void changeTraineePassword_delegates() {
        facade.changeTraineePassword("user", "old", "new");
        verify(traineeService).changePassword("user", "old", "new");
    }

    @Test
    void activateDeactivateTrainee_delegates() {
        facade.activateDeactivateTrainee("user", "pass");
        verify(traineeService).activateDeactivate("user", "pass");
    }

    @Test
    void getTraineeTrainings_delegates() {
        when(traineeService.getTrainings("user", "pass", null, null, null, null))
                .thenReturn(List.of());
        facade.getTraineeTrainings("user", "pass", null, null, null, null);
        verify(traineeService).getTrainings("user", "pass", null, null, null, null);
    }

    @Test
    void getUnassignedTrainers_delegates() {
        when(traineeService.getUnassignedTrainers("user", "pass")).thenReturn(List.of());
        facade.getUnassignedTrainers("user", "pass");
        verify(traineeService).getUnassignedTrainers("user", "pass");
    }

    @Test
    void updateTraineeTrainers_delegates() {
        facade.updateTraineeTrainers("user", "pass", List.of());
        verify(traineeService).updateTrainers("user", "pass", List.of());
    }

    @Test
    void createTrainer_delegates() {
        Trainer trainer = new Trainer();
        facade.createTrainer(trainer);
        verify(trainerService).createTrainer(trainer);
    }

    @Test
    void updateTrainer_delegates() {
        Trainer trainer = new Trainer();
        facade.updateTrainer("user", "pass", trainer);
        verify(trainerService).updateTrainer("user", "pass", trainer);
    }

    @Test
    void getTrainer_delegates() {
        when(trainerService.getTrainer("user", "pass"))
                .thenReturn(Optional.of(new Trainer()));
        Optional<Trainer> result = facade.getTrainer("user", "pass");
        assertTrue(result.isPresent());
        verify(trainerService).getTrainer("user", "pass");
    }

    @Test
    void getAllTrainers_delegates() {
        when(trainerService.getAllTrainers()).thenReturn(List.of(new Trainer()));
        assertEquals(1, facade.getAllTrainers().size());
        verify(trainerService).getAllTrainers();
    }

    @Test
    void changeTrainerPassword_delegates() {
        facade.changeTrainerPassword("user", "old", "new");
        verify(trainerService).changePassword("user", "old", "new");
    }

    @Test
    void activateDeactivateTrainer_delegates() {
        facade.activateDeactivateTrainer("user", "pass");
        verify(trainerService).activateDeactivate("user", "pass");
    }

    @Test
    void getTrainerTrainings_delegates() {
        when(trainerService.getTrainings("user", "pass", null, null, null))
                .thenReturn(List.of());
        facade.getTrainerTrainings("user", "pass", null, null, null);
        verify(trainerService).getTrainings("user", "pass", null, null, null);
    }

    @Test
    void addTraining_delegates() {
        Training training = new Training();
        facade.addTraining("user", "pass", training);
        verify(trainingService).addTraining("user", "pass", training);
    }

    @Test
    void getTraining_delegates() {
        when(trainingService.getTraining(1L)).thenReturn(Optional.of(new Training()));
        Optional<Training> result = facade.getTraining(1L);
        assertTrue(result.isPresent());
        verify(trainingService).getTraining(1L);
    }

    @Test
    void getAllTrainings_delegates() {
        when(trainingService.getAllTrainings()).thenReturn(List.of(new Training()));
        assertEquals(1, facade.getAllTrainings().size());
        verify(trainingService).getAllTrainings();
    }
}
