import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the driver statistics calculations
 * in the F1 Driver Performance Management System.
 */
public class DriverStatisticsTest {

    // Tests race count, wins, podiums, points, and average finish
    @Test
    public void testDriverWithMultipleRaces() {

        DmsApplication application = new DmsApplication();

        RaceResult race1 = new RaceResult(
                201,
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

        RaceResult race2 = new RaceResult(
                202,
                "Max Verstappen",
                "Red Bull",
                "Monaco Grand Prix",
                LocalDate.of(2025, 5, 25),
                2,
                2,
                18,
                92.3,
                false
        );

        RaceResult race3 = new RaceResult(
                203,
                "Max Verstappen",
                "Red Bull",
                "Italian Grand Prix",
                LocalDate.of(2025, 9, 7),
                4,
                4,
                12,
                91.8,
                false
        );

        assertTrue(application.createResult(race1));
        assertTrue(application.createResult(race2));
        assertTrue(application.createResult(race3));

        DriverStatistics stats =
                application.getDriverStatistics("Max Verstappen");

        assertEquals("Max Verstappen", stats.getDriverName());
        assertEquals(3, stats.getTotalRaces());
        assertEquals(1, stats.getWins());
        assertEquals(2, stats.getPodiums());
        assertEquals(55, stats.getTotalPoints());

        // Average finishing position: (1 + 2 + 4) / 3
        assertEquals(7.0 / 3.0, stats.getAverageFinish(), 0.001);
    }

    // Tests statistics when a driver has no race records
    @Test
    public void testDriverWithNoResults() {

        DmsApplication application = new DmsApplication();

        DriverStatistics stats =
                application.getDriverStatistics("Unknown Driver");

        assertEquals(0, stats.getTotalRaces());
        assertEquals(0, stats.getWins());
        assertEquals(0, stats.getPodiums());
        assertEquals(0, stats.getTotalPoints());
        assertEquals(0.0, stats.getAverageFinish(), 0.001);
    }

    // Tests that a DNF does not affect average finishing position
    @Test
    public void testDriverWithDnf() {

        DmsApplication application = new DmsApplication();

        RaceResult finishedRace = new RaceResult(
                204,
                "Lewis Hamilton",
                "Ferrari",
                "Miami Grand Prix",
                LocalDate.of(2025, 5, 4),
                3,
                2,
                18,
                90.5,
                false
        );

        RaceResult dnfRace = new RaceResult(
                205,
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

        assertTrue(application.createResult(finishedRace));
        assertTrue(application.createResult(dnfRace));

        DriverStatistics stats =
                application.getDriverStatistics("Lewis Hamilton");

        assertEquals(2, stats.getTotalRaces());
        assertEquals(0, stats.getWins());
        assertEquals(1, stats.getPodiums());
        assertEquals(18, stats.getTotalPoints());

        // Only the completed race counts toward average finish
        assertEquals(2.0, stats.getAverageFinish(), 0.001);
    }

    // Tests that statistics are calculated for the correct driver
    @Test
    public void testDifferentDrivers() {

        DmsApplication application = new DmsApplication();

        RaceResult maxRace = new RaceResult(
                206,
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

        RaceResult lewisRace = new RaceResult(
                207,
                "Lewis Hamilton",
                "Ferrari",
                "Miami Grand Prix",
                LocalDate.of(2025, 5, 4),
                5,
                3,
                15,
                91.2,
                false
        );

        assertTrue(application.createResult(maxRace));
        assertTrue(application.createResult(lewisRace));

        DriverStatistics maxStats =
                application.getDriverStatistics("Max Verstappen");

        DriverStatistics lewisStats =
                application.getDriverStatistics("Lewis Hamilton");

        assertEquals(25, maxStats.getTotalPoints());
        assertEquals(15, lewisStats.getTotalPoints());

        assertEquals(1, maxStats.getWins());
        assertEquals(0, lewisStats.getWins());

        assertEquals(1, maxStats.getTotalRaces());
        assertEquals(1, lewisStats.getTotalRaces());
    }
}