package com.gym.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Trainer extends User {

    private String specialization;
    private Long userID;

    public Trainer(String firstName, String lastName, String username, String password, boolean isActive, String specialization, Long userID) {
        super(firstName, lastName, null, null, isActive);
        this.specialization = specialization;
        this.userID = userID;
    }
}
