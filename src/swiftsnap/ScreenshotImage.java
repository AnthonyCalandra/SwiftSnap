
package swiftsnap;

import java.awt.image.BufferedImage;

/**
 * This class abstracts the screenshot which encapsulates the actual image and its
 * dimensions.
 * 
 * Wednesday, June 19, 2013
 * @author AnthonyCalandra
 */
public class ScreenshotImage {
    
    private BufferedImage image;
    private int width;
    private int height;
    
    /**
     * Constructs a screenshot object.
     * 
     * @param image Image taken by Robot object.
     * @param width Image width.
     * @param height Image height.
     */
    public ScreenshotImage(BufferedImage image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }

    /**
     * @return the image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }
}
