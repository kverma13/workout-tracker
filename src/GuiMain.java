import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;


public class GuiMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Workout Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.add(new JLabel("Hello from the GUI!"));
            frame.setVisible(true);
        });
    }


}
