
public class drawline {
	public static void main (String[] args) {
		
	/**
	 * From Files
	 */
		/*	 	
	In in = new In(args[0]);
    int N = in.readInt();
    Point[] points = new Point[N];
    for (int i = 0; i < N; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }
     
	//Draw points
	StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
        
  
    */	
    	
    
    
     //From Points I defined      
    Point[] points=new Point[8];
	points[0]=new Point(1,2);
	points[1]=new Point(2,4);
	points[2]=new Point(5,10);
	points[3]=new Point(3,6);
	points[4]=new Point(7,14);
	points[5]=new Point(6, 2);
	points[6]=new Point(12, 4);
	points[7]=new Point(9, 18);
	int N=8;
	
	StdDraw.setXscale(0, 20);
    StdDraw.setYscale(0, 30);
	 	
	
	
    for (int i = 0; i < N; i++)
    	points[i].draw();
    

    /**
     * Calculate time
     */
    Iterable<PointSequence> qps = new Queue<PointSequence>();
    Stopwatch sw = new Stopwatch();
    //  FastCollinearPoints fcp = new FastCollinearPoints(points); 
    BruteCollinearPoints fcp = new BruteCollinearPoints(points);	    
//   	System.out.println("in main: numberOfSegments = "+fcp.numberOfSegments());    	
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
