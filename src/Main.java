
// starts the project
import javax.swing.SwingUtilities;

public class Main {

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