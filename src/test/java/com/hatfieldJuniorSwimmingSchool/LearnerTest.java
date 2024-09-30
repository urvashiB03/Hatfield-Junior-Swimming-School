package com.hatfieldJuniorSwimmingSchool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LearnerTest {
    // Test to check if the gender is correct or not
    @Test
    void isGenderTest() {
        System.out.println("isGender");
        String expResult = "FEMALE";
        String actualResult = Learner.isGender("FEMALE");
        assertEquals(expResult,actualResult);
    }
}