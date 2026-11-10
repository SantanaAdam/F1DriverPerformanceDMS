
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;

// Handles connecting the F1 DMS application to a SQLite database.
public class DatabaseConnection {

    private Connection connection;

    // Connects to the database file selected by the user.
    public boolean connect(String databasePath) {

        // Reject blank or missing paths.
        if (databasePath == null || databasePath.trim().isEmpty()) {
            return false;
        }

        File databaseFile = new File(databasePath);

        // Only connect to an existing database file.
        if (!databaseFile.isFile()) {
            return false;
        }

        try {
            // Close an existing connection before opening another.
            disconnect();

            String url = "jdbc:sqlite:" + databaseFile.getAbsolutePath();

            connection = DriverManager.getConnection(url);

            return true;

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            return false;
        }
    }

    // Provides the active connection to other database classes.
    public Connection getConnection() {
        return connection;
    }

    // Closes the database connection safely.
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing database: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}
