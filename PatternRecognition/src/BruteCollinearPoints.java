/*************************************************************************
 * Name: Yuqing Yang
 * Login:
 * Precept: 
 *
 * Compilation:  javac BruteCollinearPoints.java
 *               java BruteCollinearPoints
 *
 * Description: To find the segments whose length is larger or equal to 4.
 * Use Point.java and PointSequence.java 
 *
 *Should check if we already have something in segQueue, or initialize the
 *segQueue in calculation() every time we call it.
 *
 *************************************************************************/
import java.util.Arrays;


public class BruteCollinearPoints {
	private Point [] copypoints; // the array of defensive copy
	private Queue<PointSequence> segQueue; // the queue to store PointSequence item
	private int n; //Length of segments in length 4
	
	/**
	 * makes a defensive copy of the array of points, 
	 * intialize segQueue;
	 * @param points
	 */
	public BruteCollinearPoints(Point [] points) {
		copypoints = new Point[points.length];
		segQueue = new Queue<PointSequence>();
		for (int i = 0; i < copypoints.length; i++)
			copypoints[i] = points[i];
	}
	
	/**
	 * returns the number of total points in the array
	 * @return the number of total points in the array
	 */
	public int numberOfPoints() {
		return copypoints.length;
	}
	
	/**
	 * Find out the 4 points that can form one segment. Find three points first and check
	 * if they can form a line, if yes, go on get the fourth one. If not, try other
	 * combinations. Once we get four points and form three segments, and slope of the
	 * three segments are same, we consider we got a segments that contains 4 points.
	 * 
	 * @return Iterable<PointSequence> Enqueue to the queue for four points each time.
	 */
	private Iterable<PointSequence> calculation () {	
		Arrays.sort(copypoints);
		Point[] pointArray = new Point[4];
		n = 0;
		PointSequence pointseg;//to store the points in one segments in length 4
		
		//If they can be sorted by comparing the slope 
	
		for (int i = 0; i < copypoints.length - 3; i++) {
			for (int j = i + 1; j < copypoints.length - 2; j++) {
				
				for (int k = j + 1; k < copypoints.length - 1; k++) {
					if (copypoints[i].slopeTo(copypoints[j]) == copypoints[j].slopeTo(copypoints[k])) {
						for (int l = k + 1; l < copypoints.length; l++)
							if (copypoints[j].slopeTo(copypoints[k]) == 
							copypoints[k].slopeTo(copypoints[l])) {
								pointArray[0] = copypoints[i];
								pointArray[1] = copypoints[j]; 
								pointArray[2] = copypoints[k];
								pointArray[3] = copypoints[l]; 
								
								pointseg=new PointSequence(pointArray);
								segQueue.enqueue(pointseg);					
								n++;
							}	
						
					}//end if in k
					else continue;
						
				}//end k
					
			}//end j
		}//end i
	return segQueue;
	}
	
	
	/**
	 * returns the number of segments of length 4
	 * @return the number of segments of length 4
	 */
	public int numberOfSegments() {
		Queue<PointSequence> nofs= (Queue<PointSequence>) calculation ();
		return nofs.size();
	}
	
	/**
	 * Get the result from calculation() and return it directly
	 * @return an iterable of segments of length 4
	 */
	public Iterable<PointSequence> segments() {		
		return calculation ();
	}
	
	
	/**
	 * draws all 4 point segments in file
	 * @param args
	 */
	public static void main(String[] args) {
		//From files	 	
    	In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }	
    	
    	/**
    	 * Draw points
    	 */
    	StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
            
        for (int i = 0; i < N; i++)
        	points[i].draw();
        

        /**
         * Calculate time
         */
        Iterable<PointSequence> qps = new Queue<PointSequence>();
        Stopwatch sw = new Stopwatch();
        BruteCollinearPoints fcp = new BruteCollinearPoints(points); 
//      System.out.println("in main: numberOfSegments = "+fcp.numberOfSegments());    	
        qps = fcp.segments();
        //Print out the time
        System.out.printf("Total time: %f secs. (for N=%d)", sw.elapsedTime(), N);	
        
        
        /**
         * Draw lines
         */
        for (PointSequence seq : qps) {
        	seq.draw();
    		System.out.println(seq.toString());
        }	
        // display to screen all at once
        StdDraw.show(0);
		
	}

}
