# ExerciseTracker

Setup Instructions:
1. Setup SQL Server on your computer (I also use Azure Data Studio for a GUI)
2. Run this SQL script to setup database:
    
CREATE DATABASE ExerciseTracker;

CREATE TABLE Exerciselogs (
    ExerciseName VARCHAR(255) NOT NULL,
    Reps INT NOT NULL,
    Weight FLOAT NOT NULL,
    Sets INT NOT NULL,
    Date VARCHAR(255) NOT NULL
);

3. Clone repository and import project into IDE
4. Copy MySqlCon-TEMPLATE to Model folder in project, replace USERNAME and PASSWORD with your SQL Server details
5. Make sure you have the correct external libraries:
   JDK 11.0.5
   mssql-jdbc-8.2.2.jre11
   

Run Instructions:
1. Build project
2. Run Main
3. Clear database if necessary by clicking the clear database button
4. Click the Enter Exercise Info button
5. Enter data for each exercise you complete today
6. Click the Go Back button
7. Click the View Yearly Statistics or the View Monthly Statistics button
8. Select exercise you want to see progress on
9. Click the Submit button

Currently you are able to see data based on the current month or current year.
The data displayed is created by a formula based on the weight lifted, number of reps, and the number of sets performed.
