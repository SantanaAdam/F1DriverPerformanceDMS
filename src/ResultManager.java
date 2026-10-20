
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

    // Adds a new result if the ID does not exist
    public boolean addResult(RaceResult result) {
        if (result == null ||
                findById(result.getResultId()) != null) {
            return false;
        }

        return results.add(result);
    }

    // Finds a race result using its unique ID
    public RaceResult findById(int id) {
        for (RaceResult result : results) {
            if (result.getResultId() == id) {
                return result;
            }
        }

        return null;
    }

    // Returns a copy of the race result list
    public List<RaceResult> getAllResults() {
        return new ArrayList<>(results);
    }

    // Replaces an existing race result
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

    // Removes a race result using its ID
    public boolean deleteResult(int id) {
        RaceResult result = findById(id);

        if (result == null) {
            return false;
        }

        return results.remove(result);
    }

    // Finds all race results belonging to a driver
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
