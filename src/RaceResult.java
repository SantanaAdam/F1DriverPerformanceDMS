
import java.time.LocalDate;

/**
 * Stores information about a driver's performance
 * in a Formula 1 Grand Prix.
 */
public class RaceResult {

    // Information stored for each race result
    private int resultId;
    private String driverName;
    private String teamName;
    private String grandPrix;
    private LocalDate raceDate;
    private int startingPosition;
    private int finishingPosition;
    private int championshipPoints;
    private double fastestLapTime;
    private boolean dnf;


    /**
     * Creates a new Formula 1 race result containing driver
     * information, race details, and performance statistics.
     *
     * @param id the unique identification number for the race result
     * @param driver the name of the Formula 1 driver
     * @param team the name of the driver's racing team
     * @param grandPrix the name of the Grand Prix event
     * @param date the date when the race took place
     * @param start the driver's starting position in the race
     * @param finish the driver's finishing position in the race
     * @param points the championship points earned by the driver
     * @param lap the driver's fastest lap time in seconds
     * @param dnf indicates whether the driver did not finish the race
     */

    public RaceResult(int id, String driver, String team,
                      String grandPrix, LocalDate date,
                      int start, int finish, int points,
                      double lap, boolean dnf) {

        this.resultId = id;
        this.driverName = driver;
        this.teamName = team;
        this.grandPrix = grandPrix;
        this.raceDate = date;
        this.startingPosition = start;
        this.finishingPosition = finish;
        this.championshipPoints = points;
        this.fastestLapTime = lap;
        this.dnf = dnf;
    }


    /**
     * Gets the unique identification number of the race result.
     *
     * @return the unique race result ID
     */

    public int getResultId() {
        return resultId;
    }


    /**
     * Gets the name of the Formula 1 driver.
     *
     * @return the driver's name
     */

    public String getDriverName() {
        return driverName;
    }


    /**
     * Gets the driver's finishing position in the race.
     *
     * @return the finishing position
     */

    public int getFinishingPosition() {
        return finishingPosition;
    }


    /**
     * Gets the championship points earned by the driver.
     *
     * @return the number of championship points earned
     */

    public int getChampionshipPoints() {
        return championshipPoints;
    }


    /**
     * Checks whether the driver did not finish the race.
     *
     * @return true if the driver did not finish, false otherwise
     */

    public boolean isDnf() {
        return dnf;
    }

    /**
     * Gets the name of the Formula 1 racing team.
     *
     * @return the driver's team name
     */

    public String getTeamName() {
        return teamName;
    }


    /**
     * Gets the name of the Grand Prix event.
     *
     * @return the name of the Grand Prix
     */

    public String getGrandPrix() {
        return grandPrix;
    }


    /**
     * Gets the date when the Formula 1 race took place.
     *
     * @return the race date as a LocalDate object
     */

    public LocalDate getRaceDate() {
        return raceDate;
    }


    /**
     * Gets the driver's starting position in the Formula 1 race.
     *
     * @return the driver's starting grid position
     */

    public int getStartingPosition() {
        return startingPosition;
    }


    /**
     * Gets the driver's fastest recorded lap time during the race.
     *
     * @return the fastest lap time in seconds
     */

    public double getFastestLapTime() {
        return fastestLapTime;
    }

}
