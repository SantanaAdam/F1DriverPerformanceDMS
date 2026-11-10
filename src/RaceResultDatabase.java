
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Handles reading F1 race results from the SQLite database.
public class RaceResultDatabase {

    private final DatabaseConnection databaseConnection;

    // Receives the database connection from the application.
    public RaceResultDatabase(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    // Retrieves all race results from the database.
    public List<RaceResult> getAllResults() throws SQLException {

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

    // Save a new race result into the SQLite database
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

    // Delete a race result permanently from SQLite using its ID
    public boolean deleteResult(int resultId) throws SQLException {

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

    // Update an existing race result in the SQLite database
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

