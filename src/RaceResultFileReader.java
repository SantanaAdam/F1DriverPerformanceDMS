
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads Formula 1 race results from a text file.
 * Handles invalid files and incorrectly formatted data.
 */
public class RaceResultFileReader {

    private final ResultValidator validator = new ResultValidator();

    /**
     * Reads race results from the file provided by the user.
     * Returns an empty list if the file cannot be read.
     */
    public List<RaceResult> readResults(String filePath) {

        List<RaceResult> results = new ArrayList<>();

        if (filePath == null || filePath.trim().isEmpty()) {
            System.out.println("Error: File path cannot be blank.");
            return results;
        }

        try (BufferedReader reader =
                     Files.newBufferedReader(Paths.get(filePath.trim()))) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) {
                    continue;
                }

                RaceResult result = parseLine(line);

                if (result != null && validator.validate(result)) {
                    results.add(result);
                } else {
                    System.out.println(
                            "Invalid data on line " + lineNumber
                                    + ". Record skipped.");
                }
            }

        } catch (IOException | RuntimeException e) {
            System.out.println(
                    "Error: Unable to read the selected file.");
        }

        return results;
    }

    /**
     * Converts one line of text into a RaceResult object.
     * Returns null when the line contains invalid data.
     */
    public RaceResult parseLine(String line) {

        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] data = line.split(",", -1);

        if (data.length != 10) {
            return null;
        }

        try {
            int id = Integer.parseInt(data[0].trim());
            String driver = data[1].trim();
            String team = data[2].trim();
            String grandPrix = data[3].trim();

            LocalDate date = LocalDate.parse(data[4].trim());

            int start = Integer.parseInt(data[5].trim());
            int finish = Integer.parseInt(data[6].trim());
            int points = Integer.parseInt(data[7].trim());
            double lap = Double.parseDouble(data[8].trim());

            String dnfText = data[9].trim();

            if (!dnfText.equalsIgnoreCase("true")
                    && !dnfText.equalsIgnoreCase("false")) {
                return null;
            }

            boolean dnf = Boolean.parseBoolean(dnfText);

            RaceResult result = new RaceResult(
                    id, driver, team, grandPrix, date,
                    start, finish, points, lap, dnf
            );

            return validator.validate(result) ? result : null;

        } catch (NumberFormatException |
                 DateTimeParseException e) {

            return null;
        }
    }
}
