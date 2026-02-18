package com.gym.model;

public class Trainer extends User {

    private String specialization;
    private Long userID;

    public Trainer() {}

    public Trainer(String firstName, String lastName, String username, String password, boolean isActive, String specialization, Long userID) {
        super(firstName, lastName, null, null, isActive);
        this.specialization = specialization;
        this.userID = userID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "userID=" + userID +
                ", specialization='" + specialization + '\'' +
                super.toString() +
                '}';
    }
}
