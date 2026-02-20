package com.gym;

import com.gym.config.AppConfig;
import com.gym.model.Trainee;
import com.gym.model.Trainer;
import com.gym.model.Training;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Duration;
import java.time.LocalDate;

@Slf4j
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        GymFacade gymFacade = context.getBean(GymFacade.class);

        log.info("Trainee use cases:");

        Trainee trainee1 = new Trainee();
        trainee1.setFirstName("John");
        trainee1.setLastName("Smith");
        trainee1.setDateOfBirth(LocalDate.of(1995, 3, 20));
        trainee1.setAddress("100 Main St");
        trainee1.setActive(true);
        gymFacade.createTrainee(trainee1);
        log.info("Created trainee: {}", trainee1);

        Trainee trainee = gymFacade.getTrainee(trainee1.getUserID());
        log.info("Found trainee by id: {}", trainee.toString());

        trainee1.setAddress("999 Updated Blvd");
        trainee1.setActive(false);
        gymFacade.updateTrainee(trainee1);
        log.info("Updated trainee: {}", trainee1);

        gymFacade.deleteTrainee(trainee1.getUserID());
        log.info("Deleted trainee: {}", trainee1.toString());
        log.info("All trainees after delete: ");
        gymFacade.getAllTrainees().forEach(t -> log.info(t.toString()));

        log.info("\nTrainer use cases:");

        Trainer trainer1 = new Trainer();
        trainer1.setFirstName("Michael");
        trainer1.setLastName("Johnson");
        trainer1.setSpecialization("Yoga");
        trainer1.setActive(true);
        gymFacade.createTrainer(trainer1);
        log.info("Created trainer: {}", trainer1);

        log.info("All trainers: ");
        gymFacade.getAllTrainers().forEach(t -> log.info(t.toString()));

        Trainer trainer = gymFacade.getTrainer(trainee1.getUserID());
        log.info("Found trainee by id: {}", trainer.toString());

        trainer1.setSpecialization("Pilates");
        trainer1.setActive(false);
        gymFacade.updateTrainer(trainer1);
        log.info("Updated trainer: {}", trainer1);

        log.info("\nTraining use cases:");

        Training training1 = new Training();
        training1.setTraineeId(trainee1.getUserID());
        training1.setTrainerId(trainer1.getUserID());
        training1.setTrainingName("Morning Yoga");
        training1.setTrainingType("Yoga");
        training1.setTrainingDate(LocalDate.of(2024, 7, 1));
        training1.setTrainingDuration(Duration.ofMinutes(45));
        gymFacade.createTraining(training1);
        log.info("Created training: {}", training1);

        log.info("All trainings: ");
        gymFacade.getAllTrainings().forEach(t -> log.info(t.toString()));

        Training training = gymFacade.getTraining(training1.getId());
        log.info("Found trainee by id: {}", training.toString());

        context.close();
    }
}
