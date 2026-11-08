import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class F1Dashboard extends JFrame {

    // Table used to display Formula 1 race results
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    // Connect the GUI to the existing application logic
    private final DmsApplication application = new DmsApplication();

    public F1Dashboard() {

        // Set up the main application window
        setTitle("F1 Driver Performance Management System");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(25, 25, 25));

        // Create the red header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(180, 0, 0));
        headerPanel.setPreferredSize(new Dimension(1100, 90));

        JLabel titleLabel = new JLabel(
                "F1 DRIVER PERFORMANCE MANAGEMENT SYSTEM",
                SwingConstants.CENTER
        );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Define the columns for the race results table
        String[] columns = {
                "ID",
                "Driver",
                "Team",
                "Grand Prix",
                "Date",
                "Start",
                "Finish",
                "Points",
                "Fastest Lap",
                "DNF"
        };

        // Create a table model with no starting records
        tableModel = new DefaultTableModel(columns, 0) {

            // Prevent users from directly editing table cells
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create the race results table
        resultsTable = new JTable(tableModel);
        resultsTable.setRowHeight(28);
        resultsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        resultsTable.setForeground(Color.WHITE);
        resultsTable.setBackground(new Color(40, 40, 40));
        resultsTable.setGridColor(new Color(75, 75, 75));
        resultsTable.setSelectionBackground(new Color(180, 0, 0));
        resultsTable.setSelectionForeground(Color.WHITE);

        // Style the table headers
        resultsTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        resultsTable.getTableHeader().setBackground(
                new Color(180, 0, 0)
        );

        resultsTable.getTableHeader().setForeground(Color.WHITE);

        // Add scrolling for large amounts of data
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.getViewport().setBackground(new Color(40, 40, 40));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

// Create a panel for the dashboard buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(25, 25, 25));

// Create the Load File button
        JButton loadButton = new JButton("Load Race Results");
        loadButton.setBackground(new Color(180, 0, 0));
        loadButton.setForeground(Color.WHITE);
        loadButton.setFont(new Font("Arial", Font.BOLD, 14));
        loadButton.setFocusPainted(false);

// Load a text file when the button is clicked
        loadButton.addActionListener(e -> loadRaceResults());

        buttonPanel.add(loadButton);
        // Create the Add Race Result button
        JButton addButton = new JButton("Add Race Result");

        addButton.setBackground(new Color(180, 0, 0));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setFocusPainted(false);

// Add the button to the dashboard
        // Open the form when the Add Race Result button is clicked
        addButton.addActionListener(e -> showAddResultForm());
        buttonPanel.add(addButton);
        // Create the Delete Race Result button
        JButton deleteButton = new JButton("Delete Race Result");

        deleteButton.setBackground(new Color(180, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setFocusPainted(false);

// Connect the button to the delete method
        deleteButton.addActionListener(e -> deleteSelectedResult());

// Add the button to the dashboard
        buttonPanel.add(deleteButton);
        // Create the Update Race Result button
        JButton updateButton = new JButton("Update Race Result");

        updateButton.setBackground(new Color(180, 0, 0));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.setFocusPainted(false);

// Add the button to the dashboard
        // Open the update form when the button is clicked
        updateButton.addActionListener(e -> showUpdateResultForm());
        buttonPanel.add(updateButton);
        // Create the Driver Statistics button
        JButton statsButton = new JButton("Driver Statistics");

        statsButton.setBackground(new Color(180, 0, 0));
        statsButton.setForeground(Color.WHITE);
        statsButton.setFont(new Font("Arial", Font.BOLD, 14));
        statsButton.setFocusPainted(false);

// Add the button to the dashboard
        // Open driver statistics when the button is clicked
        statsButton.addActionListener(e -> showDriverStatistics());
        buttonPanel.add(statsButton);
        // Create the Exit button
        JButton exitButton = new JButton("Exit");

        exitButton.setBackground(new Color(80, 80, 80));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setFocusPainted(false);

// Ask for confirmation before exiting
        exitButton.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit the F1 Driver Performance Management System?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

// Add the Exit button to the dashboard
        buttonPanel.add(exitButton);

// Place the button panel below the table
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

// Display the completed panel
        add(mainPanel);
    }
    // Allow the user to select and load race results through the GUI
    private void loadRaceResults() {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Race Results File");

        int selection = fileChooser.showOpenDialog(this);

        // Do nothing if the user cancels the file selection
        if (selection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        String filePath = fileChooser.getSelectedFile().getAbsolutePath();

        try {
            // Use the existing application logic to load the file
            int loaded = application.loadFile(filePath);

            // Clear existing rows before refreshing the table
            tableModel.setRowCount(0);

            // Display each loaded race result
            for (RaceResult result : application.listResults()) {

                Object[] row = {
                        result.getResultId(),
                        result.getDriverName(),
                        result.getTeamName(),
                        result.getGrandPrix(),
                        result.getRaceDate(),
                        result.getStartingPosition(),
                        result.getFinishingPosition(),
                        result.getChampionshipPoints(),
                        result.getFastestLapTime(),
                        result.isDnf() ? "Yes" : "No"
                };

                tableModel.addRow(row);
            }

            // Show the user how many records were imported
            JOptionPane.showMessageDialog(
                    this,
                    loaded + " race results loaded successfully.",
                    "File Loaded",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {

            // Display errors through the GUI instead of crashing
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load race results:\n" + ex.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }// Display a form for entering a new Formula 1 race result
    private void showAddResultForm() {

        // Create input fields for all 10 attributes
        JTextField idField = new JTextField();
        JTextField driverField = new JTextField();
        JTextField teamField = new JTextField();
        JTextField grandPrixField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField startField = new JTextField();
        JTextField finishField = new JTextField();
        JTextField pointsField = new JTextField();
        JTextField lapField = new JTextField();

        // DNF is selected using a dropdown menu
        JComboBox<String> dnfBox = new JComboBox<>(
                new String[]{"No", "Yes"}
        );

        // Create the form layout
        JPanel formPanel = new JPanel(new GridLayout(10, 2, 10, 10));

        formPanel.add(new JLabel("Result ID:"));
        formPanel.add(idField);

        formPanel.add(new JLabel("Driver Name:"));
        formPanel.add(driverField);

        formPanel.add(new JLabel("Team Name:"));
        formPanel.add(teamField);

        formPanel.add(new JLabel("Grand Prix:"));
        formPanel.add(grandPrixField);

        formPanel.add(new JLabel("Race Date (YYYY-MM-DD):"));
        formPanel.add(dateField);

        formPanel.add(new JLabel("Starting Position (1-20):"));
        formPanel.add(startField);

        formPanel.add(new JLabel("Finishing Position (0-20):"));
        formPanel.add(finishField);

        formPanel.add(new JLabel("Championship Points (0-26):"));
        formPanel.add(pointsField);

        formPanel.add(new JLabel("Fastest Lap Time (seconds):"));
        formPanel.add(lapField);

        formPanel.add(new JLabel("Did Not Finish (DNF):"));
        formPanel.add(dnfBox);

        // Display the form in a popup window
        int choice = JOptionPane.showConfirmDialog(
                this,
                formPanel,
                "Add New Race Result",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (choice == JOptionPane.OK_OPTION) {

            try {
                // Convert the form inputs into their correct data types
                int id = Integer.parseInt(idField.getText().trim());
                String driver = driverField.getText().trim();
                String team = teamField.getText().trim();
                String grandPrix = grandPrixField.getText().trim();

                LocalDate date = LocalDate.parse(dateField.getText().trim());

                int start = Integer.parseInt(startField.getText().trim());
                int finish = Integer.parseInt(finishField.getText().trim());
                int points = Integer.parseInt(pointsField.getText().trim());
                double lap = Double.parseDouble(lapField.getText().trim());

                boolean dnf = dnfBox.getSelectedIndex() == 1;

                // Check for duplicate IDs before creating the record
                for (RaceResult existing : application.listResults()) {

                    if (existing.getResultId() == id) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Result ID " + id + " already exists. Please use a different ID.",
                                "Duplicate ID",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                }

                // Create the race result using the existing constructor
                RaceResult newResult = new RaceResult(
                        id,
                        driver,
                        team,
                        grandPrix,
                        date,
                        start,
                        finish,
                        points,
                        lap,
                        dnf
                );

                // Save using the existing application logic
                boolean saved = application.createResult(newResult);

                if (saved) {

                    // Immediately add the new result to the GUI table
                    Object[] row = {
                            newResult.getResultId(),
                            newResult.getDriverName(),
                            newResult.getTeamName(),
                            newResult.getGrandPrix(),
                            newResult.getRaceDate(),
                            newResult.getStartingPosition(),
                            newResult.getFinishingPosition(),
                            newResult.getChampionshipPoints(),
                            newResult.getFastestLapTime(),
                            newResult.isDnf() ? "Yes" : "No"
                    };

                    tableModel.addRow(row);

                    JOptionPane.showMessageDialog(
                            this,
                            "Race result added successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Unable to add the race result.\n" +
                                    "Please check all fields and their allowed values.",
                            "Validation Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter valid numbers for ID, positions, points, and lap time.",
                        "Invalid Number",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (java.time.format.DateTimeParseException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter the race date in YYYY-MM-DD format.",
                        "Invalid Date",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to add the race result: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }// Delete the selected race result after user confirmation
    private void deleteSelectedResult() {

        // Find the selected row in the table
        int selectedRow = resultsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a race result from the table first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Convert the selected view row to the table model row
        int modelRow = resultsTable.convertRowIndexToModel(selectedRow);

        // Retrieve the ID and driver name
        int resultId = (Integer) tableModel.getValueAt(modelRow, 0);
        String driverName = tableModel.getValueAt(modelRow, 1).toString();

        // Ask the user to confirm deletion
        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete result " + resultId +
                        " for " + driverName + "?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        // Cancel deletion if the user selects No
        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        // Delete the record from the application
        boolean deleted = application.removeResult(resultId);

        if (deleted) {

            // Remove the row from the GUI table
            tableModel.removeRow(modelRow);

            JOptionPane.showMessageDialog(
                    this,
                    "Race result deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete the selected race result.",
                    "Deletion Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    // Display an editing form for the selected race result
    private void showUpdateResultForm() {

        // Check whether a row is selected
        int selectedRow = resultsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a race result to update.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Get the selected row from the table model
        int modelRow = resultsTable.convertRowIndexToModel(selectedRow);

        // Retrieve the selected race result's information
        int resultId = (Integer) tableModel.getValueAt(modelRow, 0);

        // Create fields containing the current values
        JTextField driverField = new JTextField(
                tableModel.getValueAt(modelRow, 1).toString()
        );

        JTextField teamField = new JTextField(
                tableModel.getValueAt(modelRow, 2).toString()
        );

        JTextField grandPrixField = new JTextField(
                tableModel.getValueAt(modelRow, 3).toString()
        );

        JTextField dateField = new JTextField(
                tableModel.getValueAt(modelRow, 4).toString()
        );

        JTextField startField = new JTextField(
                tableModel.getValueAt(modelRow, 5).toString()
        );

        JTextField finishField = new JTextField(
                tableModel.getValueAt(modelRow, 6).toString()
        );

        JTextField pointsField = new JTextField(
                tableModel.getValueAt(modelRow, 7).toString()
        );

        JTextField lapField = new JTextField(
                tableModel.getValueAt(modelRow, 8).toString()
        );

        JComboBox<String> dnfBox = new JComboBox<>(
                new String[]{"No", "Yes"}
        );

        // Set the current DNF value
        String currentDnf = tableModel.getValueAt(modelRow, 9).toString();
        dnfBox.setSelectedItem(currentDnf);

        // Create the form
        JPanel formPanel = new JPanel(new GridLayout(10, 2, 10, 10));

        formPanel.add(new JLabel("Driver Name:"));
        formPanel.add(driverField);

        formPanel.add(new JLabel("Team Name:"));
        formPanel.add(teamField);

        formPanel.add(new JLabel("Grand Prix:"));
        formPanel.add(grandPrixField);

        formPanel.add(new JLabel("Race Date (YYYY-MM-DD):"));
        formPanel.add(dateField);

        formPanel.add(new JLabel("Starting Position:"));
        formPanel.add(startField);

        formPanel.add(new JLabel("Finishing Position:"));
        formPanel.add(finishField);

        formPanel.add(new JLabel("Championship Points:"));
        formPanel.add(pointsField);

        formPanel.add(new JLabel("Fastest Lap Time:"));
        formPanel.add(lapField);

        formPanel.add(new JLabel("DNF:"));
        formPanel.add(dnfBox);

        // Display the result ID (not editable)
        formPanel.add(new JLabel("Result ID:"));
        formPanel.add(new JLabel(String.valueOf(resultId)));

        // Open the editing popup
        int choice = JOptionPane.showConfirmDialog(
                this,
                formPanel,
                "Update Race Result - ID " + resultId,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (choice == JOptionPane.OK_OPTION) {

            try {
                // Read the updated information from the form
                String driver = driverField.getText().trim();
                String team = teamField.getText().trim();
                String grandPrix = grandPrixField.getText().trim();

                java.time.LocalDate date = java.time.LocalDate.parse(
                        dateField.getText().trim()
                );

                int start = Integer.parseInt(startField.getText().trim());
                int finish = Integer.parseInt(finishField.getText().trim());
                int points = Integer.parseInt(pointsField.getText().trim());
                double lap = Double.parseDouble(lapField.getText().trim());

                boolean dnf = dnfBox.getSelectedIndex() == 1;

                // Create a replacement record with the same ID
                RaceResult updatedResult = new RaceResult(
                        resultId,
                        driver,
                        team,
                        grandPrix,
                        date,
                        start,
                        finish,
                        points,
                        lap,
                        dnf
                );

                // Update the record through the existing application logic
                boolean updated = application.updateResult(
                        resultId,
                        updatedResult
                );

                if (updated) {

                    // Refresh the selected row in the GUI table
                    Object[] updatedRow = {
                            updatedResult.getResultId(),
                            updatedResult.getDriverName(),
                            updatedResult.getTeamName(),
                            updatedResult.getGrandPrix(),
                            updatedResult.getRaceDate(),
                            updatedResult.getStartingPosition(),
                            updatedResult.getFinishingPosition(),
                            updatedResult.getChampionshipPoints(),
                            updatedResult.getFastestLapTime(),
                            updatedResult.isDnf() ? "Yes" : "No"
                    };

                    for (int column = 0; column < updatedRow.length; column++) {
                        tableModel.setValueAt(updatedRow[column], modelRow, column);
                    }

                    JOptionPane.showMessageDialog(
                            this,
                            "Race result updated successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Unable to update the race result. Please check your values.",
                            "Validation Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter valid numbers for positions, points, and lap time.",
                        "Invalid Number",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (java.time.format.DateTimeParseException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter the date in YYYY-MM-DD format.",
                        "Invalid Date",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to update the race result: " + ex.getMessage(),
                        "Update Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }// Display statistics for the selected driver
    // Display performance statistics for the selected driver
    private void showDriverStatistics() {

        // Check whether a driver has been selected
        int selectedRow = resultsTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a driver from the table first.",
                    "No Driver Selected",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Identify the selected driver
        int modelRow = resultsTable.convertRowIndexToModel(selectedRow);

        String driverName = tableModel.getValueAt(modelRow, 1).toString();

        // Calculate statistics using the existing application logic
        DriverStatistics stats = application.getDriverStatistics(driverName);

        // Format the statistics for display
        String message =
                "Driver: " + stats.getDriverName() +
                        "\n\nTotal Races: " + stats.getTotalRaces() +
                        "\nRace Wins: " + stats.getWins() +
                        "\nPodium Finishes: " + stats.getPodiums() +
                        "\nChampionship Points: " + stats.getTotalPoints() +
                        "\nAverage Finishing Position: " +
                        String.format("%.2f", stats.getAverageFinish());

        // Display the calculated statistics
        JOptionPane.showMessageDialog(
                this,
                message,
                "F1 Driver Performance Statistics",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}