
package swiftsnap;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * This application is a screenshot capture program.
 * 
 * Monday, June 3, 2013
 * @author Anthony Calandra
 */
public class SwiftSnap {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // If the system tray isn't supported... Exit.
        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(null, "SwiftSnap isn't compatible with your computer.");
            return;
        }
        
        // Get the OS system tray.
        SystemTray systemTray = SystemTray.getSystemTray();
        // The event listener on the icon's menu.
        TrayMenuListener menuListener = new TrayMenuListener();
        // The icon's menu (right-click menu).
        PopupMenu popupMenu = new PopupMenu();
        
        MenuItem settingsOption = new MenuItem("Settings");
        settingsOption.addActionListener(menuListener);
        popupMenu.add(settingsOption);
        
        MenuItem helpOption = new MenuItem("Help");
        helpOption.addActionListener(menuListener);
        popupMenu.add(helpOption);
        
        MenuItem exitOption = new MenuItem("Exit");
        exitOption.addActionListener(menuListener);
        popupMenu.add(exitOption);
         
        File icon = new File("./icon.png");
        BufferedImage image = null;
        try {
            image = ImageIO.read(icon);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        
        // Set the tray icon and attach clicking listener.
        TrayIcon trayIcon = new TrayIcon(image);
        trayIcon.addMouseListener(new TrayIconClickListener());
        trayIcon.setPopupMenu(popupMenu);
        try {
            systemTray.add(trayIcon);
        } catch (AWTException ex) {
            System.err.println(ex.getMessage());
        }
        
        JOptionPane.showMessageDialog(null, "SwiftSnap has opened and is available in the system tray (near the clock).", "SwiftSnap", JOptionPane.INFORMATION_MESSAGE);
    }
}
