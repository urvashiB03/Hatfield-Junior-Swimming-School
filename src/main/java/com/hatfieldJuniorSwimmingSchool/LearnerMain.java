package com.hatfieldJuniorSwimmingSchool;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class LearnerMain {

    // ArrayList to store learner objects
    public static ArrayList<Learner> learner_List = new ArrayList<>();

    // Initialize scanner for user input
    Scanner sc = new Scanner(System.in);

    // Method to create a new learner
    public void createLearner() {
        System.out.println("\nEnter Learner information");
        System.out.println("Enter your firstName and lastName");
        String name = sc.nextLine().toUpperCase();
        System.out.println("Enter your gender");
        String gender = sc.next().toUpperCase();
        long number = Utility.readLongInput("Enter your contact number");
        int grade = Utility.readIntegerInput("Enter garde level");
        LocalDate date = Utility.checkDateFormat("Enter Date of Birth in yyyy-MM-dd format");
        Learner new_Learner = new Learner(name, date, gender, number, grade);
        int array_List_Size = learner_List.size();
        if (new_Learner.getDob() != null) {
            addLearner(new_Learner);
            if (learner_List.size() == (array_List_Size + 1))
                System.out.println("Learner created successfully! \nYour learner id is: " + new_Learner.getID() + "\n");
        } else {
            System.out.println("Failed to create user. Please try again.\n");
        }
    }

    // Method to add a learner to the list
    public void addLearner(Learner l) {
        learner_List.add(l);
    }

    // Method to display all learners
    public void showAllLearner() {
        int index = 0;
        System.out.printf("%-20s  %-30s  %-20s  %-20s  %-20s  %-20s%n", "Learner_Id", "Name", "Date_Of_Birth", "Gender", "Contact_Number", "Grade_Level");
        while (index < learner_List.size()) {
            Learner temp = learner_List.get(index);
            System.out.println(temp.displayFormat());
            index++;
        }
    }

    // Method to read learner data from a CSV file
    public void readingLearnerData() {
        InputStream input_Stream = LearnerMain.class.getResourceAsStream("/Learner.csv");
        assert input_Stream != null;
        Scanner scanner = new Scanner(input_Stream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("#")) {
                continue;
            }
            Learner learner = getLearner(line);
            learner_List.add(learner);
        }
    }

    // Method to create a learner object from a CSV line
    public Learner getLearner(String line) {
        String[] columns = line.split(",");
        return new Learner(
                columns[0],                                        // Name
                LocalDate.parse(columns[1], Utility.formatter),    // Date of Birth
                columns[2].toUpperCase(),                          // Gender
                Long.parseLong(columns[3]),                        // Phone Number
                Integer.parseInt(columns[4])                       // Grade
        );
    }

    // Method to update grade of a learner by learner id
    public static void updateGrade(int learner_Id, int learner_grade) {
        learner_List.get(learner_Id).setGrade_Level(learner_grade);
    }

}
