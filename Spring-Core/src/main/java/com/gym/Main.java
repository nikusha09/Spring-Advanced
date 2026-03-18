package com.gym;

import com.gym.config.AppConfig;
import com.gym.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Main {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        GymFacade facade = context.getBean(GymFacade.class);

        // Below is code for simulation of implemented functionality

        // ================================================================
        // CREATE TRAINEE PROFILE
        // ================================================================
        User traineeUser1 = new User();
        traineeUser1.setFirstName("John");
        traineeUser1.setLastName("Smith");
        traineeUser1.setActive(true);

        Trainee trainee1 = new Trainee();
        trainee1.setUser(traineeUser1);
        trainee1.setDateOfBirth(LocalDate.of(1995, 3, 20));
        trainee1.setAddress("100 Main St");
        facade.createTrainee(trainee1);
        log.info("Created trainee: {}", trainee1.getUser().getUsername());

        // CREATE duplicate name trainee
        User traineeUser2 = new User();
        traineeUser2.setFirstName("John");
        traineeUser2.setLastName("Smith");
        traineeUser2.setActive(true);

        Trainee trainee2 = new Trainee();
        trainee2.setUser(traineeUser2);
        trainee2.setDateOfBirth(LocalDate.of(1998, 6, 15));
        trainee2.setAddress("200 Oak Ave");
        facade.createTrainee(trainee2);
        log.info("Created trainee (duplicate): {}", trainee2.getUser().getUsername());

        String traineeUsername = trainee1.getUser().getUsername();
        String traineePassword = trainee1.getUser().getPassword();

        // ================================================================
        // CREATE TRAINER PROFILE
        // ================================================================
        TrainingType yoga    = facade.getTrainingTypeByName("Yoga").orElseThrow();

        User trainerUser1 = new User();
        trainerUser1.setFirstName("Michael");
        trainerUser1.setLastName("Johnson");
        trainerUser1.setActive(true);

        Trainer trainer1 = new Trainer();
        trainer1.setUser(trainerUser1);
        trainer1.setSpecialization(yoga);
        facade.createTrainer(trainer1);
        log.info("Created trainer: {}", trainer1.getUser().getUsername());

        User trainerUser2 = new User();
        trainerUser2.setFirstName("Sarah");
        trainerUser2.setLastName("Williams");
        trainerUser2.setActive(true);

        TrainingType cardio  = facade.getTrainingTypeByName("Cardio").orElseThrow();
        Trainer trainer2 = new Trainer();
        trainer2.setUser(trainerUser2);
        trainer2.setSpecialization(cardio);
        facade.createTrainer(trainer2);
        log.info("Created trainer: {}", trainer2.getUser().getUsername());

        String trainerUsername = trainer1.getUser().getUsername();
        String trainerPassword = trainer1.getUser().getPassword();

        // ================================================================
        // TRAINEE USERNAME AND PASSWORD MATCHING
        // ================================================================
        facade.getTrainee(traineeUsername, traineePassword)
                .ifPresent(t -> log.info("Trainee auth OK: {}", t.getUser().getUsername()));

        // ================================================================
        // TRAINER USERNAME AND PASSWORD MATCHING
        // ================================================================
        facade.getTrainer(trainerUsername, trainerPassword)
                .ifPresent(t -> log.info("Trainer auth OK: {}", t.getUser().getUsername()));

        // ================================================================
        // SELECT TRAINER PROFILE BY USERNAME
        // ================================================================
        facade.getTrainer(trainerUsername, trainerPassword)
                .ifPresent(t -> log.info("Found trainer: {}", t));

        // ================================================================
        // SELECT TRAINEE PROFILE BY USERNAME
        // ================================================================
        facade.getTrainee(traineeUsername, traineePassword)
                .ifPresent(t -> log.info("Found trainee: {}", t));

        // ================================================================
        // TRAINEE PASSWORD CHANGE
        // ================================================================
        String newTraineePassword = "newPass123";
        facade.changeTraineePassword(traineeUsername, traineePassword, newTraineePassword);
        traineePassword = newTraineePassword;
        trainee1.getUser().setPassword(newTraineePassword);
        log.info("Trainee password changed for: {}", traineeUsername);

        // ================================================================
        // TRAINER PASSWORD CHANGE
        // ================================================================
        String newTrainerPassword = "newPass456";
        facade.changeTrainerPassword(trainerUsername, trainerPassword, newTrainerPassword);
        trainerPassword = newTrainerPassword;
        trainer1.getUser().setPassword(newTrainerPassword);
        log.info("Trainer password changed for: {}", trainerUsername);

        // ================================================================
        // UPDATE TRAINER PROFILE
        // ================================================================
        TrainingType pilates = facade.getTrainingTypeByName("Pilates").orElseThrow();
        trainer1.setSpecialization(pilates);
        facade.updateTrainer(trainerUsername, trainerPassword, trainer1);
        log.info("Trainer updated: {}", trainerUsername);

        // ================================================================
        // UPDATE TRAINEE PROFILE
        // ================================================================
        trainee1.setAddress("999 Updated Blvd");
        facade.updateTrainee(traineeUsername, traineePassword, trainee1);
        log.info("Trainee updated: {}", traineeUsername);

        // ================================================================
        // ACTIVATE/DEACTIVATE TRAINEE
        // ================================================================
        facade.activateDeactivateTrainee(traineeUsername, traineePassword);
        log.info("Trainee activate/deactivate toggled: {}", traineeUsername);

        // ================================================================
        // ACTIVATE/DEACTIVATE TRAINER
        // ================================================================
        facade.activateDeactivateTrainer(trainerUsername, trainerPassword);
        log.info("Trainer activate/deactivate toggled: {}", trainerUsername);

        // reactivate both for further operations
        facade.activateDeactivateTrainee(traineeUsername, traineePassword);
        facade.activateDeactivateTrainer(trainerUsername, trainerPassword);

        // ================================================================
        // ADD TRAINING
        // ================================================================
        Training training1 = new Training();
        training1.setTrainee(trainee1);
        training1.setTrainer(trainer1);
        training1.setTrainingName("Morning Yoga");
        training1.setTrainingType(yoga);
        training1.setTrainingDate(LocalDate.of(2024, 7, 1));
        training1.setTrainingDuration(60);
        facade.addTraining(traineeUsername, traineePassword, training1);
        log.info("Training added: {}", training1.getTrainingName());

        Training training2 = new Training();
        training2.setTrainee(trainee1);
        training2.setTrainer(trainer2);
        training2.setTrainingName("Cardio Blast");
        training2.setTrainingType(cardio);
        training2.setTrainingDate(LocalDate.of(2024, 7, 5));
        training2.setTrainingDuration(45);
        facade.addTraining(traineeUsername, traineePassword, training2);
        log.info("Training added: {}", training2.getTrainingName());

        // ================================================================
        // GET TRAINEE TRAININGS LIST WITH CRITERIA
        // ================================================================
        List<Training> traineeTrainings = facade.getTraineeTrainings(
                traineeUsername, traineePassword,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                null, null
        );
        log.info("Trainee trainings count: {}", traineeTrainings.size());

        // ================================================================
        // GET TRAINER TRAININGS LIST WITH CRITERIA
        // ================================================================
        List<Training> trainerTrainings = facade.getTrainerTrainings(
                trainerUsername, trainerPassword,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                null
        );
        log.info("Trainer trainings count: {}", trainerTrainings.size());

        // ================================================================
        // GET TRAINERS NOT ASSIGNED TO TRAINEE
        // ================================================================
        List<Trainer> unassigned = facade.getUnassignedTrainers(traineeUsername, traineePassword);
        log.info("Unassigned trainers count: {}", unassigned.size());

        // ================================================================
        // UPDATE TRAINEE'S TRAINER LIST
        // ================================================================
        facade.updateTraineeTrainers(traineeUsername, traineePassword,
                new ArrayList<>(List.of(trainer1, trainer2)));
        log.info("Updated trainers list for trainee: {}", traineeUsername);

        // ================================================================
        // DELETE TRAINEE PROFILE BY USERNAME
        // ================================================================
        String trainee2Username = trainee2.getUser().getUsername();
        String trainee2Password = trainee2.getUser().getPassword();
        facade.deleteTrainee(trainee2Username, trainee2Password);
        log.info("Trainee deleted: {}", trainee2Username);

        System.out.println(">>> H2 Console: http://localhost:8082");
        System.out.println(">>> JDBC URL: jdbc:h2:mem:testdb");
        System.out.println(">>> Username: sa  |  Password: (blank)");
        System.out.println(">>> Stop execution manually");

        Thread.sleep(60000); // Keep app running to allow H2 console access

        context.close();
    }
}
