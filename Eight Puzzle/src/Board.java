public class Board {
	
	private void output() {
    	for (int m = 0; m < N*N; m++) {
			System.out.print(brd[m] + " ");
    	}
    	System.out.println();
    	System.out.println("------------------");
    	for (int n = 0; n < N*N; n++) {
			System.out.print(brd[n] + " ");
    	}
    	System.out.println();   	
	}
	
	
	private int[] brd; //Board data type
	private int N; //the size of the board
	/**
	 * construct a board from an N-by-N array of tiles by 
	 * converting the double array to one array of N*N size
	 * (where tiles[i][j] = tile at row i, column j)
	 * @param tiles
	 */
    public Board (int[][] tiles) {
    	N = tiles.length; //get the size of the board
    	brd = new int[N*N];
    	int x = 0; 
    	for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            brd[x++] = tiles[i][j];
    }
                                            
    /**
     * return tile at row i, column j (or 0 if blank).
     * 
     * Throws a IndexOutOfBoundsException if the i or j is out of bounds.
     * @param i
     * @param j
     * @return the tile
     * 
     */
    public int tileAt (int i, int j) {
    	if (i < 0 || j < 0 || i > N- 1 || j > N - 1)
    		throw new java.lang.IndexOutOfBoundsException();
    	else return brd[i*N + j];
    }
    
    
    /**
     * board size N
     * @return the board size
     */
    public int size() {
    	return N;
    }
    
    
    /**
     * number of tiles out of place
     * @return
     */
    public int hamming() {
    	int num = 0; // number of tiles out of place
    	for (int i = 0; i < N*N; i++) {
    		if (brd[i] != i+1 && brd[i] != 0) {
    			num++;
    		}    		
    	}
    	//output();
    	return num;
    }
    
    /**
     * sum of Manhattan distances between tiles and goal
     * @return
     */
    public int manhattan() {
    	int num = 0; // number of tiles out of place
    	for (int i = 0; i < N*N; i++) {
    		int value = brd[i] - 1;//show the value it should be in N*N grid

    		//calculate Manhattan distance if not in the right place
    		if (value != i && brd[i] != 0) {
    			//calculate by adding the differences of row and col
    			num += Math.abs(value%N - i%N) + Math.abs(value/N - i/N);
    		}
    		
    	}
    	
    	//output();
    	return num;    	
    }
    
	/**
     * is this board the goal board?
     * @return
     */
    public boolean isGoal() {
    	int index = 0; //to tell if the indexth tile is in the right place
    	for (int i = 0; i < N*N; i++) {
    		if (brd[i] == i+1)
    			index++;
    	}
    	//if N*N-1 tiles are in right place and the last tile is blank, return true
    	if (index == N*N - 1 && brd[N*N - 1] == 0) 
    		return true;
    	else 
    		return false;
    }
    
    
    
    private int getInversion() {
    	int numinv = 0; //number of inversions
    	for (int i = 0; i < N*N; i++)
    		for (int j = i + 1; j < N*N; j++)
    			if(brd[j] != 0 && brd[i] != 0 && brd[i] > brd[j])
    				numinv++;
    	return numinv;
    }
    /**
     * is this board solvable?
     * @return
     */
    public boolean isSolvable() {
    	int numinv = getInversion(); //the number of inversions
    	int blankrow = findRow(0); //the row of the blank tile  
    	
//    	System.out.println("the inversion of size "+N+" is "+numinv+"The blank row is "+blankrow);
    	//calculate solvable for odd size and even size boards	
    	if((N%2 != 0 && numinv%2 == 0) || (N%2 == 0 && (blankrow + numinv)%2 != 0))
    		return true;
    	else
    		return false; 	

    }
    
    
    /**
     * does this board equal y?
     */
    public boolean equals(Object y) {
    	if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        
        int[][] that = (int[][]) y;
        int cumu = 0; //accumulate index for 
        for (int i = 0; i < that.length; i ++)
        	for (int j = 0; j < that.length; j++) {
        		if (that[i][j] == brd[i*N + j])
        			cumu++;
        	}
        if (cumu == that.length*that.length)
        	return true;
        else 
        	return false;
        
    }

    /**
     * Get a copy board of this board 
     * @return int[N][N] board
     */
    private int[][] getCopy () {   	
    	int[][] newboard = new int[N][N];
    	for (int i = 0; i < N; i++) //copy brd to the originalbrd in double array
    		for (int j = 0; j < N; j++) {
    			newboard[i][j] = brd[i*N + j];
    			newboard[i][j] = brd[i*N + j];
    		}
    	return newboard;
    }
    /**
     * return the column index of the tileValue
     * @param tileValue
     * @return the column index
     */
    private int findCol (int tileValue) {
    	int col = -1;
    	for (int i = 0; i < N*N; i++) {
    		if (brd[i] == tileValue)
    			col = i%N;
    	}
    	return col;
    }
    
    /**
     * return the row index of the tileValue
     * @param tileValue
     * @return the row index
     */
    private int findRow (int tileValue) {
    	int row = -1;
    	for (int i = 0; i < N*N; i++) {
    		if (brd[i] == tileValue)
    			row = i/N;
    	}
    	return row;
    		
    }
    
    /**
     * all neighboring boards
     * @return stack of neighboring boards
     */
    public Iterable<Board> neighbors() {
    	Stack<Board> brdstack = new Stack<Board>(); //contains neighboring boards
    	int blankrow = -1; //return the index of the blank tile
    	int blankcol = -1; //return the index of the blank tile	
    	blankrow = findRow(0); //get the row of blank square
    	blankcol = findCol(0); //get the column of blank square
    	
    	if(blankrow == -1 || blankcol == -1) //check if it has blank square
    		throw new java.lang.IndexOutOfBoundsException("No blank square found");
    	
    	//add the neighbor board    	
//    	System.out.println("The blankrow and col is "+blankrow +", "+blankcol);
    	
		
		//find neighbors by moving blank square to four directions and 
    	//push neighboring boards in brdstack
/*    	
    	if (blankrow != 0) {
        	int[][] neighborbrd = getCopy();	
    		neighborbrd[blankrow][blankcol] = neighborbrd[blankrow - 1][blankcol];
    		neighborbrd[blankrow - 1][blankcol] = 0;
    		
    		Board tempbrd = new Board(neighborbrd);
//    		System.out.println("1: "+neighborbrd[blankrow - 1][blankcol]);
//    		System.out.println(tempbrd.toString());
    		brdstack.push(tempbrd);
    	}
    	if (blankrow != N - 1) {
        	int[][] neighborbrd = getCopy();	
    		neighborbrd[blankrow][blankcol] = neighborbrd[blankrow + 1][blankcol];
    		neighborbrd[blankrow + 1][blankcol] = 0;
    		Board tempbrd = new Board(neighborbrd);
    		brdstack.push(tempbrd);
    	}
    	if (blankcol != 0) {
        	int[][] neighborbrd = getCopy();	
    		neighborbrd[blankrow][blankcol] = neighborbrd[blankrow][blankcol - 1];
    		neighborbrd[blankrow][blankcol - 1] = 0;
    		Board tempbrd = new Board(neighborbrd);
    		brdstack.push(tempbrd);
    	}
    	if (blankcol != N - 1) {
        	int[][] neighborbrd = getCopy();	
    		neighborbrd[blankrow][blankcol] = neighborbrd[blankrow][blankcol + 1];
    		neighborbrd[blankrow][blankcol + 1] = 0;
    		Board tempbrd = new Board(neighborbrd);
    		brdstack.push(tempbrd);
    	}
 */   	
    	if (blankrow != 0) {
        	int[][] neighborbrdTop = getCopy();	
    		neighborbrdTop[blankrow][blankcol] = neighborbrdTop[blankrow - 1][blankcol];
    		neighborbrdTop[blankrow - 1][blankcol] = 0;
    		
    		Board tempbrdTop = new Board(neighborbrdTop);
//    		System.out.println("1: "+neighborbrd[blankrow - 1][blankcol]);
//    		System.out.println(tempbrd.toString());
    		brdstack.push(tempbrdTop);
    	}
    	if (blankrow != N - 1) {
        	int[][] neighborbrdBtm = getCopy();	
    		neighborbrdBtm[blankrow][blankcol] = neighborbrdBtm[blankrow + 1][blankcol];
    		neighborbrdBtm[blankrow + 1][blankcol] = 0;
    		Board tempbrdBtm = new Board(neighborbrdBtm);
    		brdstack.push(tempbrdBtm);
    	}
    	if (blankcol != 0) {
        	int[][] neighborbrdLeft = getCopy();	
    		neighborbrdLeft[blankrow][blankcol] = neighborbrdLeft[blankrow][blankcol - 1];
    		neighborbrdLeft[blankrow][blankcol - 1] = 0;
    		Board tempbrdLeft = new Board(neighborbrdLeft);
    		brdstack.push(tempbrdLeft);
    	}
    	if (blankcol != N - 1) {
        	int[][] neighborbrdRight = getCopy();	
    		neighborbrdRight[blankrow][blankcol] = neighborbrdRight[blankrow][blankcol + 1];
    		neighborbrdRight[blankrow][blankcol + 1] = 0;
    		Board tempbrdRight = new Board(neighborbrdRight);
    		brdstack.push(tempbrdRight);
    	}
    	
    	
    	return brdstack;
    }
    
    /**
     * string representation of this board
     */
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	if (isSolvable()) {
    		sb.append(N);
    		sb.append("\n");
    		for (int i = 0; i < N; i++) {
    			for ( int j = 0; j < N; j++)
    				sb.append("   " + brd[i*N + j]);
    			sb.append("\n");
    		}
    	}
    	else
    		sb.append("Unsolvable puzzle");
    	
    	return sb.toString();
    		
    }

    /**
     * unit testing (not graded)
     * @param args
     */
    public static void main(String[] args) {
    	
    }
}