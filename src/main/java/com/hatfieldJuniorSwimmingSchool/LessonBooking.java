package com.hatfieldJuniorSwimmingSchool;

import java.util.*;
import java.io.InputStream;
import java.time.*;
import java.time.temporal.TemporalAdjusters;

public class LessonBooking {
    // Initialize scanner for user input
    Scanner sc = new Scanner(System.in);

    /* It's a one week of lesson, representing lesson schedule
     first column represent unique lesson_Of_The_Week_Id,
     second column represent week_Day,
     third column represent lesson_Time,
     fourth column represent grade,
     fifth column represent coach_Name
    */
    private final String[][] lessons_Of_The_Week = {
            {"1", "MONDAY", "4", "1", "LUNA"},
            {"2", "MONDAY", "5", "2", "JAME"},
            {"3", "MONDAY", "6", "3", "CLOE"},
            {"4", "WEDNESDAY", "4", "4", "LUNA"},
            {"5", "WEDNESDAY", "5", "5", "JAME"},
            {"6", "WEDNESDAY", "6", "1", "CLOE"},
            {"7", "FRIDAY", "4", "2", "LUNA"},
            {"8", "FRIDAY", "5", "3", "JAME"},
            {"9", "FRIDAY", "6", "4", "CLOE"},
            {"10", "SATURDAY", "2", "5", "LUNA"},
            {"11", "SATURDAY", "3", "1", "JAME"},
    };

    // Enum for days of the week
    public enum Day {
        MONDAY,
        WEDNESDAY,
        FRIDAY,
        SATURDAY
    }

    // Enum for lesson status
    public enum Status {
        Booked,
        Attended,
        Cancelled
    }

    ArrayList<Lesson> lesson_List = new ArrayList<>();

    int current_Week_Number = Utility.weekNumber(LocalDate.now());

    // Method to manage lesson booking process
    public void lessonBooking() {
        System.out.println("Press 1 if you want to see lessons for a specific day.");
        System.out.println("Press 2 if you want to see lessons for a specific grade level.");
        System.out.println("Press 3 if you want to see lessons for a specific coach.");
        int userInput = Utility.readIntegerInput("");
        switch (userInput) {
            case 1:
                filterByDay();
                break;
            case 2:
                filterByGrade();
                break;
            case 3:
                filterByCoach();
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 3.\n");
                break;
        }
    }

    // Method to filter lessons by day
    public void filterByDay() {
        System.out.println("Please enter a day for booking. (e.g., Monday, Wednesday, Friday, Saturday)");
        String day = sc.next().toUpperCase();
        if (!isDay(day)) {
            System.out.println("Lessons are not available for this day\n");
            return;
        }
        if (filterLesson(day, 1)) {
            registerForLesson();
        } else System.out.println("Lessons are not available for this day.\n");
    }

    // Method to filter lessons by grade
    public void filterByGrade() {
        System.out.println("Please enter a grade");
        int grade = sc.nextInt();
        if (!Utility.checkGradeLevel(grade)) System.out.println("Enter correct grade");
        if (filterLesson(String.valueOf(grade), 3)) {
            registerForLesson();
        } else System.out.println("Lessons are not available for this grade.\n");
    }

    // Method to filter lessons by coach
    public void filterByCoach() {
        System.out.println("Available coach are: \nLuna \nJame \nCloe");
        System.out.println("Please enter a coach_Name");
        String coach_Name = sc.next().toUpperCase();
        if (filterLesson(coach_Name, 4)) {
            registerForLesson();
        } else System.out.println("Lessons are not available.\n");
    }

