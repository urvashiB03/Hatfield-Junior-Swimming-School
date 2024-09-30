# Hatfield-Junior-Swimming-School

Project Overview
This project is a booking management system designed for Hatfield Junior Swimming School (HJSS). It allows learners to book swimming lessons, view lesson schedules, and manage their bookings. The school offers lessons across five grades (Grade 1-5), and lessons are conducted four times a week. The system is designed to handle booking, cancellation, lesson attendance tracking, and feedback collection for each learner.

Features:
View Timetable:
By Day: Displays all available lessons on a specific day.
By Grade Level: Displays all lessons for a particular grade.
By Coach: Displays all lessons taught by a specific coach.

Book a Lesson:
Learners can book lessons at their current grade level or one level higher. 
The system tracks available slots for each lesson (maximum 4 learners per lesson). 

Change or Cancel Bookings:
Learners can change their booked lessons if space is available.
Learners can cancel a lesson before attending.

Write a Review:
After attending a lesson, learners can leave a review with a rating (1-5).

Coach Features:
Coach Ratings: Each coach has an average rating based on learner reviews.

Admin Features:
Add New Learners: Admins can add new learners into the system.

Generate Reports:
Learner Report: Lists all lessons that a learner has booked, canceled, or attended within a 4-week period.
Coach Report: Lists all coaches and their respective average ratings.

Constraints:
A learner can book lessons at their current grade or one level higher.
No duplicate bookings are allowed.
Lessons run on Monday, Wednesday, Friday, and Saturday with specific time slots.

Timetable Design: The system includes a 4-week timetable with 44 lessons, covering all grades (1-5) and various coaches. The timetable repeats weekly and ensures that learners can book lessons across different days and times.

Weekly Schedule:
Monday, Wednesday, Friday: Three time slots (4-5pm, 5-6pm, 6-7pm).
Saturday: Two time slots (2-3pm, 3-4pm).

Coaches and Learners
There are 3-4 coaches pre-registered in the system.
Around 15 learners are also pre-registered.

Technologies Used
Java: The project is written entirely in Java.
Data Storage: The project is self-contained and does not rely on any external databases. Data is managed internally.
Reports: Reports are printed to the console.
