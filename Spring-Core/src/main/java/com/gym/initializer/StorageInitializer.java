package com.gym.initializer;

import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.Training;
import com.gym.storage.InMemoryStorage;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDate;

@Slf4j
@Component
public class StorageInitializer {

    private InMemoryStorage storage;

    @Value("${storage.trainee.file}")
    private String traineeFile;

    @Value("${storage.trainer.file}")
    private String trainerFile;

    @Value("${storage.training.file}")
    private String trainingFile;

    @Autowired
    public void setStorage(InMemoryStorage storage) {
        this.storage = storage;
    }


    @PostConstruct
    public void init() {
        log.info("Starting storage initialization");
        loadTrainees();
        loadTrainers();
        loadTrainings();
        log.info("Storage initialization completed");
    }

    public void loadTrainees() {
        log.debug("Loading trainees from file: {}", traineeFile);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource(traineeFile).getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] p = line.split(",");
                Long id = Long.parseLong(p[0].trim());
                Trainee trainee = new Trainee(
                        p[1].trim(), // firstName
                        p[2].trim(), // lastName
                        p[3].trim(), // username
                        p[4].trim(), // password
                        Boolean.parseBoolean(p[5].trim()), // isActive
                        id, // userID
                        LocalDate.parse(p[6].trim()), // dateOfBirth
                        p[7].trim()  // address
                );
                storage.getTraineeStorage().put(id, trainee);
                log.debug("Loaded trainee: {}", trainee);
            }
        } catch (Exception e) {
            log.error("Failed to load trainee file: {}", traineeFile, e);
            throw new RuntimeException("Failed to load trainee file: " + traineeFile, e);
        }
    }

    public void loadTrainers() {
        log.debug("Loading trainers from file: {}", trainerFile);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource(trainerFile).getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] p = line.split(",");
                Long id = Long.parseLong(p[0].trim());
                Trainer trainer = new Trainer(
                    p[1].trim(), // firstName
                    p[2].trim(), // lastName
                    p[3].trim(), // username
                    p[4].trim(), // password
                    Boolean.parseBoolean(p[5].trim()), // isActive
                    p[6].trim(), // specialization
                    id // userID
                );
                storage.getTrainerStorage().put(id, trainer);
                log.debug("Loaded trainer: {}", trainer);
            }
        } catch (Exception e) {
            log.error("Failed to load trainer file: {}", trainerFile, e);
            throw new RuntimeException("Failed to load trainer file: " + trainerFile, e);
        }
    }

    public void loadTrainings() {
        log.debug("Loading trainings from file: {}", trainingFile);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource(trainingFile).getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] p = line.split(",");
                Long id = Long.parseLong(p[0].trim());
                Training training = new Training(
                    id, // id
                    Long.parseLong(p[1].trim()), // traineeId
                    Long.parseLong(p[2].trim()), // trainerId
                    p[3].trim(), // trainingName
                    p[4].trim(), // trainingType
                    LocalDate.parse(p[5].trim()), // trainingDate
                    Duration.ofMinutes(Long.parseLong(p[6].trim())) // trainingDuration
                );
                storage.getTrainingStorage().put(id, training);
                log.debug("Loaded training: {}", training);
            }
        } catch (Exception e) {
            log.error("Failed to load training file: {}", trainingFile, e);
            throw new RuntimeException("Failed to load training file: " + trainingFile, e);
        }
    }
}