    // Method to filter lessons based on a specific value (day, grade, or coach)
    public boolean filterLesson(String equal_Value, int column_Number) {
        boolean lesson_Count = false;
        for (Lesson lesson : lesson_List) {
            if (lesson.getLearner_Id() == 0 && lesson.getStatus() == null) {
                int week_Lesson = lesson.getLesson_Of_The_Week();
                int time = Integer.parseInt(lessons_Of_The_Week[week_Lesson - 1][2]);
                if (lessons_Of_The_Week[week_Lesson - 1][column_Number].equals(equal_Value)) {
                    if (!lesson_Count) {
                        lesson_Count = true;
                        System.out.println("Available bookings:");
                        System.out.println("-------------------");
                        System.out.printf("%-20s  %-20s  %-20s\n", "Lesson_Number", "Booking_Date", "Lesson_Time");
                    }
                    System.out.printf("%-20s  %-20s  %-20s\n", lesson.getLesson_Id(), lesson.getBooking_Date(), time + " to " + (time + 1));
                }
            }
        }
        return lesson_Count;
    }

    // Method to register for a lesson
    public void registerForLesson() {
        int lesson_Id = checkValidLessonId();
        int learner_id = checkValidLearnerId();
        if (learner_id != -1 && lesson_Id != -1) {
            int learner_Grade = LearnerMain.learner_List.get(learner_id).getGrade_Level();
            LocalDate age = LearnerMain.learner_List.get(learner_id).getDob();
            int week_Lesson_Id = lesson_List.get(lesson_Id).getLesson_Of_The_Week();
            int lesson_Grade = Integer.parseInt(lessons_Of_The_Week[(week_Lesson_Id - 1)][3]);
            LocalDate booking_Date = lesson_List.get(lesson_Id).getBooking_Date();
            boolean isGradeValid = (learner_Grade == lesson_Grade || (learner_Grade + 1) == lesson_Grade);
            boolean isAgeValid = calculateAge(age);
            boolean isDuplicateBooking = !checkDuplicateBooking(learner_id, booking_Date, week_Lesson_Id);
            if (isGradeValid && isAgeValid && isDuplicateBooking) {
                lesson_List.get(lesson_Id).setLearner_Id(learner_id);
                lesson_List.get(lesson_Id).setStatus(String.valueOf(Status.Booked));
                System.out.println("Lesson booked.");
            } else {
                if (!isAgeValid) {
                    System.out.println("Your age should be between 4 to 11");
                } else if (!isDuplicateBooking) {
                    System.out.println("You have already booked/cancelled this lesson");
                } else {
                    System.out.println("You cannot book this lesson.");
                }
            }
        }
    }

    // Method to check weather learner has booked or not
    public boolean checkDuplicateBooking(int learner_id, LocalDate date, int week_lesson) {
        boolean duplicate = false;
        for (Lesson lesson : lesson_List) {
            if (lesson.getBooking_Date().equals(date) && lesson.getLearner_Id() == learner_id && lesson.getLesson_Of_The_Week() == week_lesson) {
                duplicate = true;
                break;
            }
        }
        return duplicate;
    }

    // Method to cancel a lesson
    public void cancelLesson() {
        int learner_id = checkValidLearnerId();
        if (learner_id != -1) {
            if (learnerAllLesson(learner_id)) {
                System.out.println("No lesson available");
            } else {
                int lesson_Id = checkValidLessonId();
                if (lesson_Id != -1) {
                    LocalDate booking_Date = lesson_List.get((lesson_Id)).getBooking_Date();
                    int week_Lesson = lesson_List.get((lesson_Id)).getLesson_Of_The_Week();
                    String[] columns = {"0", String.valueOf(booking_Date), String.valueOf(week_Lesson), null, null, null};
                    Lesson lesson = getLesson(columns);
                    lesson_List.add(lesson);
                    getLesson(columns);
                    lesson_List.get((lesson_Id)).setStatus(String.valueOf(Status.Cancelled));
                    System.out.println("Lesson Cancelled");
                }
            }
        }
    }

