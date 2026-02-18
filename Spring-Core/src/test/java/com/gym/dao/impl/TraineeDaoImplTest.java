package com.gym.dao.impl;

import com.gym.model.Trainee;
import com.gym.storage.InMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeDaoImplTest {

    private TraineeDaoImpl dao;
    private HashMap<Long, Trainee> map;

    @BeforeEach
    void setUp() {
        map = new HashMap<>();
        InMemoryStorage storage = mock(InMemoryStorage.class);
        when(storage.getTraineeStorage()).thenReturn(map);

        dao = new TraineeDaoImpl();
        dao.setStorage(storage);
    }

    @Test
    void save_assignsIdAndStoresEntity() {
        Trainee trainee = new Trainee();
        dao.save(trainee);

        assertNotNull(trainee.getUserID());
        assertTrue(map.containsKey(trainee.getUserID()));
    }

    @Test
    void save_multipleEntities_incrementsId() {
        Trainee t1 = new Trainee();
        Trainee t2 = new Trainee();
        dao.save(t1);
        dao.save(t2);

        assertNotEquals(t1.getUserID(), t2.getUserID());
        assertEquals(2, map.size());
    }

    @Test
    void update_overwritesExistingEntry() {
        Trainee trainee = new Trainee();
        dao.save(trainee);
        trainee.setAddress("Updated Address");
        dao.update(trainee);

        assertEquals("Updated Address", map.get(trainee.getUserID()).getAddress());
    }

    @Test
    void delete_removesEntityFromMap() {
        Trainee trainee = new Trainee();
        dao.save(trainee);
        dao.delete(trainee.getUserID());

        assertFalse(map.containsKey(trainee.getUserID()));
    }

    @Test
    void findById_returnsCorrectEntity() {
        Trainee trainee = new Trainee();
        dao.save(trainee);

        Optional<Trainee> result = dao.findById(trainee.getUserID());
        assertTrue(result.isPresent());
        assertEquals(trainee.getUserID(), result.get().getUserID());
    }

    @Test
    void findById_notExists_returnsEmpty() {
        Optional<Trainee> result = dao.findById(999L);
        assertFalse(result.isPresent());
    }

    @Test
    void findAll_returnsAllEntities() {
        dao.save(new Trainee());
        dao.save(new Trainee());

        List<Trainee> result = dao.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void findAll_emptyStorage_returnsEmptyList() {
        List<Trainee> result = dao.findAll();
        assertTrue(result.isEmpty());
    }
}