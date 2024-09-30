package com.hatfieldJuniorSwimmingSchool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {

    // Test to check if the grade level is correctly identified
    @Test
    void checkGradeLevelTest() {
        System.out.println("checkGradeLevel");
        boolean expResult = true;
        boolean actualResult = Utility.checkGradeLevel(3);
        assertEquals(expResult,actualResult);
    }
}