
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
     * Creates a new F1 Driver Performance Management System application.
     *
     * Initializes the ResultManager, ResultValidator, and
     * RaceResultFileReader objects used to manage, validate,
     * and load Formula 1 race results.
     */

    public DmsApplication() {
        manager = new ResultManager();
        validator = new ResultValidator();
        fileReader = new RaceResultFileReader();
    }


    /**
     * Loads Formula 1 race results from a specified text file.
     *
     * Reads the race results from the file and attempts to add
     * each record to the management system.
     *
     * @param path the path to the text file containing race results
     * @return the number of race results successfully added
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


    /**
     * Creates a new Formula 1 race result in the management system.
     *
     * Validates the race result before attempting to add it
     * to the collection of stored race results.
     *
     * @param result the RaceResult object containing the new race data
     * @return true if the race result is valid and successfully added,
     *         false otherwise
     */

    public boolean createResult(RaceResult result) {

        if (!validator.validate(result)) {
            return false;
        }

        return manager.addResult(result);
    }


    /**
     * Updates an existing Formula 1 race result in the management system.
     *
     * Validates the updated race information before attempting
     * to modify the existing record using its unique result ID.
     *
     * @param id the unique ID of the race result to update
     * @param result the RaceResult object containing the updated race data
     * @return true if the race result is valid and successfully updated,
     *         false otherwise
     */
    public boolean updateResult(int id, RaceResult result) {

        if (!validator.validate(result)) {
            return false;
        }

        return manager.updateResult(id, result);
    }


    /**
     * Removes an existing Formula 1 race result from the management system.
     *
     * Uses the unique result ID to locate and delete
     * the corresponding race record.
     *
     * @param id the unique ID of the race result to remove
     * @return true if the race result was successfully removed,
     *         false if no matching record was found
     */
    public boolean removeResult(int id) {
        return manager.deleteResult(id);
    }


    /**
     * Retrieves all Formula 1 race results currently
     * stored in the management system.
     *
     * @return a list containing all stored RaceResult objects
     */

    public List<RaceResult> listResults() {
        return manager.getAllResults();
    }



    /**
     * Calculates performance statistics for a selected Formula 1 driver.
     *
     * Retrieves the driver's race results and calculates total races,
     * wins, podium finishes, championship points, and average
     * finishing position.
     *
     * @param name the name of the Formula 1 driver
     * @return a DriverStatistics object containing the driver's
     *         calculated performance statistics
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
