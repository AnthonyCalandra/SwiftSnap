
package swiftsnap;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

/**
 * This class is responsible for double-clicking and right-clicking on the icon.
 * 
 * Wednesday, June 19, 2013
 * @author Anthony Calandra
 */
public class TrayIconClickListener implements MouseListener {

    CropWindow cropWindow = null;
    
    @Override
    public void mouseClicked(MouseEvent e) {
        // Double-click event.
        if (e.getClickCount() >= 2) {
            // Prevent the user from taking another screenshot while cropping.
            if (cropWindow != null && !cropWindow.canSave())
                return;
            
            // Get the configuration data.
            Configuration config = Configuration.loadConfiguration();
            // Take a screenshot of the entire screen.
            Screenshot.takeScreenShot();
            // Did they enable the cropping feature?
            if (config.getProperty("enableAutoCropping").equals("true")) {
                String cropMessage = "Would you like to crop this image?";
                int doCrop = JOptionPane.showConfirmDialog(null, cropMessage, "SwiftSnap", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (doCrop == JOptionPane.YES_OPTION) {
                    // Open a window for the user to select the dimensions.
                    cropWindow = new CropWindow();
                    return;
                }
            }
            
            // Save the screenshot.
            Screenshot.save();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
