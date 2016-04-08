/*************************************************************************
 * Name: Yuqing Yang
 * Login:
 * Precept: 
 *
 * Compilation:  javac Point.java
 *
 * Description: A data type for points. draw, drawTo, slopeTo, compareTo,
 * to String
 *
 *************************************************************************/
import java.util.Comparator;

/**
 * A data type and a SLOPE_ORDER comparator for points
 * @author Yuqing
 *
 */
public class Point implements Comparable<Point> {
   
	private final int x;      // x coordinate
    private final int y;      // y coordinate
    /**
	 * Define a compare method (comparator c in mergesort) by the SLOPE_ORDER
	 * @param Point p1
	 * @param Point p2
	 * 
	 * see compare(Point p1, Point p2) for details
	 */
	public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
		/**
		 * Compare slope(p0,p1) with slope (p0,p2)
		 * @param p1 Point
		 * @param p2 Point
		 * @return negative if slope(p0,p1) is smaller than slope(p0,p2)
		 */
		public int compare(Point p1, Point p2) {
	        double s1 = slopeTo(p1);
	        double s2 = slopeTo(p2);
	        if (s1 < s2) // if s1==s2 p1 and p2??
	        	return -1;
	        else if (s1 > s2)
	        	return 1;
	        else 
	        	return 0;
	    }
	};

   /**
    * construct the point (x, y)
    * @param x
    * @param y
    */
	public Point(int x, int y) {
		
		this.x = x;
		this.y = y;
	}

   /**
    * plot this point to standard drawing
    */
	public   void draw() {
		StdDraw.point(x, y);
	}
   
	/**
	 * draw the line segment from this point to that point
	 * draw line between this point and that point to standard drawing
	 * @param that
	 */
	public   void drawTo(Point that) {
		StdDraw.line(this.x, this.y, that.x, that.y);
	}
	
	/**
	 * the slope between this point and that point
	 * @param that: the point (x1, y1)
	 * @return the slope between the invoking point (x0, y0) 
	 *  and the argument point (x1, y1),
	 */
	public double slopeTo (Point that) {
		if(that.x == this.x) {
			if (that.y != this.y)
				return Double.POSITIVE_INFINITY ;
			else 
				return Double.NEGATIVE_INFINITY;
		}
		else  {
			if (that.y == this.y)
				return (1.0 - 1.0) / 1.0;
			else 
				return (double)(that.y - this.y)/(that.x - this.x);
		}
	}
	
	
	/**
    * is this point lexicographically smaller than that point?
    *  comparing y-coordinates and breaking ties by x-coordinates
    *  if y0<y1 OR  y1==y0,x0<x1 means this point is smaller that that point
    *  @return -1 if this point is smaller than that point 
    *  @return 1 if in other circumstances
    */
	public  int compareTo (Point that) {
		if (this.y == that.y && this.x == that.x)
			return 0;
		else if (this.y < that.y || (this.y == that.y && this.x < that.x))
			return -1;
		else {
			return 1;
		}
		
	}
	
	/**
	 * string representation
	 * @return string representation of this point
	 */
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}