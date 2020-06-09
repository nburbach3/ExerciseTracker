package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//This class is to connect to the local server and database using JDBC
public class MySqlCon {

    public static Connection getConnection() {
        String connectionUrl =
                "jdbc:sqlserver://localhost:1433;"
                        + "databaseName=DATABASENAME;"
                        + "user=USERNAME;"
                        + "password=PASSWORD;";

        try {
            return DriverManager.getConnection(connectionUrl);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}