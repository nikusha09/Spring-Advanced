package com.gym.initializer;

import com.gym.config.AppConfig;
import com.gym.storage.InMemoryStorage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(AppConfig.class)
class StorageInitializerIntegrationTest {

    @Autowired
    private InMemoryStorage storage;

    @Test
    void traineeStorage_isNonEmptyAfterInit() {
        assertFalse(storage.getTraineeStorage().isEmpty());
    }

    @Test
    void trainerStorage_isNonEmptyAfterInit() {
        assertFalse(storage.getTrainerStorage().isEmpty());
    }

    @Test
    void trainingStorage_isNonEmptyAfterInit() {
        assertFalse(storage.getTrainingStorage().isEmpty());
    }

    @Test
    void trainingStorage_containsKnownEntityFromCsv() {
        assertTrue(storage.getTrainingStorage().containsKey(1L));
        assertEquals("MorningYoga", storage.getTrainingStorage().get(1L).getTrainingName());
    }
}
