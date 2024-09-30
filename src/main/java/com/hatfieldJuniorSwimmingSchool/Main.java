package com.hatfieldJuniorSwimmingSchool;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Welcome message
        System.out.println("Welcome to the Hatfield Junior Swimming School");

        // Initialize scanner for user input
        Scanner sc = new Scanner(System.in);

        // Initialize LearnerMain and LessonBooking instances
        LearnerMain LearnerMain = new LearnerMain();
        LessonBooking LessonBooking = new LessonBooking();

        // Read learner data and lesson and create advance bookings for one month
        LearnerMain.readingLearnerData();
        LessonBooking.readingAllBooking();
        LessonBooking.createFutureLesson();

        // Main loop to display menu and process user input
        boolean exit = false;
        while (!exit) {
           // Display menu options
            System.out.println();
            System.out.println("Please select one of the following options:");
            System.out.println("1. Book a swimming lesson");
            System.out.println("2. Cancel a booking");
            System.out.println("3. Attending a swimming lesson");
            System.out.println("4. Monthly learner report");
            System.out.println("5. Monthly coach report");
            System.out.println("6. Add a new learner");
            System.out.println("7. Check all learner data");
            System.out.println("8. Check all bookings");
            System.out.println("9. Exit");
            System.out.println("Enter your choice");

            // Process user input
            int processNumber = Utility.readIntegerInput("");
            switch (processNumber) {
                case 1:
                    LessonBooking.lessonBooking();
                    break;
                case 2:
                    LessonBooking.cancelLesson();
                    break;
                case 3:
                    LessonBooking.attendingLesson();
                    break;
                case 4:
                    LessonBooking.monthlyLearnerReport();
                    break;
                case 5:
                    LessonBooking.monthlyCoachReport();
                    break;
                case 6:
                    LearnerMain.createLearner();
                    break;
                case 7:
                    LearnerMain.showAllLearner();
                    break;
                case 8:
                    LessonBooking.showAllLesson();
                    break;
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                    break;
            }
        }

        // Close scanner
        sc.close();
    }
}
