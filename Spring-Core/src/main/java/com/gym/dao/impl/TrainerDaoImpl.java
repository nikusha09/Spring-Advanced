package com.gym.dao.impl;

import com.gym.dao.TrainerDao;
import com.gym.model.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TrainerDaoImpl implements TrainerDao {

    private final static Logger log = LoggerFactory.getLogger(TrainerDaoImpl.class);

    private Map<Long, Trainer> storage;
    // auto-incrementing ID generator
    private final static AtomicLong counter = new AtomicLong(0);

    @Autowired
    public void setStorage(Map<Long, Trainer> storage) {
        this.storage = storage;
    }

    @Override
    public void save(Trainer trainer) {
        log.debug("Saving trainer: {}", trainer);
        if (trainer.getUserID() == null) {
            trainer.setUserID(counter.incrementAndGet());
        }
        storage.put(trainer.getUserID(), trainer);
        log.debug("Assigned new ID {} to trainer", trainer.getUserID());
    }

    @Override
    public void update(Trainer trainer) {
        log.debug("Updating trainer: {}", trainer);
        if (trainer.getUserID() != null && storage.containsKey(trainer.getUserID())) {
            storage.put(trainer.getUserID(), trainer);
            log.debug("Trainer with ID {} updated successfully", trainer.getUserID());
        } else {
            log.error("Trainer with ID {} does not exist. Update failed.", trainer.getUserID());
            throw new IllegalArgumentException("Trainer with ID " + trainer.getUserID() + " does not exist.");
        }
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        log.debug("Finding trainer with ID: {}", id);
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Trainer> findAll() {
    log.debug("Finding all trainers");
        return List.copyOf(storage.values());
    }
}
