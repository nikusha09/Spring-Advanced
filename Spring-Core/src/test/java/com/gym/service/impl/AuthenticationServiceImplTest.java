package com.gym.service.impl;

import com.gym.exception.AuthenticationException;
import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.User;
import com.gym.repository.TraineeRepository;
import com.gym.repository.TrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainerRepository trainerRepository;

    private AuthenticationServiceImpl authService;

    @BeforeEach
    void setUp() {
        authService = new AuthenticationServiceImpl();
        authService.setTraineeRepository(traineeRepository);
        authService.setTrainerRepository(trainerRepository);
    }

    private Trainee traineeWithPassword(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Trainee trainee = new Trainee();
        trainee.setUser(user);
        return trainee;
    }

    private Trainer trainerWithPassword(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Trainer trainer = new Trainer();
        trainer.setUser(user);
        return trainer;
    }

    @Test
    void authenticate_trainee_correctPassword_doesNotThrow() {
        when(traineeRepository.findByUserUsername("john"))
                .thenReturn(Optional.of(traineeWithPassword("john", "pass123")));

        assertDoesNotThrow(() -> authService.authenticate("john", "pass123"));
    }

    @Test
    void authenticate_trainee_wrongPassword_throwsAuthenticationException() {
        when(traineeRepository.findByUserUsername("john"))
                .thenReturn(Optional.of(traineeWithPassword("john", "pass123")));

        assertThrows(AuthenticationException.class,
                () -> authService.authenticate("john", "wrongpass"));
    }

    @Test
    void authenticate_trainer_correctPassword_doesNotThrow() {
        when(traineeRepository.findByUserUsername("mike")).thenReturn(Optional.empty());
        when(trainerRepository.findByUserUsername("mike"))
                .thenReturn(Optional.of(trainerWithPassword("mike", "trainerpass")));

        assertDoesNotThrow(() -> authService.authenticate("mike", "trainerpass"));
    }

    @Test
    void authenticate_trainer_wrongPassword_throwsAuthenticationException() {
        when(traineeRepository.findByUserUsername("mike")).thenReturn(Optional.empty());
        when(trainerRepository.findByUserUsername("mike"))
                .thenReturn(Optional.of(trainerWithPassword("mike", "trainerpass")));

        assertThrows(AuthenticationException.class,
                () -> authService.authenticate("mike", "wrongpass"));
    }

    @Test
    void authenticate_userNotFound_throwsAuthenticationException() {
        when(traineeRepository.findByUserUsername("unknown")).thenReturn(Optional.empty());
        when(trainerRepository.findByUserUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(AuthenticationException.class,
                () -> authService.authenticate("unknown", "anypass"));
    }
}
