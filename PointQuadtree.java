import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A point quadtree: stores an element at a 2D position, 
 * with children at the subdivided quadrants
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2015
 * @author CBK, Spring 2016, explicit rectangle
 * @author CBK, Fall 2016, generic with Point2D interface
 * 
 */
public class PointQuadtree<E extends Point2D> {
	private E point;                            // the point anchoring this node
	private int x1, y1;                            // upper-left corner of the region
	private int x2, y2;                            // bottom-right corner of the region
	private PointQuadtree<E> c1, c2, c3, c4;    // children

	/**
	 * Initializes a leaf quadtree, holding the point in the rectangle
	 * A PQT is a region with four children, who either exist or are null
	 */
	public PointQuadtree(E point, int x1, int y1, int x2, int y2) {
		this.point = point;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	// Getters

	public E getPoint() {
		return point;
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}

	public int getX2() {
		return x2;
	}

	public int getY2() {
		return y2;
	}

	/**
	 * Returns the child (if any) at the given quadrant, 1-4
	 *
	 * @param quadrant 1 through 4
	 */
	public PointQuadtree<E> getChild(int quadrant) {
		if (quadrant == 1) return c1;
		if (quadrant == 2) return c2;
		if (quadrant == 3) return c3;
		if (quadrant == 4) return c4;
		return null;
	}

	/**
	 * Returns whether or not there is a child at the given quadrant, 1-4
	 *
	 * @param quadrant 1 through 4
	 */
	public boolean hasChild(int quadrant) {
		return (quadrant == 1 && c1 != null) || (quadrant == 2 && c2 != null) || (quadrant == 3 && c3 != null) || (quadrant == 4 && c4 != null);
	}

	/**
	 * Inserts the point into the tree
	 * To insert point p, which is at (x,y)
	 * If (x,y) is in quadrant 1
	 * If child 1 exists, then insert p in child 1
	 * Else set child 1 to a new tree holding just p
	 * And similarly with the other quadrants / children
	 */
	public void insert(E p2) {
		// TODO: YOUR CODE HERE
		//insert the point into the tree
		double px = point.getX();
		double py = point.getY();
		double x = p2.getX();
		double y = p2.getY();
		E new1s;
		E new2s;

		//do I need to insert method to update x1, y1...etc. when inserting?
		//test if point is in quadrant one
		//c1 = new PointQuadTree (c1 has an upper left corner and lower right corner that are different
		//than parent)
		if (x >= px  && y<=py) {
			//if child exists, insert
			if (c1 == null) {
				new1s = get_1s(1,point);
				new2s = get_2s(1,point);
				c1 = new PointQuadtree<E>(p2, (int) new1s.getX(), (int) new1s.getY(), (int) new2s.getX(), (int) new2s.getY());

			}
			//if no child, insert it into the c1
			else {
				c1.insert(p2);
			}
		}
		//if point is in quadrant 2
		if (x <=px && y<=py) {
			//if child exists, insert
			if (c2 == null) {
				new1s = get_1s(2,point);
				new2s = get_2s(2,point);
				c2 = new PointQuadtree<E>(p2, (int) new1s.getX(), (int) new1s.getY(), (int) new2s.getX(), (int) new2s.getY());
			}
			//if the is a child, insert it into c2
			else {
				c2.insert(p2);
			}
		}
		//if point is in quadrant 3
		if (x <= px && y >= py) {
			//if child exists, insert
			if (c3 == null) {
				new1s = get_1s(3,point);
				new2s = get_2s(3,point);
				c3 = new PointQuadtree<E>(p2, (int) new1s.getX(), (int) new1s.getY(), (int) new2s.getX(), (int) new2s.getY());
			}
			//if the is a child, insert it into c3
			else {
				c3.insert(p2);
			}
		}
		//if point is in quadrant 4
		if (x >= px && y >= py) {
			//if child exists, insert
			if (c4 == null) {
				new1s = get_1s(4,point);
				new2s = get_2s(4,point);
				c4 = new PointQuadtree<E>(p2, (int) new1s.getX(), (int) new1s.getY(), (int) new2s.getX(), (int) new2s.getY());
			}
			//if the is a child, insert it into c4
			else {
				c4.insert(p2);
			}
		}
	}

	/**
	 * Finds the number of points in the quadtree (including its descendants)
	 */
	public int size() {
		// TODO: YOUR CODE HERE
		int count = 1;  //initialize the count
		if (c1 != null) {
			count = count + c1.size();
		}
		if (c2 != null) {
			count = count + c2.size();
		}
		if (c3 != null) {
			count = count + c3.size();
		}
		if (c4 != null) {
			count = count + c4.size();
		}
		return count;
	}

	/**
	 * Builds a list of all the points in the quadtree (including its descendants)
	 */
	public List<E> allPoints() {
		// TODO: YOUR CODE HERE
		List<E> my_points = new ArrayList<E>();
		addtoallPoints(my_points);
		System.out.println("allPoints method accessed");
		return my_points;
	}

	/**
	 * Helper for allPoints, adding points data to the list
	 */
	public void addtoallPoints(List<E> allPoints) {

		allPoints.add(point);

			if (c1 != null) {
				c1.addtoallPoints(allPoints);
			}
			if (c2 != null) {
				c2.addtoallPoints(allPoints);
			}
			if (c3 != null) {
				c3.addtoallPoints(allPoints);
			}
			if (c4 != null) {
				c4.addtoallPoints(allPoints);
			}
		}



	/**
	 * Uses the quadtree to find all points within the circle
	 *
	 * @param cx circle center x
	 * @param cy circle center y
	 * @param cr circle radius
	 * @return the points in the circle (and the qt's rectangle)
	 * We'll assume that we have methods to see if a point is inside a circle,
	 * and to see if a circle intersects a rectangle.
	 * To find all points within the circle (cx,cy,cr), stored in a tree covering rectangle (x1,y1)-(x2,y2)
	 * If the circle intersects the rectangle
	 * If the tree's point is in the circle, then the blob is a "hit"
	 * For each quadrant with a child
	 * Recurse with that child
	 */

	public List<E> findInCircle(double cx, double cy, double cr) {
		// TODO: YOUR CODE HERE
		List<E> circlePoints = new ArrayList<>();

		//1. check that the circle CAN intersect something, if outside return empty list
		if (Geometry.circleIntersectsRectangle(cx, cy, cr, x1, y1, x2, y2)) {

			//2. check to see if this point is in the circle, if it is, add to list
			if (Geometry.pointInCircle(point.getX(), point.getY(), cx, cy, cr)) {
				circlePoints.add(point);
			}
			//3. recurse to children c1, c2, c3, c4
			if (c1 != null) {
				List<E> localList = c1.findInCircle(cx, cy, cr); //returns a list of all the children
				for (E element : localList) {  //fancy way to write for each that Luc showed me
					circlePoints.add(element);
				}
			}
			if (c2 != null) {
				List<E> localList = c2.findInCircle(cx, cy, cr);
				for (E element : localList) {
					circlePoints.add(element);
				}
			}
			if (c3 != null) {
				List<E> localList = c3.findInCircle(cx, cy, cr);
				for (E element : localList) {
					circlePoints.add(element);
				}
			}
			if (c4 != null) {
				List<E> localList = c4.findInCircle(cx, cy, cr);
				for (E element : localList) {
					circlePoints.add(element);
				}
			}
		}

		return circlePoints; //return an empty list if there are no matches, or full if there are

		//call method recursively on the children
	}

	// TODO: YOUR CODE HERE for any helper methods

	//x1, y1 and x2, y2 will need to update their values relative
	// to which quadrant they're in
	public E get_1s(int quadrant, E location){
		//set initial location at 0,0
		//will return location of 'new' x1 & y1 for quadrant
		E new1s = (E) new Dot(0,0);
		if (quadrant ==1){
			new1s.setX(location.getX()) ;
			new1s.setY(y1);
		}
		if (quadrant ==2){
			new1s.setX(x1) ;
			new1s.setY(y1);
		}
		if (quadrant ==3){
			new1s.setX(x1) ;
			new1s.setY(location.getY());
		}
		if (quadrant ==4){
			new1s.setX(location.getX()) ;
			new1s.setY(location.getY());
		}

		return new1s;
}
	public E get_2s(int quadrant, E location){
		//set initial location at 0,0
		//will return location of 'new' x2 & y2 for quadrant
		E new2s = (E) new Dot(0,0);
		if (quadrant ==3){
			new2s.setX(location.getX()) ;
			new2s.setY(y2);
		}
		if (quadrant ==4){
			new2s.setX(x2) ;
			new2s.setY(y2);
		}
		if (quadrant ==1){
			new2s.setX(x2) ;
			new2s.setY(location.getY());
		}
		if (quadrant ==2){
			new2s.setX(location.getX()) ;
			new2s.setY(location.getY());
		}

		return new2s;
	}


	//a method should generally not be more than 30ish lines
	//so compartmentalize with helper methods to make it less than 30ish





}



