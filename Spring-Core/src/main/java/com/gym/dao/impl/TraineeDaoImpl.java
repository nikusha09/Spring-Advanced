package com.gym.dao.impl;

import com.gym.dao.TraineeDao;
import com.gym.model.Trainee;
import com.gym.storage.InMemoryStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
public class TraineeDaoImpl implements TraineeDao {

    private Map<Long, Trainee> storage;
    // autoincrement ID generator
    private final static AtomicLong counter = new AtomicLong(0);

    @Autowired
    public void setStorage(InMemoryStorage storage) {
        this.storage = storage.getTraineeStorage();
    }

    @Override
    public void save(Trainee trainee) {
        log.debug("Saving trainee: {}", trainee);
        if (trainee.getUserID() == null) {
            trainee.setUserID(counter.incrementAndGet());
        }
        storage.put(trainee.getUserID(), trainee);
        log.debug("Trainee saved with ID: {}", trainee.getUserID());
    }

    @Override
    public void update(Trainee trainee) {
        log.debug("Updating trainee: {}", trainee);
        if (trainee.getUserID() != null && storage.containsKey(trainee.getUserID())) {
            storage.put(trainee.getUserID(), trainee);
            log.debug("Trainee updated with ID: {}", trainee.getUserID());
        } else {
            log.error("Trainee with ID {} does not exist. Update failed.", trainee.getUserID());
            throw new IllegalArgumentException("Trainee with ID " + trainee.getUserID() + " does not exist.");
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("Deleting trainee with ID: {}", id);
        storage.remove(id);
        log.debug("Trainee with ID {} deleted.", id);
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        log.debug("Finding trainee with ID: {}", id);
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Trainee> findAll() {
        log.debug("Finding all trainees");
        return List.copyOf(storage.values());
    }
}
