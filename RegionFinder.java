import java.awt.*;
import java.awt.image.*;
import java.util.*;

/**
 * Region growing algorithm: finds and holds regions in an image.
 * Each region is a list of contiguous points with colors similar to a target color.
 * Scaffold for PS-1, Dartmouth CS 10, Fall 2016
 *
 * @author Chris Bailey-Kellogg, Winter 2014 (based on a very different structure from Fall 2012)
 * @author Travis W. Peters, Dartmouth CS 10, Updated Winter 2015
 * @author CBK, Spring 2015, updated for CamPaint
 */
public class RegionFinder {
    private static final int maxColorDiff = 27;                // how similar a pixel color must be to the target color, to belong to a region
    private static final int minRegion = 100;                // how many points in a region to be worth considering

    private BufferedImage image;                            // the image in which to find regions
    private BufferedImage recoloredImage;                   // the image with identified regions recolored

    private ArrayList<ArrayList<Point>> regions;            // a region is a list of points
    // so the identified regions are in a list of lists of points

    public RegionFinder() {
        this.image = null;
    }

    public RegionFinder(BufferedImage image) {
        this.image = image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getRecoloredImage() {
        return recoloredImage;
    }

    /**
     * Sets regions to the flood-fill regions in the image, similar enough to the trackColor.
     * Loop over all the pixels
     * If a pixel is unvisited and of the correct color
     * Start a new region
     * Keep track of which pixels need to be visited, initially just that one
     * As long as there's some pixel that needs to be visited
     * Get one to visit
     * Add it to the region
     * Mark it as visited
     * Loop over all its neighbors
     * If the neighbor is of the correct color
     * Add it to the list of pixels to be visited
     * If the region is big enough to be worth keeping, do so
     */

    public void findRegions(Color targetColor) {

        //make an array list of lists to keep track of my regions

        regions = new ArrayList<ArrayList<Point>>();

        //keep track of pixels visited in a BufferedImageObject (a two dimensional array of colors, sort of)

        BufferedImage visitTest = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        //loop over all of the pixels in the frame

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                //is the color of this pixel in the second image black? if yes, unvisited
                //debugging to make sure we're moving through the pixels
                //System.out.println("x is " + x);
                //System.out.println("y is " + y);

                //if it hasn't been visited

                if (visitTest.getRGB(x, y) == 0) {

                    //is pixel of the correct color?

                    if (colorMatch(new Color(image.getRGB(x, y)), targetColor)) {

                        //System.out.println("color is matched");

                        //start a new region now that we have a match

                        ArrayList<Point> region = new ArrayList<>();

                        ArrayList<Point> toBeVisited = new ArrayList<>();

                        Point first = new Point(x, y);

                        toBeVisited.add(first);
                        // System.out.println("the first point is " + toBeVisited);

                        //are there still points to visit?
                        while (toBeVisited.size() > 0) {

                            //pop it off and add it to the region
                            Point my_point = toBeVisited.remove(toBeVisited.size() - 1);

                            region.add(my_point);

                            //mark the pixel as visited
                            visitTest.setRGB(my_point.x, my_point.y, 1);


                            //increment neighbors, but set conditions to make sure neighbors are not out of bounds
                            for (int ny = Math.max(my_point.y - 1, 0); ny <= Math.min(my_point.y + 1, image.getHeight() - 1); ny++) {

                                for (int nx = Math.max(my_point.x - 1, 0); nx <= Math.min(my_point.x + 1, image.getWidth() - 1); nx++) {
//                                    System.out.println("nx is " + nx);
//                                    System.out.println("ny is " + ny);


                                    Point point = new Point(nx, ny);

                                    //test if neighbor is of the right color
                                    if (colorMatch(new Color(image.getRGB(nx, ny)), targetColor) && visitTest.getRGB(nx, ny) == 0) {

                                        //if it is, add it to the list of pixels that need to be visited

                                        toBeVisited.add(new Point(nx, ny));

                                    }

                                }
                            }

                        }
                        //after all this region creation, is the region even the right size? if so, add it to our 'master' regions list
                        if (region.size() > minRegion) {

                            regions.add(region);
                            //System.out.println("found region that exceeds min");

                        }
                    }

                }

            }
        }

    }




    /**
     * Tests whether the two colors are "similar enough" (your definition, subject to the maxColorDiff threshold, which you can vary).
     */
    private static boolean colorMatch (Color c1, Color c2) {

// borrowed from color tracking, return absolute value difference converted from hexidecimal
        boolean red = Math.abs(c1.getRed()-c2.getRed()) < maxColorDiff;
        boolean green = Math.abs(c1.getGreen()-c2.getGreen()) < maxColorDiff;
        boolean blue = Math.abs(c1.getBlue()-c2.getBlue()) < maxColorDiff;

        //do this boolean for all the other colors, then return red and blue and green
        //if all return true, then colorMatch returns true

        //return true or false if colors are close enough

        return red && blue && green;
    }
    public ArrayList<Point> largestRegion() {
        // TODO: YOUR CODE HERE
        //take in all the regions populated by regionFinder, find the biggest region

        //this will serve as the brush

        int i=0;
        //initialize biggest region as first ArrayList in the queue

        ArrayList<Point> biggestRegion = regions.get(i);

        for (i=0;i<regions.size();i++) {

            //is the next region bigger? If so, name it the biggest
            if (regions.get(i).size()>biggestRegion.size()){

                biggestRegion = regions.get(i);

                //keep track of regions
                //System.out.println("current brush size is"+biggestRegion.size());
            }



        }
        //return the largest region for paint brushing
        return biggestRegion;
    }


    public void recolorImage() {
        // First copy the original
        recoloredImage = new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
        // Now recolor the regions in it
        //System.out.println("recolor hit");

        //loop over list of take recolored image and set RGB at the points from the region to the new color
        for (int i = 0; i< regions.size();i++){

            ArrayList<Point> toRecolor = regions.get(i);

            for (int j = 0;j<toRecolor.size();j++){

                Point pointToRecolor = toRecolor.get(j);
                //do a nice blue green

                recoloredImage.setRGB(pointToRecolor.x,pointToRecolor.y,(int)Math.random()*16700000);


            }

        }
    }
}