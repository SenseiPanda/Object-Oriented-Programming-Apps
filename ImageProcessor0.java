import java.awt.image.BufferedImage;
import java.awt.Color;

/**
 * A class demonstrating manipulation of image pixels.
 * Version 0: just the core definition
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, Winter 2014, rewritten for BufferedImage
 * @author CBK, Spring 2015, refactored to separate GUI from operations
 */
public class ImageProcessor0 {
    private BufferedImage image;		// the current image being processed

    /**
     * @param image		the original
     */
    public ImageProcessor0(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
    // create a method that modifies the image to green when a mouse click occurs
    public void addGreen(int cx, int cy, int r){
        //nested for loop going over nearby pixels
        for (int y = Math.max(0, cy-r); y < Math.min(image.getHeight(), cy+r); y++) {
            for (int x = Math.max(0, cx-r); x < Math.min(image.getWidth(), cx+r); x++) {
                //perform  method here
                Color pixelcolor = new Color(0,255,0);
                 int newPixelColor = pixelcolor.getRGB();
                image.setRGB(x,y, newPixelColor);

            }
        }

    }
}