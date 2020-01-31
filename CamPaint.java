import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.plaf.synth.Region;

/**
 * Webcam-based drawing
 * Scaffold for PS-1, Dartmouth CS 10, Fall 2016
 *
 * @author Chris Bailey-Kellogg, Spring 2015 (based on a different webcam app from previous terms)
 */
public class CamPaint extends Webcam {
    private char displayMode = 'w';			// what to display: 'w': live webcam, 'r': recolored image, 'p': painting
    private char paintMode = 'p';
    private char recolorMode = 'r';
    private RegionFinder finder;			// handles the finding
    private Color targetColor;          	// color of regions of interest (set by mouse press)
    private Color paintColor = Color.blue;	// the color to put into the painting from the "brush"
    private BufferedImage painting;			// the resulting masterpiece


    /**
     * Initializes the region finder and the drawing
     */

    //You need to plug in calls to your region finder within processImage, in order to
    // find the largest region. Be sure to give the region finder the image and the color
    // (and that those aren't null).
    public CamPaint() {
        finder = new RegionFinder();
        clearPainting();
    }

    /**
     * Resets the painting to a blank image
     */
    protected void clearPainting() {
        painting = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * DrawingGUI method, here drawing one of live webcam, recolored image, or painting,
     * depending on display variable ('w', 'r', or 'p')
     */
    @Override
    public void draw(Graphics g) {
        // TODO: YOUR CODE HERE
        //save all matching pixels to a list, then do a for-each type to get through that list
        //add if statements for draw?
        if(displayMode=='w') {
            g.drawImage(image, 0, 0, null);
        }
        //do scenario for recoloring
        else if (displayMode=='r'){

            g.drawImage(finder.getRecoloredImage(),0, 0, null);
        }
        else if (displayMode=='p'){
            g.drawImage(painting, 0, 0, null);

            //white screen with using mouse to draw
        }
        }
        //add two more modes for r mode and p mode

    /**
     * Webcam method, here finding regions and updating the painting.
     */
    @Override
    public void processImage() {
        // TODO: YOUR CODE HERE
//plug in a call to my region finder
        //be sure to give region finder the image and the color
        ArrayList<Point> brush;

        finder.setImage(image);

        if (targetColor!=null) {
            System.out.println(targetColor);
            finder.findRegions(targetColor);

            brush = finder.largestRegion();
            finder.recolorImage();

        }
        //recolor the image and do repainting stuff
    }

    /**
     * Overrides the DrawingGUI method to set the track color.
     */
    @Override
    public void handleMousePress(int x, int y) {
        // TODO: YOUR CODE HERE
        if (image != null) {
            Color trackColor = new Color(image.getRGB(x, y));
            System.out.println("tracking " + trackColor);
            targetColor = trackColor;
        }
    }

    /**
     * DrawingGUI method, here doing various drawing commands
     */
    @Override
    public void handleKeyPress(char k) {
        if (k == 'p' || k == 'r' || k == 'w') { // display: painting, recolored image, or webcam
            displayMode = k;
            System.out.println("Current mode of display is" + k);
        }
        else if (k == 'c') { // clear
            clearPainting();
        }
        else if (k == 'o') { // save the recolored image
            saveImage(finder.getRecoloredImage(), "pictures/recolored.png", "png");
        }
        else if (k == 's') { // save the painting
            saveImage(painting, "pictures/painting.png", "png");
        }
        else {
            System.out.println("unexpected key "+k);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CamPaint();
            }
        });
    }
}