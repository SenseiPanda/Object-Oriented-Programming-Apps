/**
 * Short assignment 1 creates a blob that changes
 * direction randomly every 10 moves
 * Define a new subclass of Blob; you can choose the name.
 * Give it instance variables indicating the number of steps between velocity changes, and how many of those
 * it has currently gone since the last change.
 * Define a constructor that takes initial x and y values and invokes the super constructor with them.
 * The constructor should also use Math.random() to set the number of steps between velocity changes to be a random number between 12 and 30 (inclusive).
 * Override the step method so that random new values are assigned to dx and dy each time the required number of
 * steps has been taken.
 * (Optional) Modify the BlobGUI to allow creation of an instance of your class.
 */

public class Blobfish extends Blob {
    private double n_steps, steps;
    public Blobfish (double x, double y) {
        super(x, y);
        double n_steps=0, steps=0;
        this.n_steps = n_steps;
        this.steps =steps;

    }
// now change dx and dy  AFTER # of steps have been taken
    @Override
    public void step() {
        n_steps = (int) (Math.random()*(30 - 12) + 12);
        steps += 1;
        // if statement testing if # of steps have been taken, if so, move the blob
        if (n_steps == steps) {
            dx = 2 * (Math.random() - 0.5);
            dy = 2 * (Math.random() - 0.5);
            //now reset steps to 0 so blob can keep moving
            steps =0;
        }

        x += dx;
        y += dy;
    }
}


