/*************************************************************************
* Name: 
* Login: 
* Precept: 

* Partner Name: 
* Partner Login: 
* Partner Precept: 
*
* It is not necessary to include partners for this challenge.
* Make sure you include your partner on your submission for
* the collinear assignment.
* 
* Compilation:  javac RunIdentifier
* Example Execution: java RunIdentifier [input file]
*					   [input file] is optional
* 
* Prints all runs in an array above some minimum size
* 
* Starter code by: jhug@cs.princeton.edu
*
*************************************************************************/

public class RunIdentifier {

	//prints all runs in a that contain at least minNumPoints

	public static void identifyRuns(int[] a, int minNumPoints)
	{
		int[][] record = new int[a.length][3]; //the double array to store the element, begin index and end index
		int i = 0;
		
		while (i <= a.length - 1) {
			int indexbegin = i; //get the begin index
			record[indexbegin][0] = a[i]; //first element is to store the value
			record[indexbegin][1] = i; //second element is to store the begin index i
			
			if (a[i] == a[i+1]) {//calculate the length of subsequence
				while (i != a.length - 1 && a[i] == a[i+1]) {
					i = i + 1;
				}
				record[indexbegin][2] = i; // store the end index i
				i++;
			}
			else {
				record[indexbegin][2] = i; // store the end index i
				i++;
				
			}
			//Output type 1
			System.out.print("Run of "+ record[indexbegin][0] + "s: ");
			for (int j = record[indexbegin][1]; j <= record[indexbegin][2]; j ++)
				System.out.print(" "+ j );
			System.out.println();
			//Output type 2
			System.out.println("Run of "+ record[indexbegin][0] + 
					"s of length " + (record[indexbegin][2] - record[indexbegin][1]) +
					" starting at " + record[indexbegin][1]);
			
		}
	}

	/**
	 * If an input file is provided as a command line argument, the
	 * input from that file is used.   
	 * If no input file is provided, then input is taken from StdIn.
	 * @param args
	 */
	public static void main(String[] args)
	{
		int[] intArray;
		int minRunLength = 3;
		intArray = new int[]{5, 5, 5, 7, 8, 9, 9, 9, 10, 11, 12, 12, 12, 12, 15, 15};
/*
		if (args.length == 0)
		{
			System.out.print("No input file specified on command line, defaulting to a run length of 3. Collecting input from StdIn instead.\n\nTo specify an input file and run length instead, use 'java RunIdentifier [inputFile] [minRunLength]'\n\nEnter integer values separated by white space: ");

			intArray = StdIn.readInts();
		}
		else
		{
			if (args.length < 2)
				System.out.print("No run length specified on command line, defaulting to a run length of 3.\n\nTo specify a run length instead, use 'java RunIdentifier [inputFile] [minRunLength]'\n\n");
			else
				minRunLength = Integer.parseInt(args[1]);

			intArray = In.readInts(args[0]);
		}
*/
		identifyRuns(intArray, minRunLength);
	}
}