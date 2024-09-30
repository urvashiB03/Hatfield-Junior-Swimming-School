package com.hatfieldJuniorSwimmingSchool;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;

public class Utility {

    // DateTimeFormatter for date parsing
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Enum for grade levels
    public enum GradeLevel {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5);
        private final int value;
        GradeLevel(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    // Method to check if a given grade level exists in the enum
    public static boolean checkGradeLevel(int grade) {
        boolean is_Enum_Value = false;
        for (GradeLevel number : GradeLevel.values()) {
            if (grade == number.getValue()) {
                is_Enum_Value = true;
                break;
            }
        }
        return is_Enum_Value;
    }

    // Method to read integer input from the user
    public static int readIntegerInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println(prompt);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number");
                scanner.next();
            }
        }
    }

    // Method to read long input (for phone number) from the user
    public static long readLongInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println(prompt);
                return scanner.nextLong();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid 10 digit number.");
                scanner.next();
            }
        }
    }

    // Method to check and parse date input from the user
    public static LocalDate checkDateFormat(String prompt) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println(prompt);
                return LocalDate.parse(scanner.nextLine(), formatter);
            } catch (Exception e) {
                System.out.println("Error: Date format is invalid. Please enter a valid date in yyyy-MM-dd format");
            }
        }
    }

    public static int weekNumber(LocalDate date) {
        return date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
    }

}
