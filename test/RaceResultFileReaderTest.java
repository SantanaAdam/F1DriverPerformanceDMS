import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the file loading features of the
 * F1 Driver Performance Management System.
 */
public class RaceResultFileReaderTest {

    @TempDir
    Path tempDir;

    // Tests reading a valid race result from a text file
    @Test
    public void testLoadValidFile() throws IOException {

        Path file = tempDir.resolve("valid_results.txt");

        String data =
                "101,Max Verstappen,Red Bull,Miami Grand Prix," +
                        "2025-05-04,3,1,25,90.5,false";

        Files.write(file, data.getBytes());

        RaceResultFileReader reader = new RaceResultFileReader();

        List<RaceResult> results =
                reader.readResults(file.toString());

        assertEquals(1, results.size());
        assertEquals("Max Verstappen",
                results.get(0).getDriverName());
    }

    // Tests handling a file that does not exist
    @Test
    public void testLoadNonexistentFile() {

        RaceResultFileReader reader = new RaceResultFileReader();

        Path missingFile = tempDir.resolve("missing_results.txt");

        List<RaceResult> results =
                reader.readResults(missingFile.toString());

        assertTrue(results.isEmpty());
    }

    // Tests rejecting an empty file path
    @Test
    public void testLoadBlankFilePath() {

        RaceResultFileReader reader = new RaceResultFileReader();

        List<RaceResult> results = reader.readResults("");

        assertTrue(results.isEmpty());
    }

    // Tests skipping an incorrectly formatted record
    @Test
    public void testLoadInvalidData() throws IOException {

        Path file = tempDir.resolve("invalid_results.txt");

        String data = "This is not a valid F1 race result";

        Files.write(file, data.getBytes());

        RaceResultFileReader reader = new RaceResultFileReader();

        List<RaceResult> results =
                reader.readResults(file.toString());

        assertTrue(results.isEmpty());
    }

    // Tests loading valid records while skipping invalid ones
    @Test
    public void testLoadMixedData() throws IOException {

        Path file = tempDir.resolve("mixed_results.txt");

        String data =
                "101,Max Verstappen,Red Bull,Miami Grand Prix," +
                        "2025-05-04,3,1,25,90.5,false\n" +
                        "Invalid race result\n" +
                        "102,Lewis Hamilton,Ferrari,Monaco Grand Prix," +
                        "2025-05-25,4,2,18,92.3,false";

        Files.write(file, data.getBytes());

        RaceResultFileReader reader = new RaceResultFileReader();

        List<RaceResult> results =
                reader.readResults(file.toString());

        assertEquals(2, results.size());
        assertEquals(101, results.get(0).getResultId());
        assertEquals(102, results.get(1).getResultId());
    }    // Tests loading all 20 race records from the project file
    @Test
    public void testLoadTwentyRaceResults() {

        DmsApplication application = new DmsApplication();

        int loaded = application.loadFile("race_results.txt");

        assertEquals(20, loaded);
        assertEquals(20, application.listResults().size());
    }
}