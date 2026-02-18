package com.gym.service.impl;

import com.gym.dao.TraineeDao;
import com.gym.model.Trainee;
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

    @Mock private TraineeDao dao;
    @Mock private UsernamePasswordGenerator generator;

    private TraineeServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new TraineeServiceImpl();
        service.setDao(dao);
        service.setGenerator(generator);
    }

    @Test
    void createTrainee_setsUsernameAndPassword() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Smith");

        when(dao.findAll()).thenReturn(List.of());
        when(generator.generateUsername("John", "Smith", List.of())).thenReturn("John.Smith");
        when(generator.generatePassword()).thenReturn("pass123abc");

        service.createTrainee(trainee);

        assertEquals("John.Smith", trainee.getUsername());
        assertEquals("pass123abc", trainee.getPassword());
    }

    @Test
    void createTrainee_callsDaoSave() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Smith");

        when(dao.findAll()).thenReturn(List.of());
        when(generator.generateUsername(any(), any(), any())).thenReturn("John.Smith");
        when(generator.generatePassword()).thenReturn("pass123abc");

        service.createTrainee(trainee);

        verify(dao).save(trainee);
    }

    @Test
    void updateTrainee_callsDaoUpdate() {
        Trainee trainee = new Trainee();
        service.updateTrainee(trainee);
        verify(dao).update(trainee);
    }

    @Test
    void deleteTrainee_callsDaoDelete() {
        service.deleteTrainee(1L);
        verify(dao).delete(1L);
    }

    @Test
    void getTrainee_returnsResultFromDao() {
        Trainee trainee = new Trainee();
        when(dao.findById(1L)).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = service.getTrainee(1L);

        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }

    @Test
    void getTrainee_notExists_returnsEmpty() {
        when(dao.findById(999L)).thenReturn(Optional.empty());

        Optional<Trainee> result = service.getTrainee(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void getAllTrainees_returnsResultFromDao() {
        List<Trainee> trainees = List.of(new Trainee(), new Trainee());
        when(dao.findAll()).thenReturn(trainees);

        List<Trainee> result = service.getAllTrainees();

        assertEquals(2, result.size());
    }
}