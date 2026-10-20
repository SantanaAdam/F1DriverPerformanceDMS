
/**
 * Validates information entered into the F1 system.
 * Prevents invalid race results from being stored.
 */
public class ResultValidator {

    // Checks that the result ID is positive
    public boolean isValidId(int id) {
        return id > 0;
    }

    // Checks that a position is between 1 and 20
    public boolean isValidPosition(int position) {
        return position >= 1 && position <= 20;
    }

    // Checks that championship points are valid
    public boolean isValidPoints(int points) {
        return points >= 0 && points <= 26;
    }

    // Checks that lap time is a positive number
    public boolean isValidLapTime(double seconds) {
        return Double.isFinite(seconds) && seconds > 0;
    }

    // Checks that text is not empty
    public boolean isValidName(String value) {
        return value != null
                && !value.trim().isEmpty()
                && !value.contains(",");
    }

    /**
     * Checks every field in a race result.
     * Returns true if all information is valid.
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
