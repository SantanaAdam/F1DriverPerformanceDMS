
import java.util.List;

/**
 * Controls the main operations of the F1 Driver
 * Performance Management System.
 */
public class DmsApplication {

    private ResultManager manager;
    private ResultValidator validator;
    private RaceResultFileReader fileReader;

    /**
     * Creates the application and its required objects.
     */
    public DmsApplication() {
        manager = new ResultManager();
        validator = new ResultValidator();
        fileReader = new RaceResultFileReader();
    }

    /**
     * Loads race results from a text file.
     * Returns the number of successfully added records.
     */
    public int loadFile(String path) {

        List<RaceResult> loadedResults =
                fileReader.readResults(path);

        int count = 0;

        for (RaceResult result : loadedResults) {
            if (createResult(result)) {
                count++;
            }
        }

        return count;
    }

    // Creates a new race result
    public boolean createResult(RaceResult result) {

        if (!validator.validate(result)) {
            return false;
        }

        return manager.addResult(result);
    }

    // Updates an existing race result
    public boolean updateResult(int id, RaceResult result) {

        if (!validator.validate(result)) {
            return false;
        }

        return manager.updateResult(id, result);
    }

    // Removes a race result using its ID
    public boolean removeResult(int id) {
        return manager.deleteResult(id);
    }

    // Returns all race results
    public List<RaceResult> listResults() {
        return manager.getAllResults();
    }

    /**
     * Calculates statistics for a selected F1 driver.
     */
    public DriverStatistics getDriverStatistics(String name) {

        List<RaceResult> driverResults =
                manager.getResultsByDriver(name);

        int races = driverResults.size();
        int wins = 0;
        int podiums = 0;
        int totalPoints = 0;
        int finishCount = 0;
        int finishTotal = 0;

        for (RaceResult result : driverResults) {

            totalPoints += result.getChampionshipPoints();

            if (!result.isDnf()) {

                int position = result.getFinishingPosition();

                if (position == 1) {
                    wins++;
                }

                if (position >= 1 && position <= 3) {
                    podiums++;
                }

                finishTotal += position;
                finishCount++;
            }
        }

        double averageFinish = 0.0;

        if (finishCount > 0) {
            averageFinish =
                    (double) finishTotal / finishCount;
        }

        return new DriverStatistics(
                name, races, wins, podiums,
                totalPoints, averageFinish
        );
    }
}
