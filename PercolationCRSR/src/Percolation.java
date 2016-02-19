
/*----------------------------------------------------------------
 *  Author:        Yuqing Yang
 *  Written:       9/7/2015
 *  Last updated:  9/9/2015
 *
 *  Compilation:   javac HelloWorld.java
 *  Execution:     java HelloWorld
 *  
 *  Prints "Hello, World". By tradition, this is everyone's
 *  first program.
 *
 *  % java HelloWorld
 *  Hello, World
 *
 *----------------------------------------------------------------*/


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

	
public class Percolation {
	private WeightedQuickUnionUF wqu;
	private WeightedQuickUnionUF backwash;
	
	private int virtualTop; //a site connected with all the opened sites in the first row
	private int virtualBottom;//a site connected with all the bottom sites in the last row
	private int N; //N-by-N grid
	private int[][] openList; //the site is opened: 1. if it's 0 the site is blocked
	
	/**
     * Convert the two dimensional percolation structure to one dimension 
     * Leave 0 as the vitualTopSite (both wqu and backwash), 
     * and N*N+1 as the vitualBottomSite (only backwash)
     * So, QuickUnion data structure has N*N+2 sites, row and col will be convert in [1,N*N]
     * 
     *   Throws a IndexOutOfBoundsException if i or j is smaller than 0 or 
     *   greater than grid size N.
     */
	private int xyTo1d (int row, int col) {
		illegalCheck(row, col);
		return (row - 1)*N + col;
	}
	
	/**
     *   Check if row and col is beyond the boundary.
     *      @param int row
     *      @param int col   
     */
	private void illegalCheck (int row, int col) {
		if (row <= 0 || row > N || col <= 0 || col > N) 
			throw new IndexOutOfBoundsException("row index i out of bounds");
	}
	
	
	/**
     *   Constructor.
     * 
     *      @param int N for NxN grid.
     *      
     */
	// create N-by-N grid, with all sites initially blocked
	public Percolation (int N) {
		if (N <= 0)
			throw new java.lang.IllegalArgumentException();
		this.N=N;//This is the must of N
		
		wqu = new WeightedQuickUnionUF (N*N + 1);
		backwash = new WeightedQuickUnionUF (N*N + 2);
//		wqu = new QuickFindUF (N*N + 1);
//		backwash = new QuickFindUF (N*N + 2);
		virtualTop = 0;
		virtualBottom = N*N + 1;
		openList=new int[N][N];

	}
	
	
	/**
	 * If the open site is in the first row, make it full by union with the virtualTop (wqu);
	 * If the open site is in the last row, make it full by union with the virtualBottom (backwash);
	 * Check the sites above, below, on the left and one the right site if possible.
	 * Union if  opened.
	 * 
	 * @param int i: openList[][] row index.
     * @param int j: openList[][] column index.
	 * 
	 */
	// open the site (row, col) if it is not open already
	public void open(int row, int col) {
		illegalCheck(row, col);
		int i = row - 1; //get the index of openList
		int j = col - 1; //get the index of openList
		openList[i][j] = 1; //open this site
		
		//If it's in the first row, union with virtualTop site 
		if (row == 1) {
			wqu.union(virtualTop, xyTo1d(row, col));
			backwash.union(virtualTop, xyTo1d(row, col));
		}
		//If it's in the last row, union with virtualBottom site in backwash
		if( row == N) {
			backwash.union(xyTo1d(row, col), virtualBottom);	
		}
		//check the left site if it's not in the first column
		if (col != 1 && openList[i][j - 1] == 1) {
			wqu.union(xyTo1d(row, col), xyTo1d (row, col - 1));
			backwash.union(xyTo1d(row, col), xyTo1d (row, col - 1));
		}
		//check the right sit if it's not in the last column
		if (col != N && openList[i][j + 1] == 1) {
			wqu.union(xyTo1d(row, col), xyTo1d(row,col + 1));
			backwash.union(xyTo1d(row, col), xyTo1d(row,col + 1));	
		}
		//check the site below it if not in the last row
		if (row != N && openList[i+1][j] == 1) {
			wqu.union (xyTo1d(row, col), xyTo1d(row+1,col));
			backwash.union (xyTo1d(row, col), xyTo1d(row+1,col));
		}
		//check the above site if not in the first row
		if (row != 1 && openList[i-1][j] == 1) {
			wqu.union (xyTo1d(row, col), xyTo1d(row-1,col));
			backwash.union (xyTo1d(row, col), xyTo1d(row-1,col));
		}	
		
	}
	
	/**
     *   Returns if the site is open by checking openList values.
     *
     *   Throws a IndexOutOfBoundsException if row or col is smaller than 1 or 
     *   greater than grid size N.
     */
	// is the site (row, col) open?
	public boolean isOpen (int row, int col)  {
		illegalCheck(row, col);
	//	System.out.println(openList[row][col] == 1);
		return openList[row - 1][col - 1] == 1;
	}
	
	/**
     *   Returns if the site is connected to virtualTop element.
     *
     *   Throws a IndexOutOfBoundsException if row or col is smaller than 1 or 
     *   greater than grid size N.
     */
	
	// is the site (row, col) full?
	public boolean isFull (int row, int col)  {
		illegalCheck(row, col);
		return wqu.connected(xyTo1d(row,col),virtualTop);
	}
	
	/**
     *   Count the number of opened sites by openList[][] == 1
     *   
     
	// number of open sites
	public int numberOfOpenSites() {
		int count=0;
		for (int i = 0; i < N; i++) 
			for (int j = 0; j < N; j++) {
				if (openList[i][j] == 1)
					count++;
		}
		return count;
		
	}*/
	
	/**
     *   If Percolation object percolates, return true by testing if virtualTop
     *   and virtualBottom is connected in backwash. 
     */
	// does the system percolate?
	public boolean percolates() {
		
		return backwash.connected(virtualTop, virtualBottom);
	}
	/*
	public static void main(String[] args) {
		Percolation perc= new Percolation(10);
		perc.open(2,2);
		boolean o=perc.isOpen(2, 2);
		System.out.println(perc.isFull(2,2));
		
		StdOut.println("finished main");
		
	}
	*/
	
}