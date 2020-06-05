package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

public class ExerciseTracker {

    public static void addExercise(String exercise, String reps, String weight, Date date) throws SQLException {

        int repsInt = 0;
        double weightDouble = 0;
        String dateString = "";

        if (reps == null || weight == null) {
            //TODO Add error popup that doesn't quit program
            return;
        }
        try {
            repsInt = Integer.parseInt(reps);
            weightDouble = Double.parseDouble(weight);
            dateString = date.toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String values = String.format("VALUES('%s','%d','%f','%s')", exercise, repsInt,
                weightDouble, dateString);
        String query = "INSERT INTO ExerciseLogs(ExerciseName, Reps, Weight, Date) " + values;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = MySqlCon.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statement.close();
            connection.close();
        }
    }
}
