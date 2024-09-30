package com.hatfieldJuniorSwimmingSchool;

import java.time.LocalDate;

public class Learner {
    private static int global_Learner_Id = 0;
    private final String name;
    private final LocalDate dob;
    private final String gender;
    private final String contact_Number;
    private int grade_Level;
    private final int learner_Id;

    //Enum for gender
    public enum Gender {
        MALE,
        FEMALE
    }

    // constructor
    public Learner(String name, LocalDate dob, String gender, long number, int grade) {
        this.learner_Id = generateID();
        this.name = name;
        this.dob = dob;
        this.gender = isGender(gender);
        this.contact_Number = checkContactNumber(number);
        this.grade_Level = (!Utility.checkGradeLevel(grade)) ? 1 : grade;
    }

    // Method to check if a gender is from enum or not
    static public String isGender(String g) {
        boolean is_Enum_Value = false;
        for (Gender gender : Gender.values()) {
            if (gender.name().equals(g)) {
                is_Enum_Value = true;
                break;
            }
        }
        if (is_Enum_Value) return g;
        else return "GENDERLESS";
    }

    // Method to check contact number
    public String checkContactNumber(long phoneNumber) {
        StringBuilder phone_Number_String = new StringBuilder(String.valueOf(phoneNumber));
        if (phone_Number_String.length() < 10) {
            int l = 10 - phone_Number_String.length();
            for (int i = 1; i <= l; i++) {
                phone_Number_String.insert(0, "0");
            }
            return phone_Number_String.toString();
        } else return phone_Number_String.substring(1, 10);
    }

    //Getter method
    public String getName() {
        return name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public int getGrade_Level() {
        return grade_Level;
    }

    public int getLearner_Id() {
        return learner_Id;
    }

    public int generateID() {
        return global_Learner_Id++;
    }

    public int getID(){
        return learner_Id;
    }

    //Setter method
    public void setGrade_Level(int grade_Level) {
        this.grade_Level = grade_Level;
    }

    // Override toString method for better formatting
    @Override
    public String toString() {
        return learner_Id + "," + name + "," + dob + "," + gender + "," + contact_Number + "," + grade_Level;
    }

    // Method for display
    public String displayFormat() {
        return String.format("%-20s  %-30s  %-20s  %-20s  %-20s  %-20s", learner_Id, name, dob, gender, contact_Number, grade_Level);
    }

}
