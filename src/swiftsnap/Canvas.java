
package swiftsnap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class is the canvas in the cropping window the user interacts with.
 * 
 * Wednesday, June 19, 2013
 * @author AnthonyCalandra
 */
public class Canvas extends JPanel implements MouseListener, MouseMotionListener {

    private int initialX = 0;
    private int initialY = 0;
    private int finalX = 0;
    private int finalY = 0;
    private int totalScreenWidth = 0;
    private int totalScreenHeight = 0;
    private boolean clearCanvas = false;
    private CropWindow window = null;

    /**
     * Constructs the canvas.
     * 
     * @param parent The window that created this frame (its parent).
     * @param screenWidth The width of the canvas.
     * @param screenHeight The height of the canvas.
     */
    public Canvas(CropWindow parent, int screenWidth, int screenHeight) {
        window = parent;
        totalScreenWidth = screenWidth;
        totalScreenHeight = screenHeight;
        
        // Attach the mouse event listeners to this canvas.
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        // Set the brush color.
        g.setColor(new Color(255, 0, 0, 128));
        // If the canvas is requested to be cleared, clear the screen.
        if (clearCanvas) {
            g.clearRect(0, 0, totalScreenWidth, totalScreenHeight);
            clearCanvas = false;
        }
        
        g.drawImage(Screenshot.getLastScreenshot(), 0, 0, this);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Set a point and drag your mouse to create a rectangle.", 50, 50);

        // If there is a final (which implies an initial as well) point.
        if (finalX > 0 && finalY > 0) {
            // Get the point to start drawing and the size of the rectangle.
            g.drawRect(initialX, initialY, finalX - initialX, finalY - initialY);
        } else if (initialX > 0 && initialY > 0) { // Initial points set only?
            // Draw the initial point.
            g.fillRect(initialX, initialY, 5, 5);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Set the point to start drawing the rectangle.
        initialX = e.getXOnScreen();
        initialY = e.getYOnScreen();
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Is there a final point? At this stage, this means a rectangle is constructed.
        if (finalX > 0 && finalY > 0) {
            String dialogMsg = "Would you like to crop the image to the specified size?";
            int doCrop = JOptionPane.showConfirmDialog(null, dialogMsg, "SwiftSnap", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
            // If the user decides to crop the image using the rectangle...
            if (doCrop == JOptionPane.YES_OPTION) {
                // Crop and save the image.
                Screenshot.crop(initialX, initialY, finalX, finalY);
                Screenshot.save();
                window.destroyWindow();
            }
        }
        
        // Reset some values and clear the canvas.
        initialX = 0;
        initialY = 0;
        finalX = 0;
        finalY = 0;
        clearCanvas = true;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Get the current point where the mouse is dragged at.
        finalX = e.getXOnScreen();
        finalY = e.getYOnScreen();
        // Behind the initial point? Nope.
        if (finalX < initialX || finalY < initialY) {
            finalX = 0;
            finalY = 0;   
        }
        
        clearCanvas = true;
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * @return the initialX
     */
    public int getInitialX() {
        return initialX;
    }

    /**
     * @return the initialY
     */
    public int getInitialY() {
        return initialY;
    }

    /**
     * @return the finalX
     */
    public int getFinalX() {
        return finalX;
    }

    /**
     * @return the finalY
     */
    public int getFinalY() {
        return finalY;
    }
}
