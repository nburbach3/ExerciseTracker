package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlCon {

    public static Connection getConnection() {
        String connectionUrl =
                "jdbc:sqlserver://localhost:1433;"
                        + "databaseName=ExerciseTracker;"
                        + "user=sa;"
                        + "password=Flame7072000NickB;";

        try {
                Connection connection = DriverManager.getConnection(connectionUrl);
                return connection;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}