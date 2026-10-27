import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the race result management features
 * of the F1 Driver Performance DMS.
 */
public class ResultManagerTest {

    // Checks that a new manager starts with no records
    @Test
    public void testManagerStartsEmpty() {

        ResultManager manager = new ResultManager();

        assertTrue(manager.getAllResults().isEmpty());
    }

    // Checks that a valid race result can be added
    @Test
    public void testAddValidResult() {

        ResultManager manager = new ResultManager();

        RaceResult result = new RaceResult(
                101,
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

        boolean added = manager.addResult(result);

        assertTrue(added);
        assertEquals(1, manager.getAllResults().size());
        assertNotNull(manager.findById(101));
    }

    // Checks that duplicate IDs are rejected
    @Test
    public void testDuplicateResultId() {

        ResultManager manager = new ResultManager();

        RaceResult firstResult = new RaceResult(
                101,
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

        RaceResult duplicateResult = new RaceResult(
                101,
                "Lewis Hamilton",
                "Ferrari",
                "Monaco Grand Prix",
                LocalDate.of(2025, 5, 25),
                5,
                2,
                18,
                92.3,
                false
        );

        assertTrue(manager.addResult(firstResult));
        assertFalse(manager.addResult(duplicateResult));

        assertEquals(1, manager.getAllResults().size());
    }

    // Checks that a null race result cannot be added
    @Test
    public void testAddNullResult() {

        ResultManager manager = new ResultManager();

        assertFalse(manager.addResult(null));
        assertTrue(manager.getAllResults().isEmpty());
    }    // Checks that an existing race result can be deleted
    @Test
    public void testDeleteExistingResult() {

        ResultManager manager = new ResultManager();

        RaceResult result = new RaceResult(
                102,
                "Lewis Hamilton",
                "Ferrari",
                "Monaco Grand Prix",
                LocalDate.of(2025, 5, 25),
                4,
                2,
                18,
                92.3,
                false
        );

        manager.addResult(result);

        boolean deleted = manager.deleteResult(102);

        assertTrue(deleted);
        assertNull(manager.findById(102));
        assertEquals(0, manager.getAllResults().size());
    }

    // Checks that deleting an unknown ID is rejected
    @Test
    public void testDeleteNonexistentResult() {

        ResultManager manager = new ResultManager();

        RaceResult result = new RaceResult(
                103,
                "Charles Leclerc",
                "Ferrari",
                "Italian Grand Prix",
                LocalDate.of(2025, 9, 7),
                2,
                3,
                15,
                91.8,
                false
        );

        manager.addResult(result);

        assertFalse(manager.deleteResult(999));
        assertNotNull(manager.findById(103));
        assertEquals(1, manager.getAllResults().size());
    }

    // Checks that deleting from an empty manager does not crash
    @Test
    public void testDeleteFromEmptyManager() {

        ResultManager manager = new ResultManager();

        assertFalse(manager.deleteResult(500));
        assertTrue(manager.getAllResults().isEmpty());
    }    // Tests updating an existing race result
    @Test
    public void testUpdateExistingResult() {

        ResultManager manager = new ResultManager();

        RaceResult original = new RaceResult(
                104,
                "Max Verstappen",
                "Red Bull",
                "Miami Grand Prix",
                LocalDate.of(2025, 5, 4),
                3,
                2,
                18,
                90.5,
                false
        );

        manager.addResult(original);

        // Change the finishing position from 2 to 1
        RaceResult updated = new RaceResult(
                104,
                "Max Verstappen",
                "Red Bull",
                "Miami Grand Prix",
                LocalDate.of(2025, 5, 4),
                3,
                1,
                18,
                90.5,
                false
        );

        boolean success = manager.updateResult(104, updated);

        assertTrue(success);
        assertEquals(1, manager.findById(104).getFinishingPosition());

        // Verify the other information stayed the same
        assertEquals("Max Verstappen",
                manager.findById(104).getDriverName());

        assertEquals("Red Bull",
                manager.findById(104).getTeamName());

        assertEquals(18,
                manager.findById(104).getChampionshipPoints());

        assertEquals(1, manager.getAllResults().size());
    }

    // Tests updating a record that does not exist
    @Test
    public void testUpdateNonexistentResult() {

        ResultManager manager = new ResultManager();

        RaceResult replacement = new RaceResult(
                999,
                "Lewis Hamilton",
                "Ferrari",
                "Monaco Grand Prix",
                LocalDate.of(2025, 5, 25),
                4,
                2,
                18,
                92.3,
                false
        );

        boolean success = manager.updateResult(999, replacement);

        assertFalse(success);
        assertTrue(manager.getAllResults().isEmpty());
    }

    // Tests rejecting an update with a different Result ID
    @Test
    public void testUpdateWithDifferentId() {

        ResultManager manager = new ResultManager();

        RaceResult original = new RaceResult(
                105,
                "Charles Leclerc",
                "Ferrari",
                "Italian Grand Prix",
                LocalDate.of(2025, 9, 7),
                2,
                3,
                15,
                91.8,
                false
        );

        manager.addResult(original);

        // Replacement has ID 999 instead of 105
        RaceResult replacement = new RaceResult(
                999,
                "Charles Leclerc",
                "Ferrari",
                "Italian Grand Prix",
                LocalDate.of(2025, 9, 7),
                2,
                1,
                25,
                91.8,
                false
        );

        boolean success = manager.updateResult(105, replacement);

        assertFalse(success);

        // Verify the original record was not changed
        assertEquals(3, manager.findById(105).getFinishingPosition());
        assertEquals(1, manager.getAllResults().size());
    }
}