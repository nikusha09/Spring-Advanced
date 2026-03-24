package com.gym.service.impl;

import com.gym.exception.AuthenticationException;
import com.gym.exception.EntityNotFoundException;
import com.gym.exception.ValidationException;
import com.gym.model.Trainer;
import com.gym.model.TrainingType;
import com.gym.model.User;
import com.gym.repository.TrainerRepository;
import com.gym.service.AuthenticationService;
import com.gym.util.UsernamePasswordGenerator;
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
class TrainerServiceImplTest {

    @Mock
    private TrainerRepository       repository;

    @Mock
    private UsernamePasswordGenerator generator;

    @Mock
    private AuthenticationService   authService;

    private TrainerServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new TrainerServiceImpl();
        service.setTrainerRepository(repository);
        service.setGenerator(generator);
        service.setAuthService(authService);
    }

    private Trainer trainerWithUser(String username, String password) {
        User user = new User();
        user.setFirstName("Michael");
        user.setLastName("Johnson");
        user.setUsername(username);
        user.setPassword(password);
        user.setActive(true);
        Trainer trainer = new Trainer();
        trainer.setUser(user);
        trainer.setSpecialization(new TrainingType("Yoga"));
        return trainer;
    }

    @Test
    void createTrainer_setsUsernameAndPassword() {
        Trainer trainer = trainerWithUser(null, null);
        when(repository.findAll()).thenReturn(List.of());
        when(generator.generateUsername(any(), any(), any())).thenReturn("Michael.Johnson");
        when(generator.generatePassword()).thenReturn("pass123abc");

        service.createTrainer(trainer);

        assertEquals("Michael.Johnson", trainer.getUser().getUsername());
        assertEquals("pass123abc", trainer.getUser().getPassword());
        verify(repository).save(trainer);
    }

    @Test
    void createTrainer_nullUser_throwsValidationException() {
        assertThrows(ValidationException.class, () -> service.createTrainer(new Trainer()));
    }

    @Test
    void createTrainer_missingSpecialization_throwsValidationException() {
        User user = new User();
        user.setFirstName("Michael");
        user.setLastName("Johnson");
        Trainer trainer = new Trainer();
        trainer.setUser(user);

        assertThrows(ValidationException.class, () -> service.createTrainer(trainer));
    }

    @Test
    void createTrainer_missingFirstName_throwsValidationException() {
        User user = new User();
        user.setLastName("Johnson");
        Trainer trainer = new Trainer();
        trainer.setUser(user);
        trainer.setSpecialization(new TrainingType("Yoga"));

        assertThrows(ValidationException.class, () -> service.createTrainer(trainer));
    }

    @Test
    void updateTrainer_authenticatesAndUpdates() {
        Trainer trainer = trainerWithUser("Michael.Johnson", "pass");
        trainer.setId(1L);
        doNothing().when(authService).authenticate("Michael.Johnson", "pass");

        service.updateTrainer("Michael.Johnson", "pass", trainer);

        verify(authService).authenticate("Michael.Johnson", "pass");
        verify(repository).save(trainer);
    }

    @Test
    void updateTrainer_authFails_throwsAuthenticationException() {
        Trainer trainer = trainerWithUser("Michael.Johnson", "pass");
        trainer.setId(1L);
        doThrow(new AuthenticationException("Michael.Johnson"))
                .when(authService).authenticate("Michael.Johnson", "wrong");

        assertThrows(AuthenticationException.class,
                () -> service.updateTrainer("Michael.Johnson", "wrong", trainer));
    }

    @Test
    void updateTrainer_missingId_throwsValidationException() {
        Trainer trainer = trainerWithUser("Michael.Johnson", "pass");
        doNothing().when(authService).authenticate("Michael.Johnson", "pass");

        assertThrows(ValidationException.class,
                () -> service.updateTrainer("Michael.Johnson", "pass", trainer));
    }

    @Test
    void getTrainer_returnsTrainer() {
        Trainer trainer = trainerWithUser("Michael.Johnson", "pass");
        doNothing().when(authService).authenticate("Michael.Johnson", "pass");
        when(repository.findByUserUsername("Michael.Johnson")).thenReturn(Optional.of(trainer));

        Optional<Trainer> result = service.getTrainer("Michael.Johnson", "pass");

        assertTrue(result.isPresent());
    }

    @Test
    void getTrainer_notFound_returnsEmpty() {
        doNothing().when(authService).authenticate("nobody", "pass");
        when(repository.findByUserUsername("nobody")).thenReturn(Optional.empty());

        Optional<Trainer> result = service.getTrainer("nobody", "pass");

        assertFalse(result.isPresent());
    }

    @Test
    void getAllTrainers_returnsAll() {
        when(repository.findAll()).thenReturn(List.of(new Trainer(), new Trainer()));

        List<Trainer> result = service.getAllTrainers();

        assertEquals(2, result.size());
    }

    @Test
    void changePassword_updatesPassword() {
        Trainer trainer = trainerWithUser("Michael.Johnson", "oldPass");
        doNothing().when(authService).authenticate("Michael.Johnson", "oldPass");
        when(repository.findByUserUsername("Michael.Johnson")).thenReturn(Optional.of(trainer));

        service.changePassword("Michael.Johnson", "oldPass", "newPass");

        assertEquals("newPass", trainer.getUser().getPassword());
        verify(repository).save(trainer);
    }

    @Test
    void changePassword_blankNewPassword_throwsValidationException() {
        doNothing().when(authService).authenticate("Michael.Johnson", "oldPass");

        assertThrows(ValidationException.class,
                () -> service.changePassword("Michael.Johnson", "oldPass", ""));
    }

    @Test
    void changePassword_notFound_throwsEntityNotFoundException() {
        doNothing().when(authService).authenticate("nobody", "pass");
        when(repository.findByUserUsername("nobody")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> service.changePassword("nobody", "pass", "newPass"));
    }

    @Test
    void activateDeactivate_togglesStatus() {
        Trainer trainer = trainerWithUser("Michael.Johnson", "pass");
        trainer.getUser().setActive(true);
        doNothing().when(authService).authenticate("Michael.Johnson", "pass");
        when(repository.findByUserUsername("Michael.Johnson")).thenReturn(Optional.of(trainer));

        service.activateDeactivate("Michael.Johnson", "pass");

        assertFalse(trainer.getUser().isActive());
        verify(repository).save(trainer);
    }

    @Test
    void activateDeactivate_notFound_throwsEntityNotFoundException() {
        doNothing().when(authService).authenticate("nobody", "pass");
        when(repository.findByUserUsername("nobody")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> service.activateDeactivate("nobody", "pass"));
    }

    @Test
    void getTrainings_delegatesToRepository() {
        doNothing().when(authService).authenticate("Michael.Johnson", "pass");
        when(repository.findTrainings("Michael.Johnson", null, null, null))
                .thenReturn(List.of());

        List<?> result = service.getTrainings("Michael.Johnson", "pass",
                null, null, null);

        assertNotNull(result);
        verify(repository).findTrainings("Michael.Johnson", null, null, null);
    }
}
