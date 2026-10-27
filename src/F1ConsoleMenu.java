
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

    // Reads and validates fastest lap time
    private double readLapTime() {
        while (true) {
            double lap = readDouble("Fastest lap time in seconds (greater than 0): ");

            if (lap > 0) {
                return lap;
            }

            System.out.println(
                    "ERROR: Fastest lap time must be greater than 0 seconds."
            );
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

            if (input.isEmpty()) {
                System.out.println("ERROR: This field cannot be empty. Please enter text.");
            } else {
                System.out.println("ERROR: Commas are not allowed in this field.");
            }
        }
    }

    // Reads and validates a racing position between 1 and 20
    private int readPosition(String message) {
        while (true) {
            int position = readInt(message);

            if (position >= 1 && position <= 20) {
                return position;
            }

            System.out.println("ERROR: Racing position must be between 1 and 20.");
        }
    }

    // Reads championship points and prevents negative values
    // Reads championship points and prevents negative values
    private int readPoints() {
        while (true) {
            int points = readInt("Championship points (0 or greater): ");

            if (points >= 0) {
                return points;
            }

            System.out.println(
                    "ERROR: Championship points cannot be negative. Enter 0 or greater."
            );
        }
    }

    // Reads a date in YYYY-MM-DD format
    private LocalDate readDate() {
        while (true) {
            System.out.print("Race date (YYYY-MM-DD): ");

            try {
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println(
                        "ERROR: Invalid race date. Use YYYY-MM-DD format, such as 2025-04-06."
                );
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

            System.out.println(
                    "ERROR: Enter true if the driver did not finish, or false if they finished."
            );
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

            System.out.println("----------------------------------------");
            System.out.println("Result ID: " + result.getResultId());
            System.out.println("Driver: " + result.getDriverName());
            System.out.println("Team: " + result.getTeamName());
            System.out.println("Grand Prix: " + result.getGrandPrix());
            System.out.println("Race Date: " + result.getRaceDate());
            System.out.println("Starting Position: " + result.getStartingPosition());
            System.out.println("Finishing Position: " + result.getFinishingPosition());
            System.out.println("Championship Points: " + result.getChampionshipPoints());
            System.out.println("Fastest Lap Time: " + result.getFastestLapTime() + " seconds");
            System.out.println("DNF: " + result.isDnf());

        }

        System.out.println("----------------------------------------");

        return true;
    }

    // Collects all ten fields for a race result
    private RaceResult readRaceResult(int id) {
        String driver = readText("Driver name: ");
        String team = readText("Team name: ");
        String grandPrix = readText("Grand Prix: ");
        LocalDate date = readDate();

        int start = readPosition("Starting position (1-20): ");

        boolean dnf = readBoolean();

        int finish;

        if (dnf) {
            finish = 0;
            System.out.println("DNF selected. Finishing position automatically set to 0.");
        } else {
            finish = readPosition("Finishing position (1-20): ");
        }

        int points = readPoints();
        double lap = readLapTime();

        return new RaceResult(
                id, driver, team, grandPrix, date,
                start, finish, points, lap, dnf
        );
    }

    // Adds a new result after checking for duplicate IDs
    private boolean addResult() {

        int id;

        while (true) {

            id = readInt("Enter new Result ID: ");

            // Make sure the ID is a positive number
            if (id <= 0) {
                System.out.println("ERROR: Result ID must be a positive whole number.");
                continue;
            }

            // Check if the ID already exists
            boolean duplicate = false;

            for (RaceResult result : application.listResults()) {
                if (result.getResultId() == id) {
                    duplicate = true;
                    break;
                }
            }

            if (duplicate) {
                System.out.println("ERROR: Result ID " + id + " already exists.");
                System.out.println("Please enter a different ID.");
            } else {
                System.out.println("Result ID accepted!");
                break;
            }
        }

        // Only collect the remaining fields after the ID is accepted
        RaceResult result = readRaceResult(id);

        if (application.createResult(result)) {
            System.out.println("Race result added successfully.");
            displayResults();
            return true;
        }

        System.out.println("Unable to add result. Check the entered data.");
        return false;
    }

    // Updates only the field selected by the user
    private boolean updateResult() {

        int id = readInt("Enter Result ID to update: ");

        RaceResult original = null;

        // Find the existing race result
        for (RaceResult result : application.listResults()) {
            if (result.getResultId() == id) {
                original = result;
                break;
            }
        }

        if (original == null) {
            System.out.println("ERROR: Result ID " + id + " was not found.");
            return false;
        }

        boolean updated = false;

        while (true) {

            System.out.println("\n===== UPDATE RACE RESULT =====");
            System.out.println("Result ID: " + id);
            System.out.println("1. Driver Name");
            System.out.println("2. Team Name");
            System.out.println("3. Grand Prix");
            System.out.println("4. Race Date");
            System.out.println("5. Starting Position");
            System.out.println("6. Finishing Position");
            System.out.println("7. Championship Points");
            System.out.println("8. Fastest Lap Time");
            System.out.println("9. DNF Status");
            System.out.println("10. Return to Main Menu");

            int choice = readInt("Choose a field to update: ");

            if (choice == 10) {
                return updated;
            }

            if (choice < 1 || choice > 9) {
                System.out.println("ERROR: Choose a number between 1 and 10.");
                continue;
            }

            // Keep all the original information
            String driver = original.getDriverName();
            String team = original.getTeamName();
            String grandPrix = original.getGrandPrix();
            LocalDate date = original.getRaceDate();
            int start = original.getStartingPosition();
            int finish = original.getFinishingPosition();
            int points = original.getChampionshipPoints();
            double lap = original.getFastestLapTime();
            boolean dnf = original.isDnf();

            // Change only the selected field
            switch (choice) {

                case 1:
                    driver = readText("New driver name: ");
                    break;

                case 2:
                    team = readText("New team name: ");
                    break;

                case 3:
                    grandPrix = readText("New Grand Prix: ");
                    break;

                case 4:
                    date = readDate();
                    break;

                case 5:
                    start = readPosition("New starting position (1-20): ");
                    break;

                case 6:
                    if (dnf) {
                        System.out.println(
                                "This driver has a DNF. Change DNF status to false before updating the finishing position."
                        );
                    } else {
                        finish = readPosition("New finishing position (1-20): ");
                    }
                    break;
                case 7:
                    points = readPoints();
                    break;

                case 8:
                    lap = readLapTime();
                    break;

                case 9:
                    dnf = readBoolean();

                    if (dnf) {
                        finish = 0;
                        System.out.println("DNF selected. Finishing position set to 0.");
                    } else if (finish == 0) {
                        finish = readPosition("New finishing position (1-20): ");
                    }

                    break;
            }

            // Create a replacement record with the updated information
            RaceResult replacement = new RaceResult(
                    id, driver, team, grandPrix, date,
                    start, finish, points, lap, dnf
            );

            // Save the updated record
            if (application.updateResult(id, replacement)) {

                System.out.println("Race result updated successfully!");

                // Keep the updated information for additional changes
                original = replacement;
                updated = true;

            } else {
                System.out.println(
                        "ERROR: Update failed. Check that the values are valid."
                );
            }

        } // End of while loop

    } // End of updateResult method
    // Deletes a race result after asking for confirmation
    private boolean deleteResult() {

        int id = readInt("Enter Result ID to delete: ");

        // Find the record before attempting deletion
        RaceResult selected = null;

        for (RaceResult result : application.listResults()) {
            if (result.getResultId() == id) {
                selected = result;
                break;
            }
        }

        if (selected == null) {
            System.out.println("ERROR: Result ID " + id + " was not found.");
            return false;
        }

        // Show the user which record will be deleted
        System.out.println("\n===== DELETE RACE RESULT =====");
        System.out.println("Result ID: " + selected.getResultId());
        System.out.println("Driver: " + selected.getDriverName());
        System.out.println("Grand Prix: " + selected.getGrandPrix());

        // Ask for confirmation
        while (true) {

            System.out.print("Are you sure you want to delete this record? (Y/N): ");
            String answer = scanner.nextLine().trim();

            if (answer.equalsIgnoreCase("N")) {
                System.out.println("Deletion cancelled. No records were removed.");
                return false;
            }

            if (answer.equalsIgnoreCase("Y")) {

                if (application.removeResult(id)) {
                    System.out.println("Race result deleted successfully.");
                    displayResults();
                    return true;
                }

                System.out.println("ERROR: Unable to delete race result.");
                return false;
            }

            System.out.println("ERROR: Please enter Y for Yes or N for No.");
        }
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
