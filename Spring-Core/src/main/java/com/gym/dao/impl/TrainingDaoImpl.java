package com.gym.dao.impl;

import com.gym.dao.TrainingDao;
import com.gym.model.Training;
import com.gym.storage.InMemoryStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TrainingDaoImpl implements TrainingDao {

    private static final Logger log = LoggerFactory.getLogger(TrainingDaoImpl.class);

    private final AtomicLong counter = new AtomicLong(0);
    private Map<Long, Training> storage;

    @Autowired
    public void setStorage(InMemoryStorage inMemoryStorage) {
        this.storage = inMemoryStorage.getTrainingStorage();
    }

    @Override
    public void save(Training training) {
        log.debug("Saving training: {}", training);
        if (training.getId() == null) {
            training.setId(counter.incrementAndGet());
        }
        storage.put(training.getId(), training);
        log.debug("Training saved with ID: {}", training.getId());
    }

    @Override
    public Optional<Training> findById(Long id) {
        log.debug("Finding training with ID: {}", id);
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Training> findAll() {
        log.debug("Finding all trainings");
        return List.copyOf(storage.values());
    }
}
