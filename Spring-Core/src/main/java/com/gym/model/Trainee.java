package com.gym.model;

import java.time.LocalDate;

public class Trainee extends User {

    private Long userID;
    private LocalDate dateOfBirth;
    private String address;

    public Trainee() {}

    public Trainee(String firstName, String lastName, String username, String password, boolean isActive, Long userID, LocalDate dateOfBirth, String address) {
        super(firstName, lastName, null, null, isActive);
        this.userID = userID;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "userID=" + userID +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                super.toString() +
                '}';
    }
}
