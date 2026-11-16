
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;

/**
 * Manages the SQLite database connection for the Formula 1
 * Driver Performance Management System.
 *
 * Provides methods to establish a connection to an existing
 * database, retrieve the active connection, and safely close it.
 */
public class DatabaseConnection {

    private Connection connection;

    /**
     * Establishes a connection to an existing SQLite database file.
     * Checks that the provided path is valid and that the file exists.
     * Closes any previous connection before opening a new one.
     *
     * @param databasePath the path to the SQLite database file
     * @return true if the connection is successful, false otherwise
     */    public boolean connect(String databasePath) {

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

    /**
     * Retrieves the current SQLite database connection.
     * Allows other classes to access the connected database
     * and perform database operations.
     *
     * @return the active database connection, or null if
     *         no database connection has been established
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Safely closes the current SQLite database connection.
     * Checks whether a connection exists before attempting to close it.
     * Handles any SQL exceptions that occur during the process
     * and resets the connection reference to null.
     */
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
