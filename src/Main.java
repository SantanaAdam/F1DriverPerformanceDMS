
// starts the project
import javax.swing.SwingUtilities;

/**
 * Main entry point for the Formula 1 Driver Performance
 * Management System.
 *
 * Launches the graphical user interface using Java Swing
 * and initializes the F1Dashboard application window.
 */

public class Main {

    /**
     * Starts the Formula 1 Driver Performance Management System.
     *
     * Uses the Swing event dispatch thread to create and display
     * the F1Dashboard graphical user interface.
     *
     * @param args command-line arguments, which are not used
     *             by this application
     */

    public static void main(String[] args) {

        // Start the graphical user interface
        // on Swing's Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> {

            F1Dashboard dashboard = new F1Dashboard();

            // Display the application window.
            dashboard.setVisible(true);
        });
    }
}