package com.gym.dao.impl;

import com.gym.model.Training;
import com.gym.storage.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrainingDaoImplTest {

    private TrainingDaoImpl dao;
    private HashMap<Long, Training> map;

    @BeforeEach
    void setUp() {
        map = new HashMap<>();
        InMemoryStorage storage = mock(InMemoryStorage.class);
        when(storage.getTrainingStorage()).thenReturn(map);

        dao = new TrainingDaoImpl();
        dao.setStorage(storage);
    }

    @Test
    void save_assignsIdAndStoresEntity() {
        Training training = new Training();
        dao.save(training);

        assertNotNull(training.getId());
        assertTrue(map.containsKey(training.getId()));
    }

    @Test
    void findById_returnsCorrectEntity() {
        Training training = new Training();
        dao.save(training);

        Optional<Training> result = dao.findById(training.getId());
        assertTrue(result.isPresent());
        assertEquals(training.getId(), result.get().getId());
    }

    @Test
    void findById_notExists_returnsEmpty() {
        Optional<Training> result = dao.findById(999L);
        assertFalse(result.isPresent());
    }

    @Test
    void findAll_returnsAllEntities() {
        dao.save(new Training());
        dao.save(new Training());

        List<Training> result = dao.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void findAll_emptyStorage_returnsEmptyList() {
        List<Training> result = dao.findAll();
        assertTrue(result.isEmpty());
    }
}
