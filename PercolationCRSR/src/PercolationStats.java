import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
public class PercolationStats {
	private double[] threshold; // store threshold of each experiment
	private int t; // hold the T times 
	/**
	 * Calculate the threshold for one time percolation
	 * @param N : N*N grid
	 * @return double type threshold for one experiment
	 */
	private double thresholdCal (int N) {
		int row, col; //row and col of the N*N grid
		int count=0; // count the number of open
		Percolation perc = new Percolation(N);
		do {
			row = StdRandom.uniform(N) + 1;
			col = StdRandom.uniform(N) + 1;
			if (!perc.isOpen(row, col)) {
                count++;
                perc.open(row, col);
            }
		} while (!perc.percolates());
		
		return (double)count / (N*N);
	}
	
	/**
	 * perform T independent experiments on an N-by-N grid
	 * @param N : N*N grid
	 * @param T : T times independent experiments
	 * 
	 */
	// perform T independent experiments on an N-by-N grid
	public PercolationStats(int N, int T) {

		if (N < 1 || T < 1)
            throw new IllegalArgumentException();
		t=T;
		threshold = new double[T];
		for (int i = 0; i < T; i++) {
			threshold[i] = thresholdCal (N);
		}
		
	}
	/**
	 *  sample mean of percolation threshold
	 *  use StdStats.mean(double [])
	 * @return
	 */
	public double mean() {
		/*
		double sumThres = 0; //hold the sum of thredholds
		System.out.println("Now it's in mean(), threshold[].length = " + threshold.length);
		for (int i = 0; i < threshold.length; i++)
			sumThres += threshold[i];
		System.out.println("the sumThres / threshold.length ="+ sumThres/threshold.length);
		return sumThres / threshold.length;
		*/
		return StdStats.mean(threshold);
	}
	/**
	 *  sample standard deviation of percolation threshold
	 *  use StdStats.stddev(double [])
	 * @return
	 */
	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(threshold);
	}
	
	
	// low  endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - (1.96*stddev())/(Math.sqrt(t)); 
	}
	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + (1.96*stddev())/(Math.sqrt(t)); 
	}
	
	public static void main (String[] args) {
		Stopwatch sw = new Stopwatch();
		int tt,nn;
		tt=10;
		nn=2000;

		PercolationStats pcs=new PercolationStats(nn,tt);
	    System.out.printf("Total time: %f secs. (for N=%d, T=%d)",
	        sw.elapsedTime(), nn, tt);
	    System.out.println();
		System.out.println("in Main: stddev():  " + pcs.stddev());
		System.out.println("in Main: confidenceLow():  " + pcs.confidenceLo());
		System.out.println("in Main: confidenceHigh():  " + pcs.confidenceHi());
	}
	
}