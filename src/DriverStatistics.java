
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
     * Creates a DriverStatistics object containing the
     * performance statistics for a Formula 1 driver.
     *
     * Stores the driver's name, total races, wins, podium
     * finishes, championship points, and average finishing position.
     *
     * @param name the name of the Formula 1 driver
     * @param races the total number of races completed
     * @param wins the total number of race victories
     * @param podiums the total number of podium finishes
     * @param points the total championship points earned
     * @param avg the driver's average finishing position
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


    /**
     * Returns the name of the Formula 1 driver.
     *
     * @return the driver's name
     */
    public String getDriverName() {
        return driverName;
    }


    /**
     * Returns the total number of races recorded for
     * the Formula 1 driver.
     *
     * @return the total number of races
     */
    public int getTotalRaces() {
        return totalRaces;
    }


    /**
     * Returns the total number of race victories
     * recorded for the Formula 1 driver.
     *
     * @return the total number of race wins
     */
    public int getWins() {
        return wins;
    }


    /**
     * Returns the total number of podium finishes
     * recorded for the Formula 1 driver.
     *
     * @return the total number of podium finishes
     */
    public int getPodiums() {
        return podiums;
    }


    /**
     * Returns the total championship points earned
     * by the Formula 1 driver.
     *
     * @return the total number of championship points
     */
    public int getTotalPoints() {
        return totalPoints;
    }


    /**
     * Returns the average finishing position of the
     * Formula 1 driver across their recorded races.
     *
     * @return the driver's average finishing position
     */
    public double getAverageFinish() {
        return averageFinish;
    }
}
