package com.gym.service.impl;

import com.gym.dao.TrainerDao;
import com.gym.model.Trainer;
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

    @Mock private TrainerDao dao;
    @Mock private UsernamePasswordGenerator generator;

    private TrainerServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new TrainerServiceImpl();
        service.setDao(dao);
        service.setGenerator(generator);
    }

    @Test
    void createTrainer_setsUsernameAndPassword() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Michael");
        trainer.setLastName("Johnson");

        when(dao.findAll()).thenReturn(List.of());
        when(generator.generateUsername("Michael", "Johnson", List.of())).thenReturn("Michael.Johnson");
        when(generator.generatePassword()).thenReturn("pass123abc");

        service.createTrainer(trainer);

        assertEquals("Michael.Johnson", trainer.getUsername());
        assertEquals("pass123abc", trainer.getPassword());
    }

    @Test
    void createTrainer_callsDaoSave() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Michael");
        trainer.setLastName("Johnson");

        when(dao.findAll()).thenReturn(List.of());
        when(generator.generateUsername(any(), any(), any())).thenReturn("Michael.Johnson");
        when(generator.generatePassword()).thenReturn("pass123abc");

        service.createTrainer(trainer);

        verify(dao).save(trainer);
    }

    @Test
    void updateTrainer_callsDaoUpdate() {
        Trainer trainer = new Trainer();
        service.updateTrainer(trainer);
        verify(dao).update(trainer);
    }

    @Test
    void getTrainer_returnsResultFromDao() {
        Trainer trainer = new Trainer();
        when(dao.findById(1L)).thenReturn(Optional.of(trainer));

        Optional<Trainer> result = service.getTrainer(1L);

        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
    }

    @Test
    void getTrainer_notExists_returnsEmpty() {
        when(dao.findById(999L)).thenReturn(Optional.empty());

        Optional<Trainer> result = service.getTrainer(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void getAllTrainers_returnsResultFromDao() {
        List<Trainer> trainers = List.of(new Trainer(), new Trainer());
        when(dao.findAll()).thenReturn(trainers);

        List<Trainer> result = service.getAllTrainers();

        assertEquals(2, result.size());
    }
}