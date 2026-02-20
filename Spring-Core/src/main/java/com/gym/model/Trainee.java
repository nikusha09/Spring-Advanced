package com.gym.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Trainee extends User {

    private Long userID;
    private LocalDate dateOfBirth;
    private String address;

    public Trainee(String firstName, String lastName, String username, String password, boolean isActive, Long userID, LocalDate dateOfBirth, String address) {
        super(firstName, lastName, null, null, isActive);
        this.userID = userID;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }
}
