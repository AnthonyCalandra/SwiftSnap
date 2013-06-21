package swiftsnap;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * This class manages the cropping window during the cropping process.
 *
 * Wednesday, June 19, 2013
 *
 * @author AnthonyCalandra
 */
public class CropWindow {

    private JFrame window;
    // Are they allowed to save another image while cropping?
    private boolean canSave;

    /**
     * Create the window and set some initial settings.
     */
    public CropWindow() {
        window = new JFrame();
        // Get screen dimensions and set the window to that size.
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        window.setContentPane(new Canvas(this, (int) screenDimension.getWidth(), (int) screenDimension.getHeight()));
        window.setSize((int) screenDimension.getWidth(), (int) screenDimension.getHeight());
        // No top frame.
        window.setUndecorated(true);
        window.setVisible(true);
        canSave = false;
    }

    public void destroyWindow() {
        // Destroy the JFrame (window).
        window.setVisible(false);
        window.dispose();
        canSave = true;
    }

    /**
     * @return the canCrop
     */
    public boolean canSave() {
        return canSave;
    }
}
