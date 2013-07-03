
package swiftsnap;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * Handles all screenshot functions.
 * 
 * Wednesday, June 19, 2013
 * @author AnthonyCalandra
 */
public class Screenshot {
    
    private static Robot robot = null;
    private static ScreenshotImage lastScreenshot = null;
    
    /**
     * Takes a screenshot of the screen.
     * 
     * @return A screenshot object with the image, and dimensions set.
     */
    public static ScreenshotImage takeScreenShot() {
        if (robot == null) {
            try {
                robot = new Robot();
            } catch (AWTException ex) {
                System.err.println(ex.getMessage());
                return null;
            }
        }
        
        // Get the dimensions of the screen.
        Dimension window = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screen = new Rectangle(window);
        
        // Take a screenshot using the screen dimensions.
        BufferedImage screenshotImage = robot.createScreenCapture(screen);
        ScreenshotImage screenshot = new ScreenshotImage(screenshotImage, screenshotImage.getWidth(), screenshotImage.getHeight());
        // Cache the screenshot (to use cropping and saving methods).
        lastScreenshot = screenshot;
        return screenshot;
    }
    
    /**
     * Crops the screenshotted image to the specified dimensions.
     * 
     * @param x Initial point.
     * @param y Initial point.
     * @param x2 Final point.
     * @param y2 Final point.
     */
    public static void crop(Rectangle rect) {
        if (lastScreenshot == null)
            return;
        
        BufferedImage image = lastScreenshot.getImage();
        int x = (int) rect.getX();
        int y = (int) rect.getY();
        int width = (int) rect.getWidth();
        int height = (int) rect.getHeight();
        // Crop the image to the secified dimensions and re-create the screenshot object.
        BufferedImage newImage = image.getSubimage(x, y, width, height);
        lastScreenshot = new ScreenshotImage(newImage, newImage.getWidth(), newImage.getHeight());
    }
    
    public static BufferedImage getLastScreenshot() {
        return lastScreenshot.getImage();
    }
    
    /**
     * Save the screenshot to the filesystem.
     */
    public static void save() {
        Configuration config = Configuration.loadConfiguration();
        // Find the directory to save to.
        File saveDirectory = new File(config.getProperty("savePath"));
        // If the directory doesn't exist, leave it.
        if (!saveDirectory.exists()) {
            String dialogMsg = "Cannot find the save directory! Please change your settings.";
            JOptionPane.showMessageDialog(null, dialogMsg, "SwiftSnap", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Write the screenshot image to the file and save it.
        String filename = "Screenshot_" + System.currentTimeMillis() + ".png";
        File pngFile = new File(config.getProperty("savePath") + "\\" + filename);
        try {
            pngFile.createNewFile();
            // Write the image as a PNG.
            ImageIO.write(lastScreenshot.getImage(), "png", pngFile);
            JOptionPane.showMessageDialog(null, "Successfully saved image to " + config.getProperty("savePath"), "SwiftSnap", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
