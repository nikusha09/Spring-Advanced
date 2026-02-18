package com.gym.service.impl;

import com.gym.dao.TraineeDao;
import com.gym.model.Trainee;
import com.gym.service.TraineeService;
import com.gym.util.UsernamePasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {

    private static final Logger log = LoggerFactory.getLogger(TraineeServiceImpl.class);

    private TraineeDao dao;
    private UsernamePasswordGenerator generator;

    @Autowired
    public void setDao(TraineeDao dao) {
        this.dao = dao;
    }

    @Autowired
    public void setGenerator(UsernamePasswordGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void createTrainee(Trainee trainee) {
        log.debug("Creating trainee: {}", trainee);
        trainee.setUsername(generator.generateUsername(trainee.getFirstName(), trainee.getLastName(), dao.findAll()));
        trainee.setPassword(generator.generatePassword());
        dao.save(trainee);
        log.debug("Trainee created with username: {}", trainee.getUsername());
    }

    @Override
    public void updateTrainee(Trainee trainee) {
        log.debug("Updating trainee: {}", trainee);
        dao.update(trainee);
        log.debug("Trainee with ID {} updated successfully", trainee.getUserID());
    }

    @Override
    public void deleteTrainee(Long id) {
        log.debug("Deleting trainee with ID: {}", id);
        dao.delete(id);
        log.debug("Trainee with ID {} deleted successfully", id);
    }

    @Override
    public Optional<Trainee> getTrainee(Long id) {
        log.debug("Retrieving trainee with ID: {}", id);
        return dao.findById(id);
    }

    @Override
    public List<Trainee> getAllTrainees() {
        log.debug("Retrieving all trainees");
        return dao.findAll();
    }
}
