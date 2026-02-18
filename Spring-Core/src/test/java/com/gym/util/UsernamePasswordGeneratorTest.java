package com.gym.util;

import com.gym.model.Trainee;
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

    @Test
    void generateUsername_noExisting_returnsBase() {
        String username = generator.generateUsername("John", "Smith", List.of());
        assertEquals("John.Smith", username);
    }

    @Test
    void generateUsername_oneDuplicate_returnsSuffixOne() {
        Trainee existing = new Trainee();
        existing.setUsername("John.Smith");

        String username = generator.generateUsername("John", "Smith", List.of(existing));
        assertEquals("John.Smith1", username);
    }

    @Test
    void generateUsername_twoDuplicates_returnsSuffixTwo() {
        Trainee existing1 = new Trainee();
        existing1.setUsername("John.Smith");
        Trainee existing2 = new Trainee();
        existing2.setUsername("John.Smith1");

        String username = generator.generateUsername("John", "Smith", List.of(existing1, existing2));
        assertEquals("John.Smith2", username);
    }

    @Test
    void generateUsername_differentName_noSuffix() {
        Trainee existing = new Trainee();
        existing.setUsername("Jane.Doe");

        String username = generator.generateUsername("John", "Smith", List.of(existing));
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