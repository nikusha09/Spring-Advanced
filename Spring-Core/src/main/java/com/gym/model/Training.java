package com.gym.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Training {

    private Long id;
    private Long traineeId;
    private Long trainerId;
    private String trainingName;
    private String trainingType;
    private LocalDate trainingDate;
    private Duration trainingDuration;

}
