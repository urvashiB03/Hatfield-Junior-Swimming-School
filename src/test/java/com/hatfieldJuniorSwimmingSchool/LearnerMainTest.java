package com.hatfieldJuniorSwimmingSchool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LearnerMainTest {

    LearnerMain learnerMain;

    @BeforeEach
    void setUp() {
        learnerMain = new LearnerMain();
    }

    // Test to check if leaner added or not
    @Test
    void addLearnerTest() {
        System.out.println("addLearner");
        int expResult = 1;
        Learner l = learnerMain.getLearner("WANG DANIEL,2018-08-12,Male,0987760945,3");
        learnerMain.addLearner(l);
        int actualResult = LearnerMain.learner_List.size();
        assertEquals(expResult,actualResult);
    }
}