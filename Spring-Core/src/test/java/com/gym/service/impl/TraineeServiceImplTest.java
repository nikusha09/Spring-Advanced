package com.gym.service.impl;

import com.gym.exception.AuthenticationException;
import com.gym.exception.EntityNotFoundException;
import com.gym.exception.ValidationException;
import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.User;
import com.gym.repository.TraineeRepository;
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
class TraineeServiceImplTest {

    @Mock
    private TraineeRepository repository;

    @Mock
    private UsernamePasswordGenerator generator;

    @Mock
    private AuthenticationService authService;

    private TraineeServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new TraineeServiceImpl();
        service.setTraineeRepository(repository);
        service.setGenerator(generator);
        service.setAuthService(authService);
    }

    private Trainee traineeWithUser(String username, String password) {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setUsername(username);
        user.setPassword(password);
        user.setActive(true);
        Trainee trainee = new Trainee();
        trainee.setUser(user);
        return trainee;
    }

    @Test
    void createTrainee_setsUsernameAndPassword() {
        Trainee trainee = traineeWithUser(null, null);
        when(repository.findAll()).thenReturn(List.of());
        when(generator.generateUsername(any(), any(), any())).thenReturn("John.Smith");
        when(generator.generatePassword()).thenReturn("pass123abc");

        service.createTrainee(trainee);

        assertEquals("John.Smith", trainee.getUser().getUsername());
        assertEquals("pass123abc", trainee.getUser().getPassword());
        verify(repository).save(trainee);
    }

    @Test
    void createTrainee_missingFirstName_throwsValidationException() {
        Trainee trainee = new Trainee();
        User user = new User();
        user.setLastName("Smith");
        trainee.setUser(user);

        assertThrows(ValidationException.class, () -> service.createTrainee(trainee));
    }

    @Test
    void createTrainee_nullUser_throwsValidationException() {
        assertThrows(ValidationException.class, () -> service.createTrainee(new Trainee()));
    }

    @Test
    void updateTrainee_authenticatesAndUpdates() {
        Trainee trainee = traineeWithUser("John.Smith", "pass");
        trainee.setId(1L);
        doNothing().when(authService).authenticate("John.Smith", "pass");

        service.updateTrainee("John.Smith", "pass", trainee);

        verify(authService).authenticate("John.Smith", "pass");
        verify(repository).save(trainee);
    }

    @Test
    void updateTrainee_authFails_throwsAuthenticationException() {
        Trainee trainee = traineeWithUser("John.Smith", "pass");
        trainee.setId(1L);
        doThrow(new AuthenticationException("John.Smith"))
                .when(authService).authenticate("John.Smith", "wrong");

        assertThrows(AuthenticationException.class,
                () -> service.updateTrainee("John.Smith", "wrong", trainee));
    }

    @Test
    void updateTrainee_missingId_throwsValidationException() {
        Trainee trainee = traineeWithUser("John.Smith", "pass");
        doNothing().when(authService).authenticate("John.Smith", "pass");

        assertThrows(ValidationException.class,
                () -> service.updateTrainee("John.Smith", "pass", trainee));
    }

    @Test
    void deleteByUsername_authenticatesAndDeletes() {
        Trainee trainee = traineeWithUser("John.Smith", "pass");
        doNothing().when(authService).authenticate("John.Smith", "pass");
        when(repository.findByUserUsername("John.Smith")).thenReturn(Optional.of(trainee));

        service.deleteByUsername("John.Smith", "pass");

        verify(repository).delete(trainee);
    }

    @Test
    void deleteByUsername_notFound_throwsEntityNotFoundException() {
        doNothing().when(authService).authenticate("nobody", "pass");
        when(repository.findByUserUsername("nobody")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> service.deleteByUsername("nobody", "pass"));
    }

    @Test
    void getTrainee_returnsTrainee() {
        Trainee trainee = traineeWithUser("John.Smith", "pass");
        doNothing().when(authService).authenticate("John.Smith", "pass");
        when(repository.findByUserUsername("John.Smith")).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = service.getTrainee("John.Smith", "pass");

        assertTrue(result.isPresent());
    }

    @Test
    void getTrainee_notFound_returnsEmpty() {
        doNothing().when(authService).authenticate("nobody", "pass");
        when(repository.findByUserUsername("nobody")).thenReturn(Optional.empty());

        Optional<Trainee> result = service.getTrainee("nobody", "pass");

        assertFalse(result.isPresent());
    }

    @Test
    void getAllTrainees_returnsAll() {
        when(repository.findAll()).thenReturn(List.of(new Trainee(), new Trainee()));

        List<Trainee> result = service.getAllTrainees();

        assertEquals(2, result.size());
    }

    @Test
    void changePassword_updatesPassword() {
        Trainee trainee = traineeWithUser("John.Smith", "oldPass");
        doNothing().when(authService).authenticate("John.Smith", "oldPass");
        when(repository.findByUserUsername("John.Smith")).thenReturn(Optional.of(trainee));

        service.changePassword("John.Smith", "oldPass", "newPass");

        assertEquals("newPass", trainee.getUser().getPassword());
        verify(repository).save(trainee);
    }

    @Test
    void changePassword_blankNewPassword_throwsValidationException() {
        doNothing().when(authService).authenticate("John.Smith", "oldPass");

        assertThrows(ValidationException.class,
                () -> service.changePassword("John.Smith", "oldPass", ""));
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
        Trainee trainee = traineeWithUser("John.Smith", "pass");
        trainee.getUser().setActive(true);
        doNothing().when(authService).authenticate("John.Smith", "pass");
        when(repository.findByUserUsername("John.Smith")).thenReturn(Optional.of(trainee));

        service.activateDeactivate("John.Smith", "pass");

        assertFalse(trainee.getUser().isActive());
        verify(repository).save(trainee);
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
        doNothing().when(authService).authenticate("John.Smith", "pass");
        when(repository.findTrainings("John.Smith", null, null, null, null))
                .thenReturn(List.of());

        List<?> result = service.getTrainings("John.Smith", "pass",
                null, null, null, null);

        assertNotNull(result);
        verify(repository).findTrainings("John.Smith", null, null, null, null);
    }

    @Test
    void getUnassignedTrainers_delegatesToRepository() {
        doNothing().when(authService).authenticate("John.Smith", "pass");
        when(repository.getUnassignedTrainers("John.Smith")).thenReturn(List.of());

        List<Trainer> result = service.getUnassignedTrainers("John.Smith", "pass");

        assertNotNull(result);
        verify(repository).getUnassignedTrainers("John.Smith");
    }

    @Test
    void updateTrainers_updatesTraineeTrainers() {
        Trainee trainee = traineeWithUser("John.Smith", "pass");
        doNothing().when(authService).authenticate("John.Smith", "pass");
        when(repository.findByUserUsername("John.Smith")).thenReturn(Optional.of(trainee));

        service.updateTrainers("John.Smith", "pass", List.of());

        verify(repository).save(trainee);
    }
}
