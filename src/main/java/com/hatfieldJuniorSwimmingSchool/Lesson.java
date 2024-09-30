package com.hatfieldJuniorSwimmingSchool;

import java.time.*;

public class Lesson {
    static int global_Lesson_Booking_Id = 0;
    private int learner_Id;
    private String status;
    private String review;
    private int rating;
    private final int lesson_Id;
    private final LocalDate booking_Date;
    private final int lesson_Of_The_Week;
    private final int week_Number;

    // Constructor
    public Lesson(int learner_Id, LocalDate dob, int lesson_Of_The_Week, String status, int rating, String review) {
        this.lesson_Id = generateID();
        this.learner_Id = learner_Id;
        this.booking_Date = dob;
        this.lesson_Of_The_Week = lesson_Of_The_Week;
        this.status = status;
        this.rating = rating;
        this.review = review;
        this.week_Number = Utility.weekNumber(booking_Date);
    }

    //Getter method
    public int getLesson_Id() {
        return lesson_Id;
    }

    public int getLearner_Id() {
        return learner_Id;
    }

    public LocalDate getBooking_Date() { return booking_Date; }

    public int getLesson_Of_The_Week() {
        return lesson_Of_The_Week;
    }

    public int getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }

    //Setter method
    public void setStatus(String status) {
        this.status = status;
    }

    public void setLearner_Id(int learner_Id) {
        this.learner_Id = learner_Id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
    }

    // Generate unique lesson ID
    public int generateID() {
        return global_Lesson_Booking_Id++;
    }

    // Override toString method for better formatting
    @Override
    public String toString() {
        return lesson_Id + "," + learner_Id + "," + booking_Date + "," + lesson_Of_The_Week + "," + week_Number + "," + status + "," + rating + "," + review;
    }

    // Method for display
    public String displayFormat() {
        return String.format("%-20s   %-20s   %-20s   %-20s   %-20s   %-20s   %-20s", lesson_Id, learner_Id, booking_Date, lesson_Of_The_Week, status, rating, review);
    }

}
