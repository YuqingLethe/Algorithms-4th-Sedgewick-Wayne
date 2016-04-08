
public class Solver {
	
	private MinPQ<SearchNode> pq = new MinPQ<SearchNode>(); 
	private Queue<SearchNode> searchNodeSolution = new Queue<SearchNode>(); //store solution SearchNode
	private Board initialBoard; //initialBoard we are solving
	
	/**
	 * Give a SearchNode class that contains the Board, Moves have been made
	 *  , the priority and previous SearchNode
	 * @author Yuqing
	 *
	 */
	private class SearchNode implements Comparable<SearchNode> {
		Board board;
		int moves;
		SearchNode prevNode;	

		/**
		 * Constructor of SearchNode class
		 * @param brd current board
		 * @param move number of moves have been made
		 * @param prev the previous SearchNode 
		 */
		public SearchNode (Board brd, SearchNode prev) {
			this.board = brd;
			this.prevNode = prev;
			if (prev == null)
				this.moves = 0;
			else 
				this.moves = prev.moves + 1;
			
		}
		/**
		 * Get the priority of the board
		 * @return
		 */
		private int priority() {
			return board.manhattan() + moves;
		}
		
		
		/**
		 * Compare the SearchNode with the anchor one by priority value.
		 */
		public int compareTo(SearchNode that) {
	        if (this.priority() < that.priority()) 
	        	return -1;
	        else if (this.priority() > that.priority())
	        	return 1;
	        else 
	        	return 0;
	    }
		
	}
	
	private void outputSN(SearchNode sn) {
		System.out.println("------------");
		System.out.println(sn.board.toString());
		System.out.println("Moves = "+ sn.moves + " Prio = " + sn.priority());
	}
	
	/**
	 * Return the neighbor SearchNode in a stack
	 * @param parentNode
	 * @return
	 */
	private Stack<SearchNode> findNbrSearchNode (SearchNode parentNode) {
		Iterable<Board> stackNbrBoards = new Stack<Board>();
		stackNbrBoards = parentNode.board.neighbors();
		Stack<SearchNode> stackSearchNodes = new Stack<SearchNode>();
		
		for (Board nb : stackNbrBoards) {
			if (nb != initialBoard) {
				SearchNode tempSearchN = new SearchNode(nb, parentNode);
				stackSearchNodes.push(tempSearchN);
				outputSN(tempSearchN);
			}				
		}
		return stackSearchNodes;
	}

	/**
	 * find a solution to the initial board (using the A* algorithm).
	 * Result will be stored in searchNodeSolution queue;
	 * @param initial
	 */
	public Solver(Board initial) {
		SearchNode initSearchNd = new SearchNode(initial, null);
		initialBoard = initial;
		pq.insert(initSearchNd);
		
		//output
		System.out.println("======Original SearchBoard==========");
		outputSN(initSearchNd);
		
		//find the Goal SearchNode
		while (!pq.min().board.isGoal()) {
			Stack<SearchNode> stackSNforNbrs = new Stack<SearchNode>();
			System.out.println("======NextLevel SearchBoard==========");
			
			//Insert all neighbors in the priority queue
			stackSNforNbrs = findNbrSearchNode(pq.delMin());
			while(stackSNforNbrs.size() != 0) {
				pq.insert(stackSNforNbrs.pop());					
			}
		}
		
		//Trace back the search node
		SearchNode snInSolution = pq.min();
		Stack<SearchNode> stacksolution = new Stack<SearchNode>();
		while(snInSolution.prevNode != null) {
			stacksolution.push(snInSolution);
			snInSolution = snInSolution.prevNode;
		}
		stacksolution.push(initSearchNd);
		
		//Make it reverse order
		while (!stacksolution.isEmpty())
			searchNodeSolution.enqueue(stacksolution.pop());

	}
	
	/**
	 * min number of moves to solve initial board
	 * @return
	 */
    public int moves() {
    	return searchNodeSolution.size() - 1;
    }
    
    /**
     * sequence of boards in a shortest solution
     * @return
     */
    public Iterable<Board> solution() {
    	Queue<Board> boardsolution = new Queue<Board>();
    	for(SearchNode sn : searchNodeSolution) {
			boardsolution.enqueue(sn.board);
		}
    	return boardsolution;
    }
    
    
    
	public static void main(String[] args) {

	    // create initial board from file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    int[][] tiles = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++) {
	            tiles[i][j] = in.readInt();
	        }
	    
	    
	    Board initial = new Board(tiles);
	    /*
	    System.out.println(initial.isSolvable());
	    System.out.println(initial.toString());
	    Iterable<Board> newbrdstack = new Stack<Board>();
	    newbrdstack = initial.neighbors();
	    System.out.println(newbrdstack.toString());
	     */	    
	    
	    // check if puzzle is solvable; if so, solve it and output solution
	    if (initial.isSolvable()) {
	        Solver solver = new Solver(initial);
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }

	    // if not, report unsolvable
	    else {
	        StdOut.println("Unsolvable puzzle");
	    }
	}
	
}
