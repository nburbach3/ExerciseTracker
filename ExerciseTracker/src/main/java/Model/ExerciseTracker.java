package Model;

import Controller.InputExercisesController;

import java.sql.*;
import java.util.ArrayList;

//All SQL queries will be run through this class
public class ExerciseTracker {

    //Adds an exercise to the database
    public static void addExercise(String exercise, String reps, String weight, String sets, Date date) throws SQLException {

        int repsInt = 0;
        double weightDouble = 0;
        int setsInt = 0;
        String dateString = "";

        try {
            repsInt = Integer.parseInt(reps);
            weightDouble = Double.parseDouble(weight);
            setsInt = Integer.parseInt(sets);
            dateString = date.toString();

            String values = String.format("VALUES('%s','%d','%f','%d', '%s')", exercise, repsInt,
                    weightDouble, setsInt, dateString);
            String query = "INSERT INTO ExerciseLogs(ExerciseName, Reps, Weight, Sets, Date) " + values;
            Connection connection = null;
            Statement statement = null;
            try {
                connection = MySqlCon.getConnection();
                assert connection != null;
                statement = connection.createStatement();
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                assert statement != null;
                statement.close();
                connection.close();
            }
            InputExercisesController.displayPopUp();
        } catch (NumberFormatException e) {
            InputExercisesController.displayError();
        }
    }

    //Clears the database
    public static void clearDatabase() throws SQLException {
        String query = "DELETE FROM ExerciseLogs";
        Connection connection = null;
        Statement statement = null;
        try {
            connection = MySqlCon.getConnection();
            assert connection != null;
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert statement != null;
            statement.close();
            connection.close();
        }
    }

    //Gets all dates for the current year
    public static ArrayList<String> getDateInfo(String exercise) throws SQLException {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        String[] dateParts = date.toString().split("-");
        String year = dateParts[0];
        String query = "SELECT Date FROM ExerciseLogs WHERE ExerciseName = '" + exercise + "' AND Date LIKE '%" + year + "%'";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        ArrayList<String> dateList = new ArrayList<>();
        try {
            connection = MySqlCon.getConnection();
            assert connection != null;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                dateList.add(resultSet.getString("Date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert statement != null;
            statement.close();
            connection.close();
        }
        return dateList;
    }

    //Gets all reps for the current year
    public static ArrayList<Integer> getRepsInfo(String exercise) throws SQLException {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        String[] dateParts = date.toString().split("-");
        String year = dateParts[0];
        String query = "SELECT Reps FROM ExerciseLogs WHERE ExerciseName = '" + exercise + "' AND Date LIKE '%" + year + "%'";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        ArrayList<Integer> repsList = new ArrayList<>();
        try {
            connection = MySqlCon.getConnection();
            assert connection != null;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                repsList.add(resultSet.getInt("Reps"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert statement != null;
            statement.close();
            connection.close();
        }
        return repsList;
    }

    //Gets all weights for the current year
    public static ArrayList<Double> getWeightInfo(String exercise) throws SQLException {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        String[] dateParts = date.toString().split("-");
        String year = dateParts[0];
        String query = "SELECT Weight FROM ExerciseLogs WHERE ExerciseName = '" + exercise + "' AND Date LIKE '%" + year + "%'";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        ArrayList<Double> weightList = new ArrayList<>();
        try {
            connection = MySqlCon.getConnection();
            assert connection != null;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                weightList.add(resultSet.getDouble("Weight"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert statement != null;
            statement.close();
            connection.close();
        }
        return weightList;
    }

    //Gets all sets for the current year
    public static ArrayList<Integer> getSetsInfo(String exercise) throws SQLException {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        String[] dateParts = date.toString().split("-");
        String year = dateParts[0];
        String query = "SELECT Sets FROM ExerciseLogs WHERE ExerciseName = '" + exercise + "' AND Date LIKE '%" + year + "%'";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        ArrayList<Integer> setsList = new ArrayList<>();
        try {
            connection = MySqlCon.getConnection();
            assert connection != null;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                setsList.add(resultSet.getInt("Sets"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert statement != null;
            statement.close();
            connection.close();
        }
        return setsList;
    }

    //Gets all dates for the current month
    public static ArrayList<String> getMonthDateInfo(String exercise) throws SQLException {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        String[] dateParts = date.toString().split("-");
        String yearMonth = dateParts[0] + "-" + dateParts[1];
        String query = "SELECT Date FROM ExerciseLogs WHERE ExerciseName = '" + exercise + "' AND Date LIKE '%" + yearMonth + "%'" ;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        ArrayList<String> dateList = new ArrayList<>();
        try {
            connection = MySqlCon.getConnection();
            assert connection != null;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                dateList.add(resultSet.getString("Date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert statement != null;
            statement.close();
            connection.close();
        }
        return dateList;
    }

    //Gets all weights for the current month
    public static ArrayList<Double> getMonthWeightInfo(String exercise) throws SQLException {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        String[] dateParts = date.toString().split("-");
        String yearMonth = dateParts[0] + "-" + dateParts[1];
        String query = "SELECT Weight FROM ExerciseLogs WHERE ExerciseName = '" + exercise + "' AND Date LIKE '%" + yearMonth + "%'" ;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        ArrayList<Double> weightList = new ArrayList<>();
        try {
            connection = MySqlCon.getConnection();
            assert connection != null;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                weightList.add(resultSet.getDouble("Weight"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert statement != null;
            statement.close();
            connection.close();
        }
        return weightList;
    }

    //Gets all reps for the current month
    public static ArrayList<Integer> getMonthRepsInfo(String exercise) throws SQLException {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        String[] dateParts = date.toString().split("-");
        String yearMonth = dateParts[0] + "-" + dateParts[1];
        String query = "SELECT Reps FROM ExerciseLogs WHERE ExerciseName = '" + exercise + "' AND Date LIKE '%" + yearMonth + "%'" ;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        ArrayList<Integer> repsList = new ArrayList<>();
        try {
            connection = MySqlCon.getConnection();
            assert connection != null;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                repsList.add(resultSet.getInt("Reps"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert statement != null;
            statement.close();
            connection.close();
        }
        return repsList;
    }

    //Gets all sets for the current month
    public static ArrayList<Integer> getMonthSetsInfo(String exercise) throws SQLException {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        String[] dateParts = date.toString().split("-");
        String yearMonth = dateParts[0] + "-" + dateParts[1];
        String query = "SELECT Sets FROM ExerciseLogs WHERE ExerciseName = '" + exercise + "' AND Date LIKE '%" + yearMonth + "%'" ;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        ArrayList<Integer> setsList = new ArrayList<>();
        try {
            connection = MySqlCon.getConnection();
            assert connection != null;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                setsList.add(resultSet.getInt("Sets"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert statement != null;
            statement.close();
            connection.close();
        }
        return setsList;
    }
}
