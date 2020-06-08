package Model;

import Controller.InputExercisesController;

import java.sql.*;
import java.util.ArrayList;

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
            InputExercisesController.displayPopUp();
        } catch (NumberFormatException e) {
            InputExercisesController.displayError();
        }
    }

    public static void clearDatabase() throws SQLException {
        String query = "DELETE FROM ExerciseLogs";
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

    public static ArrayList<String> getDateInfo(String exercise) throws SQLException {
        String query = "SELECT Date FROM ExerciseLogs WHERE ExerciseName = '" + exercise + "'";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        ArrayList<String> dateList = new ArrayList<>();
        try {
            connection = MySqlCon.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                dateList.add(resultSet.getString("Date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statement.close();
            connection.close();
        }
        return dateList;
    }

    public static ArrayList<Integer> getRepsInfo(String exercise) throws SQLException {
        String query = "SELECT Reps FROM ExerciseLogs WHERE ExerciseName = '" + exercise + "'";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        ArrayList<Integer> repsList = new ArrayList<>();
        try {
            connection = MySqlCon.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                repsList.add(resultSet.getInt("Reps"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statement.close();
            connection.close();
        }
        return repsList;
    }

    public static ArrayList<Double> getWeightInfo(String exercise) throws SQLException {
        String query = "SELECT Weight FROM ExerciseLogs WHERE ExerciseName = '" + exercise + "'";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        ArrayList<Double> weightList = new ArrayList<>();
        try {
            connection = MySqlCon.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                weightList.add(resultSet.getDouble("Weight"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statement.close();
            connection.close();
        }
        return weightList;
    }
}