    // Method to give rating and review to a lesson after attending
    public void attendingLesson() {
        int learner_id = checkValidLearnerId();
        if (learner_id != -1) {
            if (learnerAllLesson(learner_id)) {
                System.out.println("No lesson available");
            } else {
                int lesson_Id = checkValidLessonId();
                if (lesson_Id != -1) {
                    checkPreviousLessonGrade(lesson_Id, learner_id);
                    int rating = Utility.readIntegerInput("Give your rating to lesson between 1 to 5");
                    System.out.println("Please give your review");
                    sc.nextLine();
                    String review = sc.nextLine();
                    lesson_List.get(lesson_Id).setStatus(String.valueOf(Status.Attended));
                    lesson_List.get(lesson_Id).setRating(rating);
                    lesson_List.get(lesson_Id).setReview(review);
                    System.out.println("Thank you for your review.\n");
                }
            }
        }
    }

    // Method to update learner grade
    public void checkPreviousLessonGrade(int lesson_id, int learner_id) {
        int week_Lesson_Id = lesson_List.get(lesson_id).getLesson_Of_The_Week();
        int lesson_Grade = Integer.parseInt(lessons_Of_The_Week[(week_Lesson_Id - 1)][3]);
        int actual_learner_Grade = LearnerMain.learner_List.get(learner_id).getGrade_Level();
        if ((actual_learner_Grade + 1) == lesson_Grade) {
            LearnerMain.updateGrade(learner_id, lesson_Grade);
        }
    }

    // Method to display all lesson
    public boolean learnerAllLesson(int learner_Id) {
        boolean count = true;
        for (Lesson lesson : lesson_List) {
            if (lesson.getLearner_Id() == learner_Id) {
                if (count) {
                    count = false;
                    System.out.printf("%-20s   %-20s   %-20s   %-20s   %-20s   %-20s   %-20s%n", "lesson_Number", "learner_Id", "booking_Date", "lesson_Of_The_Week", "status", "rating", "review");
                }
                System.out.println(lesson.displayFormat());
            }
        }
        System.out.println();
        return count;
    }

