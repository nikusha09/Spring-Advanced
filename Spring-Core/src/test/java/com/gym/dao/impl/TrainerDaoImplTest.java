package com.gym.dao.impl;

import com.gym.model.Trainer;
import com.gym.storage.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrainerDaoImplTest {

    private TrainerDaoImpl dao;
    private HashMap<Long, Trainer> map;

    @BeforeEach
    void setUp() {
        map = new HashMap<>();
        InMemoryStorage storage = mock(InMemoryStorage.class);
        when(storage.getTrainerStorage()).thenReturn(map);

        dao = new TrainerDaoImpl();
        dao.setStorage(storage.getTrainerStorage());
    }

    @Test
    void save_assignsIdAndStoresEntity() {
        Trainer trainer = new Trainer();
        dao.save(trainer);

        assertNotNull(trainer.getUserID());
        assertTrue(map.containsKey(trainer.getUserID()));
    }

    @Test
    void update_overwritesExistingEntry() {
        Trainer trainer = new Trainer();
        dao.save(trainer);
        trainer.setSpecialization("Pilates");
        dao.update(trainer);

        assertEquals("Pilates", map.get(trainer.getUserID()).getSpecialization());
    }

    @Test
    void findById_returnsCorrectEntity() {
        Trainer trainer = new Trainer();
        dao.save(trainer);

        Optional<Trainer> result = dao.findById(trainer.getUserID());
        assertTrue(result.isPresent());
        assertEquals(trainer.getUserID(), result.get().getUserID());
    }

    @Test
    void findById_notExists_returnsEmpty() {
        Optional<Trainer> result = dao.findById(999L);
        assertFalse(result.isPresent());
    }

    @Test
    void findAll_returnsAllEntities() {
        dao.save(new Trainer());
        dao.save(new Trainer());

        List<Trainer> result = dao.findAll();
        assertEquals(2, result.size());
    }
}
