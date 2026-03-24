package com.gym.service.impl;

import com.gym.exception.ValidationException;
import com.gym.model.*;
import com.gym.repository.TrainingRepository;
import com.gym.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private TrainingRepository repository;

    @Mock
    private AuthenticationService authService;

    private TrainingServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new TrainingServiceImpl();
        service.setTrainingRepository(repository);
        service.setAuthService(authService);
    }

    private Training validTraining() {
        User traineeUser = new User();
        traineeUser.setUsername("John.Smith");
        Trainee trainee = new Trainee();
        trainee.setUser(traineeUser);

        User trainerUser = new User();
        trainerUser.setUsername("Mike.Jones");
        Trainer trainer = new Trainer();
        trainer.setUser(trainerUser);

        Training training = new Training();
        training.setTrainingName("Yoga Session");
        training.setTrainingType(new TrainingType("Yoga"));
        training.setTrainingDate(LocalDate.of(2024, 7, 1));
        training.setTrainingDuration(60);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        return training;
    }

    @Test
    void addTraining_callsRepositorySave() {
        doNothing().when(authService).authenticate("John.Smith", "pass");
        Training training = validTraining();

        service.addTraining("John.Smith", "pass", training);

        verify(repository).save(training);
    }

    @Test
    void addTraining_missingName_throwsValidationException() {
        doNothing().when(authService).authenticate("John.Smith", "pass");
        Training training = validTraining();
        training.setTrainingName(null);

        assertThrows(ValidationException.class,
                () -> service.addTraining("John.Smith", "pass", training));
    }

    @Test
    void addTraining_missingType_throwsValidationException() {
        doNothing().when(authService).authenticate("John.Smith", "pass");
        Training training = validTraining();
        training.setTrainingType(null);

        assertThrows(ValidationException.class,
                () -> service.addTraining("John.Smith", "pass", training));
    }

    @Test
    void addTraining_missingDate_throwsValidationException() {
        doNothing().when(authService).authenticate("John.Smith", "pass");
        Training training = validTraining();
        training.setTrainingDate(null);

        assertThrows(ValidationException.class,
                () -> service.addTraining("John.Smith", "pass", training));
    }

    @Test
    void addTraining_zeroDuration_throwsValidationException() {
        doNothing().when(authService).authenticate("John.Smith", "pass");
        Training training = validTraining();
        training.setTrainingDuration(0);

        assertThrows(ValidationException.class,
                () -> service.addTraining("John.Smith", "pass", training));
    }

    @Test
    void addTraining_missingTrainee_throwsValidationException() {
        doNothing().when(authService).authenticate("John.Smith", "pass");
        Training training = validTraining();
        training.setTrainee(null);

        assertThrows(ValidationException.class,
                () -> service.addTraining("John.Smith", "pass", training));
    }

    @Test
    void addTraining_missingTrainer_throwsValidationException() {
        doNothing().when(authService).authenticate("John.Smith", "pass");
        Training training = validTraining();
        training.setTrainer(null);

        assertThrows(ValidationException.class,
                () -> service.addTraining("John.Smith", "pass", training));
    }

    @Test
    void getTraining_returnsResult() {
        Training training = validTraining();
        when(repository.findById(1L)).thenReturn(Optional.of(training));

        Optional<Training> result = service.getTraining(1L);

        assertTrue(result.isPresent());
    }

    @Test
    void getTraining_notFound_returnsEmpty() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        Optional<Training> result = service.getTraining(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void getAllTrainings_returnsAll() {
        when(repository.findAll()).thenReturn(List.of(validTraining(), validTraining()));

        List<Training> result = service.getAllTrainings();

        assertEquals(2, result.size());
    }
}