    // Method to read lesson data from a CSV file
    public void readingAllBooking() {
        InputStream input_Stream = LessonBooking.class.getResourceAsStream("/LearnerAppointment.csv");
        assert input_Stream != null;
        Scanner scanner = new Scanner(input_Stream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("#")) {
                continue;
            }
            String[] columns = line.split(",");
            Lesson lesson = getLesson(columns);
            lesson_List.add(lesson);
        }
    }

    // Method to create a lesson object from a CSV line
    public Lesson getLesson(String[] columns) {
        return new Lesson(
                ("null".equals(columns[0]) || columns[0] == null) ? 0 : Integer.parseInt(columns[0]), // learner_ID
                LocalDate.parse(columns[1], Utility.formatter),                                       // booking_Date
                Integer.parseInt(columns[2]),                                                         // lesson_Of_The_Week
                columns[3],                                                                           // status
                ("null".equals(columns[4]) || columns[4] == null) ? 0 : Integer.parseInt(columns[4]), // rating
                String.valueOf(columns[5])                                                            // review
        );
    }

    // Method to calculate age
    public boolean calculateAge(LocalDate date) {
        LocalDate curDate = LocalDate.now();
        int age = Period.between(date, curDate).getYears();
        return age > 4 && age < 11;
    }

    // Method to generate one month advance lesson
    public void createFutureLesson() {
        LocalDate end_Date = LocalDate.now().plusMonths(1);
        int last_Week_Number = Utility.weekNumber(end_Date);
        int current_Year = Year.now().getValue();
        for (int i = current_Week_Number; i <= last_Week_Number; i++) {
            LocalDate first_Day_Of_Year = LocalDate.of(current_Year, 1, 1);
            LocalDate monday = first_Day_Of_Year.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)).plusWeeks(i - 1);
            if (monday.isAfter(LocalDate.now()))
                generateFutureLesson(monday.format(Utility.formatter), String.valueOf(Day.MONDAY));
            LocalDate wednesday = first_Day_Of_Year.with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY)).plusWeeks(i - 1);

            if (wednesday.isAfter(LocalDate.now()))
                generateFutureLesson(wednesday.format(Utility.formatter), String.valueOf(Day.WEDNESDAY));
            LocalDate friday = first_Day_Of_Year.with(TemporalAdjusters.firstInMonth(DayOfWeek.FRIDAY)).plusWeeks(i - 1);

            if (friday.isAfter(LocalDate.now()))
                generateFutureLesson(friday.format(Utility.formatter), String.valueOf(Day.FRIDAY));
            LocalDate saturday = first_Day_Of_Year.with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY)).plusWeeks(i - 1);

            if (saturday.isAfter(LocalDate.now()))
                generateFutureLesson(saturday.format(Utility.formatter), String.valueOf(Day.SATURDAY));
        }
    }

    // Method to generate lesson for specific day and available seat
    public void generateFutureLesson(String date, String day) {
        String[] week_Day = new String[]{"4", "5", "6"};
        String[] week_End = new String[]{"2", "3"};
        int length = day.equals("SATURDAY") ? week_End.length : week_Day.length;
        for (int j = 0; j < length; j++) {
            String lesson_Id = null;
            for (String[] strings : lessons_Of_The_Week) {
                if (strings[1].equals(day) && (length == 2 ? strings[2].equals(week_End[j]) : strings[2].equals(week_Day[j]))) {
                    lesson_Id = strings[0];
                }
            }
            assert lesson_Id != null;
            long remaining_Seat = 4 - countBookingsForDateAndStatus(LocalDate.parse(date, Utility.formatter), Integer.parseInt(lesson_Id));
            for (int i = 0; i < remaining_Seat; i++) {
                String[] columns = new String[7];
                columns[0] = null;
                columns[1] = date;
                columns[2] = lesson_Id;
                columns[3] = null;
                columns[4] = null;
                columns[5] = null;
                Lesson lesson = getLesson(columns);
                lesson_List.add(lesson);
            }
        }
    }

    // Method to check how many place left for a lesson
    public long countBookingsForDateAndStatus(LocalDate booked_Date, int lesson_Of_The_Week) {
        return lesson_List.stream()
                .filter(lesson ->
                        lesson.getBooking_Date().equals(booked_Date) &&
                                lesson.getLesson_Of_The_Week() == lesson_Of_The_Week &&
                                lesson.getStatus().equals(String.valueOf(Status.Booked)))
                .count();
    }

    // Method to generate monthly learner report
    public void monthlyLearnerReport() {
        int month_Number = Utility.readIntegerInput("Please enter a month number");
        if ((month_Number >= 1) && (month_Number <= 12)) {
            if (lesson_List.stream().noneMatch(lesson_List -> lesson_List.getBooking_Date().getMonthValue() == month_Number && lesson_List.getLearner_Id() != 0)) {
                System.out.println("No lesson attended in this month.\n");
            } else {
                for (Learner learner : LearnerMain.learner_List) {
                    if (lesson_List.stream().noneMatch(lesson_List -> lesson_List.getBooking_Date().getMonthValue() == month_Number
                            && lesson_List.getLearner_Id() == learner.getLearner_Id())) continue;
                    else displayLearnerLessonDetails(learner.getLearner_Id(), month_Number, learner.getName());
                }
            }
        } else {
            System.out.println("Invalid month.\n");
        }
    }

    // Method to display each learner lesson and respective status and statics per month
    public void displayLearnerLessonDetails(int learner_Id, int month_Number, String learner_Name) {
        System.out.printf("%-20s  %-20s  %-20s  %-20s  %-20s  %-20s\n", "Leaner_Id", "Learner_Name", "Status", "Time", "Grade", "Coach");
        int total_Booked_Lesson = 0;
        int total_Attended_Lesson = 0;
        int total_Cancelled_Lesson = 0;
        for (Lesson lesson : lesson_List) {
            if (lesson.getLearner_Id() == learner_Id && (lesson.getBooking_Date().getMonthValue() == month_Number)) {
                if (lesson.getStatus().equals(String.valueOf(Status.Booked))) total_Booked_Lesson += 1;
                if (lesson.getStatus().equals(String.valueOf(Status.Attended))) total_Attended_Lesson += 1;
                if (lesson.getStatus().equals(String.valueOf(Status.Cancelled))) total_Cancelled_Lesson += 1;
                int week_Lesson = lesson.getLesson_Of_The_Week();
                int time = Integer.parseInt(lessons_Of_The_Week[week_Lesson - 1][2]);
                int grade = Integer.parseInt(lessons_Of_The_Week[week_Lesson - 1][3]);
                String coach = lessons_Of_The_Week[week_Lesson - 1][4];
                System.out.printf("%-20s  %-20s  %-20s  %-20s  %-20s  %-20s\n", learner_Id, learner_Name, lesson.getStatus(), time + " to " + (time + 1), grade, coach);
            }
        }
        System.out.println("\nTotal booked lesson : " + total_Booked_Lesson);
        System.out.println("Total attended lesson : " + total_Attended_Lesson);
        System.out.println("Total cancelled lesson : " + total_Cancelled_Lesson);
        System.out.println();
    }

    // Method to display monthly coach report
    public void monthlyCoachReport() {
        int month_Number = Utility.readIntegerInput("Please enter a month number");
        if ((month_Number >= 1) && (month_Number <= 12)) {
            if (lesson_List.stream().noneMatch(lesson_List -> lesson_List.getBooking_Date().getMonthValue() == month_Number && lesson_List.getLearner_Id() != 0)) {
                System.out.println("No lesson attended in this month.\n");
            } else {
                String[] existing_Coach = {"LUNA", "JAME", "CLOE"};
                System.out.printf("%-20s  %-20s\n", "Coach", "Average_Rating");
                for (String existingCoach : existing_Coach) {
                    displayCoachRating(existingCoach);
                }
            }
        } else {
            System.out.println("Invalid month.\n");
        }
    }

    // Method to display each coach rating per month
    public void displayCoachRating(String coach_Name) {
        int rating = 0;
        int count = 0;
        for (Lesson lesson : lesson_List) {
            int week_Lesson_Id = lesson.getLesson_Of_The_Week();
            if (lessons_Of_The_Week[week_Lesson_Id - 1][4].equals(coach_Name)) {
                count++;
                rating += lesson.getRating();
            }
        }
        System.out.printf("%-20s  %-20s\n", coach_Name, (rating / count));
    }

    // Method to display all lesson
    public void showAllLesson() {
        int index = 0;
        System.out.printf("%-20s   %-20s   %-20s   %-20s   %-20s   %-20s   %-20s%n", "lesson_Number", "learner_Id", "booking_Date", "lesson_Of_The_Week", "status", "rating", "review");
        while (index < lesson_List.size()) {
            Lesson temp = lesson_List.get(index);
            System.out.println(temp.displayFormat());
            index++;
        }
    }

    // Method to check if a given day exists in the enum
    public boolean isDay(String day) {
        boolean is_Enum_Value = false;
        for (Day days : Day.values()) {
            if (days.name().equals(day)) {
                is_Enum_Value = true;
                break;
            }
        }
        return is_Enum_Value;
    }

    // Method to check if learner exist or not
    public int checkValidLearnerId() {
        int learner_id = Utility.readIntegerInput("Enter your learner_Id.");
        if (LearnerMain.learner_List.stream().noneMatch(learner_List -> learner_List.getLearner_Id() == learner_id)) {
            System.out.println(" Invalid learner_Id.");
            return -1;
        } else return learner_id;
    }

    // Method to check if lesson exist or not
    public int checkValidLessonId() {
        int lesson_Id = Utility.readIntegerInput("Enter a lesson_Id");
        if (lesson_List.stream().noneMatch(lesson_List -> lesson_List.getLesson_Id() == lesson_Id)) {
            System.out.println("Invalid lesson_Id.\n");
            return -1;
        } else return lesson_Id;
    }

}
