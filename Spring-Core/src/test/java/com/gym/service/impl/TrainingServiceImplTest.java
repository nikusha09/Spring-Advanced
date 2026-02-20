package com.gym.service.impl;

import com.gym.dao.TrainingDao;
import com.gym.model.Training;
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
class TrainingServiceImplTest {

    @Mock private TrainingDao dao;

    private TrainingServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new TrainingServiceImpl();
        service.setDao(dao);
    }

    @Test
    void createTraining_callsDaoSave() {
        Training training = new Training();
        service.createTraining(training);
        verify(dao).save(training);
    }

    @Test
    void getTraining_returnsResultFromDao() {
        Training training = new Training();
        when(dao.findById(1L)).thenReturn(Optional.of(training));

        Optional<Training> result = service.getTraining(1L);

        assertTrue(result.isPresent());
        assertEquals(training, result.get());
    }

    @Test
    void getTraining_notExists_returnsEmpty() {
        when(dao.findById(999L)).thenReturn(Optional.empty());

        Optional<Training> result = service.getTraining(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void getAllTrainings_returnsResultFromDao() {
        List<Training> trainings = List.of(new Training(), new Training());
        when(dao.findAll()).thenReturn(trainings);

        List<Training> result = service.getAllTrainings();

        assertEquals(2, result.size());
    }
}
