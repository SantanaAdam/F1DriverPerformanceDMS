
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages Formula 1 race result records stored in a SQLite database.
 *
 * Provides database operations for retrieving, adding, updating,
 * and deleting race results using SQL prepared statements.
 *
 * Uses the DatabaseConnection class to access the SQLite database
 * and converts database records into RaceResult objects.
 */public class RaceResultDatabase {

    private final DatabaseConnection databaseConnection;

    /**
     * Creates a RaceResultDatabase object using the provided
     * database connection manager.
     *
     * Stores the DatabaseConnection reference so the class
     * can perform SQLite database operations.
     *
     * @param databaseConnection the database connection manager
     *                           used to access the SQLite database
     */    public RaceResultDatabase(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    /**
     * Retrieves all Formula 1 race results stored in the
     * connected SQLite database.
     *
     * Reads each record from the race_results table and
     * converts it into a RaceResult object.
     *
     * @return a list containing all race results from the database
     * @throws SQLException if a database error occurs while
     *                      retrieving the race results
     */    public List<RaceResult> getAllResults() throws SQLException {

        List<RaceResult> results = new ArrayList<>();

        String sql = "SELECT * FROM race_results ORDER BY result_id";

        Connection connection = databaseConnection.getConnection();

        if (connection == null || connection.isClosed()) {
            throw new SQLException("No active database connection.");
        }

        try (PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                RaceResult result = new RaceResult(
                        resultSet.getInt("result_id"),
                        resultSet.getString("driver_name"),
                        resultSet.getString("team_name"),
                        resultSet.getString("grand_prix"),
                        LocalDate.parse(resultSet.getString("race_date")),
                        resultSet.getInt("starting_position"),
                        resultSet.getInt("finishing_position"),
                        resultSet.getInt("championship_points"),
                        resultSet.getDouble("fastest_lap_time"),
                        resultSet.getInt("dnf") == 1
                );

                results.add(result);
            }
        }

        return results;
    }

    /**
     * Adds a new Formula 1 race result to the SQLite database.
     *
     * Stores the driver's information, race details, finishing
     * position, championship points, fastest lap time, and DNF status.
     *
     * @param result the RaceResult object containing the race data to save
     * @return true if the race result was successfully added,
     *         false otherwise
     * @throws SQLException if an error occurs while saving the result
     */
    public boolean addResult(RaceResult result) throws SQLException {

        String sql = "INSERT INTO race_results (" +
                "result_id, driver_name, team_name, grand_prix, " +
                "race_date, starting_position, finishing_position, " +
                "championship_points, fastest_lap_time, dnf" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = databaseConnection.getConnection();

        if (connection == null || connection.isClosed()) {
            throw new SQLException("No active database connection.");
        }

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, result.getResultId());
            statement.setString(2, result.getDriverName());
            statement.setString(3, result.getTeamName());
            statement.setString(4, result.getGrandPrix());
            statement.setString(5, result.getRaceDate().toString());
            statement.setInt(6, result.getStartingPosition());
            statement.setInt(7, result.getFinishingPosition());
            statement.setInt(8, result.getChampionshipPoints());
            statement.setDouble(9, result.getFastestLapTime());
            statement.setInt(10, result.isDnf() ? 1 : 0);

            return statement.executeUpdate() == 1;
        }
    }

    /**
     * Deletes an existing Formula 1 race result from the SQLite database.
     *
     * Uses the unique result ID to locate and permanently remove
     * the corresponding race record.
     *
     * @param resultId the unique ID of the race result to delete
     * @return true if the race result was successfully deleted,
     *         false if no matching record was found
     * @throws SQLException if an error occurs while deleting the result
     */    public boolean deleteResult(int resultId) throws SQLException {

        String sql = "DELETE FROM race_results WHERE result_id = ?";

        Connection connection = databaseConnection.getConnection();

        if (connection == null || connection.isClosed()) {
            throw new SQLException("No active database connection.");
        }

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, resultId);

            return statement.executeUpdate() == 1;
        }
    }


    /**
     * Updates an existing Formula 1 race result in the SQLite database.
     *
     * Uses the unique result ID to locate the record and update
     * its driver information, race details, positions, championship
     * points, fastest lap time, and DNF status.
     *
     * @param result the RaceResult object containing the updated race data
     * @return true if an existing race result was successfully updated,
     *         false if no matching record was found
     * @throws SQLException if a database error occurs while updating the result
     */

    public boolean updateResult(RaceResult result) throws SQLException {

        String sql = "UPDATE race_results SET " +
                "driver_name = ?, " +
                "team_name = ?, " +
                "grand_prix = ?, " +
                "race_date = ?, " +
                "starting_position = ?, " +
                "finishing_position = ?, " +
                "championship_points = ?, " +
                "fastest_lap_time = ?, " +
                "dnf = ? " +
                "WHERE result_id = ?";

        Connection connection = databaseConnection.getConnection();

        if (connection == null || connection.isClosed()) {
            throw new SQLException("No active database connection.");
        }

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, result.getDriverName());
            statement.setString(2, result.getTeamName());
            statement.setString(3, result.getGrandPrix());
            statement.setString(4, result.getRaceDate().toString());
            statement.setInt(5, result.getStartingPosition());
            statement.setInt(6, result.getFinishingPosition());
            statement.setInt(7, result.getChampionshipPoints());
            statement.setDouble(8, result.getFastestLapTime());
            statement.setInt(9, result.isDnf() ? 1 : 0);
            statement.setInt(10, result.getResultId());

            return statement.executeUpdate() == 1;
        }
    }

}

