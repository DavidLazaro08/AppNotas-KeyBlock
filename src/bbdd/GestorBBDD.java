package bbdd;

import java.sql.*;

public class GestorBBDD {
    private static final String URL = "jdbc:mysql://localhost:3306/notelab";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    public static void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }

    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static ResultSet executeSelect(String sql) throws SQLException {
        connect();
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    public static int executeUpdate(String sql) throws SQLException {
        connect();
        Statement stmt = connection.createStatement();
        return stmt.executeUpdate(sql);
    }

    public static Connection getConnection() throws SQLException {
        connect();
        return connection;
    }
}
