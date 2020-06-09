package Model;

import Controller.InputExercisesController;

import java.sql.*;
import java.util.ArrayList;

public class ExerciseTracker {

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

    public static ArrayList<Integer> getSetsInfo(String exercise) throws SQLException {
        String query = "SELECT Sets FROM ExerciseLogs WHERE ExerciseName = '" + exercise + "'";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        ArrayList<Integer> setsList = new ArrayList<>();
        try {
            connection = MySqlCon.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                setsList.add(resultSet.getInt("Sets"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statement.close();
            connection.close();
        }
        return setsList;
    }

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
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                setsList.add(resultSet.getInt("Sets"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statement.close();
            connection.close();
        }
        return setsList;
    }
}
