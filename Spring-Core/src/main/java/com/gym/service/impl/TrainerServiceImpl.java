package com.gym.service.impl;

import com.gym.dao.TrainerDao;
import com.gym.model.Trainer;
import com.gym.service.TrainerService;
import com.gym.util.UsernamePasswordGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TrainerServiceImpl implements TrainerService {

    private TrainerDao dao;
    private UsernamePasswordGenerator generator;

    @Autowired
    public void setDao(TrainerDao dao) {
        this.dao = dao;
    }

    @Autowired
    public void setGenerator(UsernamePasswordGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void createTrainer(Trainer trainer) {
        log.debug("Creating trainer: {}", trainer);
        trainer.setUsername(generator.generateUsername(trainer.getFirstName(), trainer.getLastName(), dao.findAll()));
        trainer.setPassword(generator.generatePassword());
        dao.save(trainer);
        log.debug("Trainer created with username: {}", trainer.getUsername());
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        log.debug("Updating trainer: {}", trainer);
        dao.update(trainer);
        log.debug("Trainer with ID {} updated successfully", trainer.getUserID());
    }

    @Override
    public Optional<Trainer> getTrainer(Long id) {
        log.debug("Retrieving trainer with ID: {}", id);
        return dao.findById(id);
    }

    @Override
    public List<Trainer> getAllTrainers() {
        log.debug("Retrieving all trainers");
        return dao.findAll();
    }
}
