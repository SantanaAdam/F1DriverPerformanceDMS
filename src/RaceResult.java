
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
     * Creates a new Formula 1 race result.
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

    // Returns the unique race result ID
    public int getResultId() {
        return resultId;
    }

    // Returns the driver's name
    public String getDriverName() {
        return driverName;
    }

    // Returns the finishing position
    public int getFinishingPosition() {
        return finishingPosition;
    }

    // Returns the championship points
    public int getChampionshipPoints() {
        return championshipPoints;
    }

    // Returns whether the driver did not finish
    public boolean isDnf() {
        return dnf;
    }
    // Returns the team name
    public String getTeamName() {
        return teamName;
    }

    // Returns the Grand Prix name
    public String getGrandPrix() {
        return grandPrix;
    }

    // Returns the race date
    public LocalDate getRaceDate() {
        return raceDate;
    }

    // Returns the starting position
    public int getStartingPosition() {
        return startingPosition;
    }

    // Returns the fastest lap time
    public double getFastestLapTime() {
        return fastestLapTime;
    }

}
