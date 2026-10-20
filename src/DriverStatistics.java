
/**
 * Stores calculated statistics for a Formula 1 driver.
 * Includes races, wins, podiums, points, and average finish.
 */
public class DriverStatistics {

    // Statistics for the selected driver
    private String driverName;
    private int totalRaces;
    private int wins;
    private int podiums;
    private int totalPoints;
    private double averageFinish;

    /**
     * Creates a driver statistics object.
     */
    public DriverStatistics(String name, int races, int wins,
                            int podiums, int points, double avg) {

        this.driverName = name;
        this.totalRaces = races;
        this.wins = wins;
        this.podiums = podiums;
        this.totalPoints = points;
        this.averageFinish = avg;
    }

    // Returns the driver's name
    public String getDriverName() {
        return driverName;
    }

    // Returns the number of races
    public int getTotalRaces() {
        return totalRaces;
    }

    // Returns the number of race wins
    public int getWins() {
        return wins;
    }

    // Returns the number of podium finishes
    public int getPodiums() {
        return podiums;
    }

    // Returns the driver's total championship points
    public int getTotalPoints() {
        return totalPoints;
    }

    // Returns the driver's average finishing position
    public double getAverageFinish() {
        return averageFinish;
    }
}
