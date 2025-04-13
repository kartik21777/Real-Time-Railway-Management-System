package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static final String DB_URL = System.getenv("JDBC_DATABASE_URL");
    private static final String DB_USER = System.getenv("JDBC_DATABASE_USER");
    private static final String DB_PASSWORD = System.getenv("JDBC_DATABASE_PASSWORD");

    public static Connection getConnection() throws SQLException {
        if (DB_URL == null || DB_USER == null || DB_PASSWORD == null) {
            throw new SQLException("Database connection properties not configured (check environment variables).");
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void main(String[] args) {
        try {
            Connection connection = DatabaseUtil.getConnection();
            if (connection != null) {
                System.out.println("Connection Established");
                connection.close(); // Don't forget to close the connection
            } else {
                System.out.println("Connection Not Established");
            }
        } catch (SQLException e) {
            System.out.println("Error establishing connection: " + e.getMessage());
        }
    }
}