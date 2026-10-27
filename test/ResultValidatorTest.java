import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests input validation for the
 * F1 Driver Performance Management System.
 */
public class ResultValidatorTest {

    // Tests valid and invalid result IDs
    @Test
    public void testResultIdValidation() {

        ResultValidator validator = new ResultValidator();

        assertTrue(validator.isValidId(101));
        assertFalse(validator.isValidId(0));
        assertFalse(validator.isValidId(-5));
    }

    // Tests starting and finishing position limits
    @Test
    public void testPositionValidation() {

        ResultValidator validator = new ResultValidator();

        assertTrue(validator.isValidPosition(1));
        assertTrue(validator.isValidPosition(20));

        assertFalse(validator.isValidPosition(0));
        assertFalse(validator.isValidPosition(21));
        assertFalse(validator.isValidPosition(-1));
    }

    // Tests valid and invalid championship points
    @Test
    public void testPointsValidation() {

        ResultValidator validator = new ResultValidator();

        assertTrue(validator.isValidPoints(0));
        assertTrue(validator.isValidPoints(25));
        assertTrue(validator.isValidPoints(26));

        assertFalse(validator.isValidPoints(-1));
        assertFalse(validator.isValidPoints(27));
    }

    // Tests lap time validation
    @Test
    public void testLapTimeValidation() {

        ResultValidator validator = new ResultValidator();

        assertTrue(validator.isValidLapTime(90.5));

        assertFalse(validator.isValidLapTime(0));
        assertFalse(validator.isValidLapTime(-10.5));
        assertFalse(validator.isValidLapTime(Double.NaN));
        assertFalse(validator.isValidLapTime(
                Double.POSITIVE_INFINITY));
    }

    // Tests empty names and invalid text
    @Test
    public void testNameValidation() {

        ResultValidator validator = new ResultValidator();

        assertTrue(validator.isValidName("Max Verstappen"));

        assertFalse(validator.isValidName(""));
        assertFalse(validator.isValidName("   "));
        assertFalse(validator.isValidName(null));
        assertFalse(validator.isValidName("Max, Verstappen"));
    }

    // Tests that a valid race result is accepted
    @Test
    public void testValidRaceResult() {

        ResultValidator validator = new ResultValidator();

        RaceResult result = new RaceResult(
                301,
                "Max Verstappen",
                "Red Bull",
                "Miami Grand Prix",
                LocalDate.of(2025, 5, 4),
                3,
                1,
                25,
                90.5,
                false
        );

        assertTrue(validator.validate(result));
    }

    // Tests that an invalid race result is rejected
    @Test
    public void testInvalidRaceResult() {

        ResultValidator validator = new ResultValidator();

        RaceResult result = new RaceResult(
                302,
                "",
                "Ferrari",
                "Monaco Grand Prix",
                LocalDate.of(2025, 5, 25),
                4,
                2,
                18,
                92.3,
                false
        );

        assertFalse(validator.validate(result));
    }

    // Tests that a DNF must have finishing position 0
    @Test
    public void testDnfValidation() {

        ResultValidator validator = new ResultValidator();

        RaceResult validDnf = new RaceResult(
                303,
                "Lewis Hamilton",
                "Ferrari",
                "Monaco Grand Prix",
                LocalDate.of(2025, 5, 25),
                5,
                0,
                0,
                92.3,
                true
        );

        RaceResult invalidDnf = new RaceResult(
                304,
                "Lewis Hamilton",
                "Ferrari",
                "Miami Grand Prix",
                LocalDate.of(2025, 5, 4),
                5,
                3,
                15,
                90.5,
                true
        );

        assertTrue(validator.validate(validDnf));
        assertFalse(validator.validate(invalidDnf));
    }

    // Tests that a null race result is rejected
    @Test
    public void testNullRaceResult() {

        ResultValidator validator = new ResultValidator();

        assertFalse(validator.validate(null));
    }
}