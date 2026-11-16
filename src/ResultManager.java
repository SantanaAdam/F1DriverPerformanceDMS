
import java.util.ArrayList;
import java.util.List;

/**
 * Manages Formula 1 race results.
 * Handles adding, finding, updating, and deleting records.
 */
public class ResultManager {

    // Stores all race results in memory
    private List<RaceResult> results;

    /**
     * Creates an empty list of race results.
     */
    public ResultManager() {
        results = new ArrayList<>();
    }


    /**
     * Adds a new Formula 1 race result to the in-memory list.
     * Prevents duplicate result IDs and rejects null entries.
     *
     * @param result the race result to add
     * @return true if the result was added successfully,
     *         false if the result is null or its ID already exists
     */

    public boolean addResult(RaceResult result) {
        if (result == null ||
                findById(result.getResultId()) != null) {
            return false;
        }

        return results.add(result);
    }


    /**
     * Searches the stored race results for a matching result ID.
     *
     * @param id the unique identification number to search for
     * @return the matching RaceResult object, or null if no result is found
     */

    public RaceResult findById(int id) {
        for (RaceResult result : results) {
            if (result.getResultId() == id) {
                return result;
            }
        }

        return null;
    }


    /**
     * Retrieves a copy of all Formula 1 race results stored in memory.
     * Changes to the returned list do not modify the original list.
     *
     * @return a new list containing all stored race results
     */

    public List<RaceResult> getAllResults() {
        return new ArrayList<>(results);
    }


    /**
     * Updates an existing Formula 1 race result using its unique ID.
     * The replacement must not be null and must have the same ID.
     *
     * @param id the unique ID of the race result to update
     * @param replacement the new race result containing updated information
     * @return true if the result was updated successfully,
     *         false if the result was not found or the replacement is invalid
     */

    public boolean updateResult(int id, RaceResult replacement) {
        if (replacement == null ||
                replacement.getResultId() != id) {
            return false;
        }

        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getResultId() == id) {
                results.set(i, replacement);
                return true;
            }
        }

        return false;
    }


    /**
     * Deletes a Formula 1 race result from the in-memory list
     * using its unique identification number.
     *
     * @param id the unique ID of the race result to delete
     * @return true if the result was deleted successfully,
     *         false if no matching result exists
     */

    public boolean deleteResult(int id) {
        RaceResult result = findById(id);

        if (result == null) {
            return false;
        }

        return results.remove(result);
    }


    /**
     * Retrieves race results associated with a specified Formula 1 driver.
     *
     * @param name the name of the driver to search for
     * @return a list of matching race results, or an empty list
     *         if the driver is not found or the name is null
     */

    public List<RaceResult> getResultsByDriver(String name) {
        List<RaceResult> driverResults = new ArrayList<>();

        if (name == null) {
            return driverResults;
        }

        for (RaceResult result : results) {
            if (result.getDriverName().equalsIgnoreCase(name.trim())) {
                driverResults.add(result);
            }
        }

        return driverResults;
    }
}
