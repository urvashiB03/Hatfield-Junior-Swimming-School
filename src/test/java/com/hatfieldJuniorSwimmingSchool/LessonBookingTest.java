package com.hatfieldJuniorSwimmingSchool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LessonBookingTest {
    LessonBooking lessonBooking;
    @BeforeEach
    void setUp() {
        // Initialize your class under test
        lessonBooking = new LessonBooking();
        // Mocking learner data
        LearnerMain.learner_List.clear();
        lessonBooking.lesson_List.clear();
        LearnerMain.learner_List.add(new Learner("Will Byers",LocalDate.parse("2018-05-23",Utility.formatter),"MALE", 234567, 1));
    }

    // filter lesson for monday
    @Test
    void filterLessonTest() {
        System.out.println("filterLesson");
        // mocking available lesson data
        lessonBooking.lesson_List.add(new Lesson(0,LocalDate.parse("2024-05-06",Utility.formatter),1,null,0,null));
        boolean expResult = true;
        boolean actualResult = lessonBooking.filterLesson("MONDAY",1);
        assertEquals(expResult,actualResult);
    }

    // Test to check if learner has duplicate booking or not
    @Test
    void checkDuplicateBookingTest() {
        System.out.println("checkDuplicateBooking");
        // mocking registration for lesson
        lessonBooking.lesson_List.add(new Lesson(LearnerMain.learner_List.getFirst().getLearner_Id(),LocalDate.parse("2024-05-13",Utility.formatter),1,"BOOKED",0,null));
        boolean expResult = true;
        // check weather booking is exist or not
        boolean actualResult = lessonBooking.checkDuplicateBooking(LearnerMain.learner_List.getFirst().getLearner_Id(),lessonBooking.lesson_List.getFirst().getBooking_Date(),lessonBooking.lesson_List.getFirst().getLesson_Of_The_Week());
        assertEquals(expResult,actualResult);
    }

    // Test to check leaner age if leaner is between 4 and 11 then its true else false
    @Test
    void calculateAgeTest() {
        System.out.println("calculateAge");
        boolean expResult = true;
        boolean actualResult = lessonBooking.calculateAge(LearnerMain.learner_List.getFirst().getDob());
        assertEquals(expResult,actualResult);
    }
}