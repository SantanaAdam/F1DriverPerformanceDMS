
/**
 * Validates information entered into the F1 system.
 * Prevents invalid race results from being stored.
 */
public class ResultValidator {


    /**
     * Checks whether a race result ID is a positive number.
     *
     * @param id the race result ID to validate
     * @return true if the ID is greater than zero, false otherwise
     */

    public boolean isValidId(int id) {
        return id > 0;
    }
    /**
     * Checks whether a race position is within the valid range
     * of 1 through 20.
     *
     * @param position the starting or finishing position to validate
     * @return true if the position is between 1 and 20,
     *         false otherwise
     */

    // Checks that a position is between 1 and 20
    public boolean isValidPosition(int position) {
        return position >= 1 && position <= 20;
    }

    /**
     * Checks whether championship points are within the
     * accepted range of 0 through 26.
     *
     * @param points the championship points to validate
     * @return true if the points are between 0 and 26,
     *         false otherwise
     */


    // Checks that championship points are valid
    public boolean isValidPoints(int points) {
        return points >= 0 && points <= 26;
    }


    /**
     * Checks whether a lap time is a positive, finite number.
     *
     * @param seconds the lap time in seconds to validate
     * @return true if the lap time is finite and greater than zero,
     *         false otherwise
     */

    // Checks that lap time is a positive number
    public boolean isValidLapTime(double seconds) {
        return Double.isFinite(seconds) && seconds > 0;
    }


    /**
     * Checks whether a text value is valid for a race result.
     * Rejects null values, blank text, and values containing
     * the pipe character used as a data separator.
     *
     * @param value the text value to validate
     * @return true if the text is valid, false otherwise
     */

    public boolean isValidName(String value) {
        return value != null
                && !value.trim().isEmpty()
                && !value.contains(",");
    }


    /**
     * Validates the information stored in a Formula 1 race result.
     * Checks the result's fields to determine whether the record
     * meets the application's validation requirements.
     *
     * @param result the RaceResult object to validate
     * @return true if the race result passes all validation checks,
     *         false if the result is null or contains invalid data
     */

    public boolean validate(RaceResult result) {

        if (result == null) {
            return false;
        }

        if (!isValidId(result.getResultId())) {
            return false;
        }

        if (!isValidName(result.getDriverName())
                || !isValidName(result.getTeamName())
                || !isValidName(result.getGrandPrix())) {
            return false;
        }

        if (result.getRaceDate() == null) {
            return false;
        }

        if (!isValidPosition(result.getStartingPosition())) {
            return false;
        }

        if (!isValidPoints(result.getChampionshipPoints())) {
            return false;
        }

        if (!isValidLapTime(result.getFastestLapTime())) {
            return false;
        }

        // A DNF uses 0 instead of a finishing position
        if (result.isDnf()) {
            return result.getFinishingPosition() == 0;
        }

        return isValidPosition(result.getFinishingPosition());
    }
}
