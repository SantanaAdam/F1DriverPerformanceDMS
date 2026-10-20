
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Provides the command-line menu for the
 * Formula 1 Driver Performance Management System.
 */
public class F1ConsoleMenu {

    private final DmsApplication application;
    private final Scanner scanner;

    public F1ConsoleMenu() {
        application = new DmsApplication();
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the menu until the user chooses Exit.
     */
    public boolean start() {
        boolean running = true;

        while (running) {
            System.out.println("\n===== F1 DRIVER PERFORMANCE DMS =====");
            System.out.println("1. Load Race Results");
            System.out.println("2. Display All Results");
            System.out.println("3. Add Race Result");
            System.out.println("4. Update Race Result");
            System.out.println("5. Delete Race Result");
            System.out.println("6. View Driver Statistics");
            System.out.println("7. Exit");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    loadResults();
                    break;
                case 2:
                    displayResults();
                    break;
                case 3:
                    addResult();
                    break;
                case 4:
                    updateResult();
                    break;
                case 5:
                    deleteResult();
                    break;
                case 6:
                    displayStatistics();
                    break;
                case 7:
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid menu choice.");
            }
        }

        return true;
    }

    // Reads an integer without crashing on invalid input
    private int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    // Reads a decimal number
    private double readDouble(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                double value = Double.parseDouble(input);
                if (Double.isFinite(value)) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // Ask the user again
            }

            System.out.println("Please enter a valid decimal number.");
        }
    }

    // Reads required text
    private String readText(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty() && !input.contains(",")) {
                return input;
            }

            System.out.println("Text cannot be blank or contain commas.");
        }
    }

    // Reads a date in YYYY-MM-DD format
    private LocalDate readDate() {
        while (true) {
            System.out.print("Race date (YYYY-MM-DD): ");

            try {
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Example: 2025-04-06");
            }
        }
    }

    // Reads a true or false value
    private boolean readBoolean() {
        while (true) {
            System.out.print("Did not finish? (true/false): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("true")) {
                return true;
            }

            if (input.equalsIgnoreCase("false")) {
                return false;
            }

            System.out.println("Please enter true or false.");
        }
    }

    // Loads race results from a text file
    private boolean loadResults() {
        String path = readText("Enter file path: ");
        int count = application.loadFile(path);

        System.out.println(count + " race results loaded.");
        return count > 0;
    }

    // Displays every race result
    private boolean displayResults() {
        List<RaceResult> results = application.listResults();

        if (results.isEmpty()) {
            System.out.println("No race results available.");
            return false;
        }

        System.out.println("\n===== RACE RESULTS =====");

        for (RaceResult result : results) {
            System.out.println(
                    "ID: " + result.getResultId()
                            + " | Driver: " + result.getDriverName()
                            + " | Finish: " + result.getFinishingPosition()
                            + " | Points: " + result.getChampionshipPoints()
                            + " | DNF: " + result.isDnf()
            );
        }

        return true;
    }

    // Collects all ten fields for a race result
    private RaceResult readRaceResult(int id) {
        String driver = readText("Driver name: ");
        String team = readText("Team name: ");
        String grandPrix = readText("Grand Prix: ");
        LocalDate date = readDate();

        int start = readInt("Starting position (1-20): ");
        int finish = readInt("Finishing position (1-20): ");
        int points = readInt("Championship points: ");
        double lap = readDouble("Fastest lap time in seconds: ");
        boolean dnf = readBoolean();

        return new RaceResult(
                id, driver, team, grandPrix, date,
                start, finish, points, lap, dnf
        );
    }

    // Adds a new result
    private boolean addResult() {
        int id = readInt("Enter new Result ID: ");
        RaceResult result = readRaceResult(id);

        if (application.createResult(result)) {
            System.out.println("Race result added successfully.");
            displayResults();
            return true;
        }

        System.out.println("Unable to add result. Check the data or ID.");
        return false;
    }

    // Updates a result by replacing its fields
    private boolean updateResult() {
        int id = readInt("Enter Result ID to update: ");

        boolean found = false;
        for (RaceResult result : application.listResults()) {
            if (result.getResultId() == id) {
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Result ID not found.");
            return false;
        }

        System.out.println("Enter the replacement information:");
        RaceResult replacement = readRaceResult(id);

        if (application.updateResult(id, replacement)) {
            System.out.println("Race result updated successfully.");
            displayResults();
            return true;
        }

        System.out.println("Unable to update race result.");
        return false;
    }

    // Deletes a race result by ID
    private boolean deleteResult() {
        int id = readInt("Enter Result ID to delete: ");

        if (application.removeResult(id)) {
            System.out.println("Race result deleted successfully.");
            displayResults();
            return true;
        }

        System.out.println("Result ID not found.");
        return false;
    }

    // Displays the calculated statistics for a driver
    private boolean displayStatistics() {
        String name = readText("Enter driver name: ");

        DriverStatistics stats =
                application.getDriverStatistics(name);

        if (stats.getTotalRaces() == 0) {
            System.out.println("No results found for that driver.");
            return false;
        }

        System.out.println("\n===== DRIVER STATISTICS =====");
        System.out.println("Driver: " + stats.getDriverName());
        System.out.println("Total Races: " + stats.getTotalRaces());
        System.out.println("Wins: " + stats.getWins());
        System.out.println("Podiums: " + stats.getPodiums());
        System.out.println("Total Points: " + stats.getTotalPoints());
        System.out.printf("Average Finish: %.2f%n",
                stats.getAverageFinish());

        return true;
    }
}
