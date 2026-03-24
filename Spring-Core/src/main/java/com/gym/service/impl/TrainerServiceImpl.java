package com.gym.service.impl;

import com.gym.exception.EntityNotFoundException;
import com.gym.exception.ValidationException;
import com.gym.model.Trainer;
import com.gym.model.Training;
import com.gym.repository.TrainerRepository;
import com.gym.service.AuthenticationService;
import com.gym.service.TrainerService;
import com.gym.util.UsernamePasswordGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TrainerServiceImpl implements TrainerService {

    private TrainerRepository trainerRepository;
    private UsernamePasswordGenerator generator;
    private AuthenticationService authService;

    @Autowired
    public void setTrainerRepository(TrainerRepository trainerRepository) { this.trainerRepository = trainerRepository; }

    @Autowired
    public void setGenerator(UsernamePasswordGenerator generator) { this.generator = generator; }

    @Autowired
    public void setAuthService(AuthenticationService authService) { this.authService = authService; }

    @Override
    @Transactional
    public void createTrainer(Trainer trainer) {
        validateTrainerForCreate(trainer);
        String username = generator.generateUsername(
                trainer.getUser().getFirstName(),
                trainer.getUser().getLastName(),
                trainerRepository.findAll()
                        .stream()
                        .map(t -> t.getUser())
                        .toList()
        );
        trainer.getUser().setUsername(username);
        trainer.getUser().setPassword(generator.generatePassword());
        trainerRepository.save(trainer);
        log.info("Trainer created with username: {}", username);
    }

    @Override
    @Transactional
    public void updateTrainer(String username, String password, Trainer trainer) {
        authService.authenticate(username, password);
        validateTrainerForUpdate(trainer);
        trainerRepository.save(trainer);
        log.info("Trainer updated: {}", username);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Trainer> getTrainer(String username, String password) {
        authService.authenticate(username, password);
        log.debug("Fetching trainer: {}", username);
        return trainerRepository.findByUserUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trainer> getAllTrainers() {
        log.debug("Fetching all trainers");
        return trainerRepository.findAll();
    }

    @Override
    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        authService.authenticate(username, oldPassword);
        if (newPassword == null || newPassword.isBlank()) {
            throw new ValidationException("New password must not be blank");
        }
        Trainer trainer = trainerRepository.findByUserUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Trainer", username));
        trainer.getUser().setPassword(newPassword);
        trainerRepository.save(trainer);
        log.info("Password changed for trainer: {}", username);
    }

    @Override
    @Transactional
    public void activateDeactivate(String username, String password) {
        authService.authenticate(username, password);
        Trainer trainer = trainerRepository.findByUserUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Trainer", username));
        boolean current = trainer.getUser().isActive();
        trainer.getUser().setActive(!current);
        trainerRepository.save(trainer);
        log.info("Trainer {} activation status changed from {} to {}", username, current, !current);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Training> getTrainings(String username, String password,
                                       LocalDate fromDate, LocalDate toDate, String traineeName) {
        authService.authenticate(username, password);
        log.debug("Fetching trainings for trainer: {}", username);
        return trainerRepository.findTrainings(username, fromDate, toDate, traineeName);
    }

    private void validateTrainerForCreate(Trainer trainer) {
        if (trainer.getUser() == null)
            throw new ValidationException("User must not be null");
        if (trainer.getUser().getFirstName() == null || trainer.getUser().getFirstName().isBlank())
            throw new ValidationException("First name is required");
        if (trainer.getUser().getLastName() == null || trainer.getUser().getLastName().isBlank())
            throw new ValidationException("Last name is required");
        if (trainer.getSpecialization() == null)
            throw new ValidationException("Specialization is required");
    }

    private void validateTrainerForUpdate(Trainer trainer) {
        validateTrainerForCreate(trainer);
        if (trainer.getId() == null)
            throw new ValidationException("Trainer id is required for update");
    }
}
