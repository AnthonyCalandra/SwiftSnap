
package swiftsnap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JOptionPane;

/**
 * This class is responsible for handling events for the icon's menu. Events
 * such as choosing a menu item or right-clicking the icon.
 * 
 * Wednesday, June 19, 2013
 * @author Anthony Calandra
 */
public class TrayMenuListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Exit":
                String exitMessage = "Are you sure you wish to exit?";
                int doExit = JOptionPane.showConfirmDialog(null, exitMessage, action, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                // Close the program.
                if (doExit == JOptionPane.YES_OPTION)
                    System.exit(0);
                break;
            
            case "Help":
                String helpMessage = "Double-click on the SwiftSnap icon located in your notification area (where the clock is on the bottom-right)"
                    + " to take a picture of the current screen.\n\nTo change where pictures are stored and to toggle"
                    + " cropping, right-click the SwiftSnap icon and find the settings option.";
                JOptionPane.showMessageDialog(null, helpMessage, action, JOptionPane.INFORMATION_MESSAGE);
                break;
                
            case "Settings":
                Configuration config = Configuration.loadConfiguration();
                String dialogMsg1 = "Enter a directory to save your screenshots to:";
                String dialogMsg2 = "Allow cropping option?";
                
                // Get the file path from the user.
                // Make sure the path can be written to and is valid.
                String path;
                File file;
                do {
                    path = JOptionPane.showInputDialog(null, dialogMsg1, config.getProperty("savePath"));
                    if (path == null) {
                        path = config.getProperty("savePath");
                        break;
                    }
                        
                    file = new File(path);
                    // Incase the loop re-iterates, send a message.
                    dialogMsg1 = "The directory is invalid or doesn't exist!\n\nEnter a directory to save your screenshots to:";
                } while(!file.canRead() || !file.isDirectory());
                
                // Ask the user if they want the cropping feature.
                String allowCrop = "false";
                int doCrop = JOptionPane.showConfirmDialog(null, dialogMsg2, "SwiftSnap", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (doCrop == JOptionPane.YES_OPTION) {
                    allowCrop = "true";
                }
                
                // Set all the configuration settings filled in by the user.
                config.setProperty("savePath", path);
                config.setProperty("enableAutoCropping", allowCrop);
                config.saveConfiguration();
                JOptionPane.showMessageDialog(null, "Settings saved!");
                break;
        }
    }
}
