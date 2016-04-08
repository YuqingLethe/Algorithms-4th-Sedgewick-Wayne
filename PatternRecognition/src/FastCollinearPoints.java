/*************************************************************************
 * Name: Yuqing Yang
 * Login: yuqyang
 * Precept: Yoki
 *
 * Compilation:  javac FastCollinearPoints.java
 *               java FastCollinearPoints
 *
 * Description: To find the segments whose length is larger or equal to minLength.
 * No duplicate, subsegment and permutation existed.
 * Use Point.java and PointSequence.java . Import util.Arrays
 * 
 * Might have the last point problem!??!??
 *
 *************************************************************************/
import java.util.Arrays;

public class FastCollinearPoints {
	private Point [] cp; // the array of defensive copy
	private Queue<PointSequence> segQueue; // the queue to store PointSequence items
	
	/**
	 * makes a defensive copy of the array of points
	 * @param points
	 */
    public FastCollinearPoints(Point [] points) {
		cp = new Point[points.length];
		segQueue = new Queue<PointSequence>();
		for (int i = 0; i < cp.length; i++)
			cp[i] = points[i];
		
    }
    
    
    /**
     * returns the number of total points in the array
     * 
     * @return
     */
    public int numberOfPoints() {   
    	return cp.length;
    	
    }
    
    
    /**
     * We find the segments related with specific Point P
     * @param p the specific Point that we calculate segments pass P
     * @param minLength the minimum length of segments
     * 
     * The function will add PointSequence in segQueue if no duplicate PointSequence
     * in the Queue
     */
    private void calcollinear (Point p, int minLength) {
    	PointSequence pointseg;//to store the points in one segments in length larger than minLength
    	int i = 0; //the point index from 0
    	
    	Arrays.sort(cp, p.SLOPE_ORDER); //Sort the cp by point p
    	
    	while (i <= cp.length - 2) {
    		int starti = i; //store which point the segment begin with
    			
    		
    		//find two points in the same loop to form a segment
			if(p.slopeTo(cp[i]) == p.slopeTo(cp[i + 1])) {
				//While loop to see if any other points in this segment
				while (i != cp.length - 1 && p.slopeTo(cp[i]) == p.slopeTo(cp[i + 1])) {
					i++;
				}
				i++;//add the last point in the segment
				

				//Create a Point[] array to store the segments
				Point[] tempoints = new Point[i - starti + 1];
				int tempindex = 0;
				for (int j = starti; j < i; j++) {					
					tempoints[tempindex] = cp[j];	
					tempindex++;
				}
				tempoints[i-starti] = p;
				
				
				//add the points array that larger or equal to minLength to the segQueue
				if (tempoints.length >= minLength) {
					Arrays.sort(tempoints);  
					//put the point[] in PointSequence and enqueue it to segQueue
					pointseg = new PointSequence(tempoints);						
				
					//check if we have the same PointSequence already in segQueue, if not, enqueue
					//TA Huong tell me to use foreach 
					
					if (segQueue.size() == 0)
						segQueue.enqueue(pointseg);
					else {
						byte duexist = 0; //to tell us if we have a duplicate PointSequence in the Queue
						for (PointSequence seq : segQueue) {
							if (pointseg.compareTo(seq) == 0) {
								duexist = 1;
								break; //break statement has no effect on if statements. It only works on switch, for, while and do loops
							}
						}
						if (duexist == 0)
							segQueue.enqueue(pointseg);
					}//end for loop to enqueue
				}
			}
			//If this point and the next point are not equal in slope,index move to the next point	
			else {
				i++;	
			}
			
    	}//end While loop in this method
    	
    }
    
    
    
    private void calculation (int minLength) {
    	//Sort the points  	    	
    	for (int i = 0; i < cp.length; i++) {   		
    		calcollinear(cp[i], minLength);    		
    	}
		
    }

    
    /**
     *  returns the number of segments of length minLength or more
     * @param minLength
     * @return
     */
    public int numberOfSegments (int minLength) {
    	if(segQueue.isEmpty()) 
    		calculation (minLength);
    	else {
    		System.out.println("The numberOfSegments() have been called");	
    	}
    	return segQueue.size();
    }
    
    
    /**
     * 
     * @param args
     */
    public Iterable<PointSequence> segments (int minLength) {    	
    	if(segQueue.isEmpty()) 
    		calculation (minLength);
    	else {
    		System.out.println("The segments() have been called");
    		
    	}
    	output();
    	return segQueue;
    }
    
    private void output() {
    	System.out.println("the segQueue of this file is:");
    	System.out.println(segQueue.toString());
    }
    
    /**
     * draws all maximal length segments of length 4 or more
     * @param args
     */
    public static void main (String[] args) {
    	
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
        FastCollinearPoints fcp = new FastCollinearPoints(points); 
        System.out.println("in main: numberOfSegments = "+fcp.numberOfSegments(4));    	
        qps = fcp.segments(4);
        //Print out the time
        System.out.printf("Total time: %f secs. (for N=%d)", sw.elapsedTime(), N);	
        
        
        /**
         * Draw lines
         */
        for (PointSequence seq : qps) {
        	seq.draw();
    		System.out.println(seq.toString());
        }
        System.out.printf("After draw time: %f secs. (for N=%d)", sw.elapsedTime(), N);	
        // display to screen all at once
        StdDraw.show(0);

    }
}