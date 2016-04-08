
public class PatternChecker {
	public static void main(String[] args) {
        // header
        StdOut.printf("%-25s %7s %8s\n", "filename", "moves", "time");
        StdOut.println("------------------------------------------");

        // for each command-line argument
        for (String filename : args) {
            // read in the board specified in the filename
        	In in = new In(args[0]);
            int N = in.readInt();
            Point[] points = new Point[N];
            for (int i = 0; i < N; i++) {
                int x = in.readInt();
                int y = in.readInt();
                points[i] = new Point(x, y);
            }
            /**
             * Calculate time
             */
            System.out.println("--------"+ filename+"----------");
            Iterable<PointSequence> qps = new Queue<PointSequence>();
            FastCollinearPoints fcp = new FastCollinearPoints(points); 
            System.out.println(" numberOfSegments = "+fcp.numberOfSegments(4));    	
            qps = fcp.segments(4);
            
            for (PointSequence seq : qps) {
            	seq.draw();
        		System.out.println(seq.toString());
            }
          
        }
    }
}
