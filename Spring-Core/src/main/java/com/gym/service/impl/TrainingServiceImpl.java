package com.gym.service.impl;

import com.gym.dao.TrainingDao;
import com.gym.model.Training;
import com.gym.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService {

    private static final Logger log = LoggerFactory.getLogger(TrainingServiceImpl.class);

    private TrainingDao dao;

    @Autowired
    public void setDao(TrainingDao dao) {
        this.dao = dao;
    }

    @Override
    public void createTraining(Training training) {
        log.debug("Creating training: {}", training);
        dao.save(training);
        log.debug("Training created with ID: {}", training.getId());
    }

    @Override
    public Optional<Training> getTraining(Long id) {
        log.debug("Retrieving training with ID: {}", id);
        return dao.findById(id);
    }

    @Override
    public List<Training> getAllTrainings() {
        log.debug("Retrieving all trainings");
        return dao.findAll();
    }
}
