import java.awt.*;

import javax.swing.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Using a quadtree for collision detection
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2015
 * @author CBK, Spring 2016, updated for blobs
 * @author CBK, Fall 2016, using generic PointQuadtree
 */
public class CollisionGUI extends DrawingGUI {
	private static final int width=800, height=600;		// size of the universe

	private List<Blob> blobs;						// all the blobs
	private List<Blob> colliders;					// the blobs who collided at this step
	private char blobType = 'b';						// what type of blob to create
	private char collisionHandler = 'c';				// when there's a collision, 'c'olor them, or 'd'estroy them
	private int delay = 100;							// timer control

	public CollisionGUI() {
		super("super-collider", width, height);

		blobs = new ArrayList<Blob>();

		// Timer drives the animation.
		startTimer();
	}

	/**
	 * Adds an blob of the current blobType at the location
	 */
	private void add(int x, int y) {
		if (blobType=='b') {
			blobs.add(new Bouncer(x,y,width,height));
		}
		else if (blobType=='w') {
			blobs.add(new Wanderer(x,y));
		}
		else {
			System.err.println("Unknown blob type "+blobType);
		}
	}

	/**
	 * DrawingGUI method, here creating a new blob
	 */
	public void handleMousePress(int x, int y) {
		add(x,y);
		repaint();
	}

	/**
	 * DrawingGUI method
	 */
	public void handleKeyPress(char k) {
		if (k == 'f') { // faster
			if (delay>1) delay /= 2;
			setTimerDelay(delay);
			System.out.println("delay:"+delay);
		}
		else if (k == 's') { // slower
			delay *= 2;
			setTimerDelay(delay);
			System.out.println("delay:"+delay);
		}
		else if (k == 'r') { // add some new blobs at random positions
			for (int i=0; i<10; i++) {
				add((int)(width*Math.random()), (int)(height*Math.random()));
				repaint();
			}			
		}
		else if (k == 'c' || k == 'd') { // control how collisions are handled
			collisionHandler = k;
			System.out.println("collision:"+k);
		}
		else if (k=='0') {
			testStuff();
		}
		else { // set the type for new blobs
			blobType = k;			
		}
	}

	/**
	 * DrawingGUI method, here drawing all the blobs and then re-drawing the colliders in red
	 */
	public void draw(Graphics g) {
		// TODO: YOUR CODE HERE
		// Ask all the blobs to draw themselves.
		// Ask the colliders to draw themselves in red.
		for (int i = 0; i < blobs.size(); i++) {
			//is blob in colliders list? if so, make it red

			if (colliders != null && colliders.contains(blobs.get(i))) {
				g.setColor(Color.RED);
				System.out.println("A red blob is born");
				blobs.get(i).draw(g);
			} else {
				g.setColor(Color.BLACK);
				blobs.get(i).draw(g);
				//for each item in blobs list, have the items draw themselves

			}
		}
	}

	/**
	 * Sets colliders to include all blobs in contact with another blob
	 */
	private void findColliders() {
		// TODO: YOUR CODE HERE
		// Create the tree
		// For each blob, see if anybody else collided with it

		//make an empty tree to populate, give screen dimensions in parameters


		 PointQuadtree <Blob> blobtree = new PointQuadtree<Blob>(blobs.get(0), 0, 0, 800, 600);

		 //insert all existing blobs into blobtree
		for (int i=0; i<blobs.size();i++){
			blobtree.insert(blobs.get(i));
			System.out.println("Blobs inserted into blobtree");
		}

		//make a new place (ArrayList?) to store blobs that have collided
		colliders = new ArrayList<Blob>();

		//make a list to store all the Blobs' points, calling allPoints method from PointQuadTree

		List<Blob> blobPoints = blobtree.allPoints();
		for (int i = 0;i<blobPoints.size();i++){
			//create list of other blobs besides the one being looked at
			Blob blobpoint = blobPoints.get(i);
			System.out.println("Blobpoints have been stored");

			//increase radius by 50% to increase collision frequency
			List<Blob> others = blobtree.findInCircle(blobpoint.getX(),blobpoint.getY(),1.5*blobpoint.getR());
			//remove self from list
			if (others.size()<=1){ others.remove(0);}

			//test if x's and y's of another blob match up with
			//this blob's x's and y's
			for (int j = 0; j<others.size();j++ ){

				Blob testBlob = others.get(j);

				//if there is a point match, add blobs to collided blob list

				if (blobpoint.contains(testBlob.getX(), testBlob.getY())){
					colliders.add(testBlob);
					colliders.add(blobpoint);

				}

			}
		}


	}

	/**
	 * DrawingGUI method, here moving all the blobs and checking for collisions
	 */
	public void handleTimer() {
		// Ask all the blobs to move themselves.
		for (Blob blob : blobs) {
			blob.step();
		}
		// Check for collisions
		if (blobs.size() > 0) {
			findColliders();
			if (collisionHandler=='d') {
				blobs.removeAll(colliders);
				colliders = null;
			}
		}
		// Now update the drawing
		repaint();
	}
	public void testStuff(){
		Blob nemo = new Bouncer(200,100, width, height);
		Blob dory = new Bouncer (200,200, width, height);

		nemo.setGrowth(5);
		nemo.setVelocity(10,10);
		dory.setVelocity(50,0);

		blobs.add(dory);
		blobs.add(nemo);


	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CollisionGUI();
			}
		});
	}
}
