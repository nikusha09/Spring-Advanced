package com.gym.util;

import com.gym.model.Trainee;
import com.gym.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsernamePasswordGeneratorTest {

    private UsernamePasswordGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new UsernamePasswordGenerator();
    }

    private Trainee traineeWithUsername(String username) {
        User user = new User();
        user.setUsername(username);
        Trainee trainee = new Trainee();
        trainee.setUser(user);
        return trainee;
    }

    @Test
    void generateUsername_noExisting_returnsBase() {
        String username = generator.generateUsername("John", "Smith", List.of());
        assertEquals("John.Smith", username);
    }

    @Test
    void generateUsername_oneDuplicate_returnsSuffixOne() {
        String username = generator.generateUsername("John", "Smith",
                List.of(traineeWithUsername("John.Smith")));
        assertEquals("John.Smith1", username);
    }

    @Test
    void generateUsername_twoDuplicates_returnsSuffixTwo() {
        String username = generator.generateUsername("John", "Smith",
                List.of(traineeWithUsername("John.Smith"),
                        traineeWithUsername("John.Smith1")));
        assertEquals("John.Smith2", username);
    }

    @Test
    void generateUsername_differentName_noSuffix() {
        String username = generator.generateUsername("John", "Smith",
                List.of(traineeWithUsername("Jane.Doe")));
        assertEquals("John.Smith", username);
    }

    @Test
    void generatePassword_lengthIsTen() {
        String password = generator.generatePassword();
        assertEquals(10, password.length());
    }

    @Test
    void generatePassword_notNullOrBlank() {
        String password = generator.generatePassword();
        assertNotNull(password);
        assertFalse(password.isBlank());
    }

    @Test
    void generatePassword_twoCallsProduceDifferentResults() {
        String first  = generator.generatePassword();
        String second = generator.generatePassword();
        assertNotEquals(first, second);
    }
}
