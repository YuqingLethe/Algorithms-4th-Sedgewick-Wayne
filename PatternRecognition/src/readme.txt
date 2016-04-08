/******************************************************************************
 *  Pattern Recognition readme.txt template
 *****************************************************************************/

Name:             Yuqing Yang
NetID:            yuqyang@clarku.edu
Precept:          

Partner name:     
Partner login:    
Partner precept:

Hours to complete assignment (optional): 14 hours



/******************************************************************************
 *  Step 1.  Explain *briefly* how you implemented brute force.
 *           Describe how you implemented compareTo() and the
 *           slopeTo() methods in the Point data type.
 *****************************************************************************/
compareTo() in Point data type: 
	We return 0 if this.Point and that.Point are the same point. We compare
them by checking their y values and their x values. If this.y < that.y or this.x
< that.x when this.y == that.y, the return will be -1 to indicate that this.point is
smaller than that.point. In other circumstances, we return 1 which means this.
point is larger than that.point. In summary, the one in the position of left
bottom to the other one will be considered smaller.


slopeTo() in Point data type:
	slopeTo() are devided by two cases mainly, based on whether x1 == x2.
If yes, we give Positive_Infinity to the vertical lines and give Negative_Infinity
to the point and itself (y1 == y2).If x1 != x2, we return a Positive_Zero to the horizontal
lines (y1 == y2), and for other general conditions, we return the slope.


Brute force:
	In Brute, I use three nested for loop to get three points (a b c), which will form two
segments(ab bc), then check their slopes to see if they can form to one segment(abc). If yes,
use another for loop to find the 4th point(d) and see if the third segment(cd) have the
same slope with one of the other two (e.g. check slope of cd and bc). If the two
segments (ab bc) have different slope, we moved to the next element without
searching for the 4th point(d).    
	In the end, if we find the same slope of each three segments, that means
 we have four points in a line, we make these four point as PointSequence data 
structure and enqueue it to the Queue.



/******************************************************************************
 *  Step 2.  Explain *briefly* how you implemented the sorting solution.
 *           What steps did you do to avoid printing permutations
 *           and subsegments?
 *****************************************************************************/
	My copy of original point array is called CP here. For each point p in CP, 
I will do a calcollinear() method to find the segments contained p, and enqueue 
them to the final result segQueue. So, roughly, I have three parts to finish this work. 
	First, sorting the CP by SLOPE_ORDER, find the begin index and finish index of each segment.
	Second, put the points in the point[] data structure and then to PointSequence data structure 'pointseg'
	Third, check if the PointSequence 'pointseg' is smaller than minLength, 
if not, check every PointSequence that already in segQueue to see if 'pointseg' 
is already existed. If not, enqueue it to segQueue.


	To avoid Permutations, I will give a natural sort of the 'pointseg', so
segments composed by same points will be stored by natural order, so they are 
same. Before enqueue to the segQueue, I can check if the segment (PointSequence)
is already in the segQueue by calling compareTo(PointSequence that) method.

	To avoid sebsegments, for each point p, I sort the array by SLOPE_ORDER
of p, so that points having the same slope with p will be put together. So I just
find the begin index and end index for these points that have the same slope.
Till now, all points in this segment have been found. Before enqueue it to the
segQueue, all these points will be sorted by natural order, then we find the begin
and end point easily;therefore, there is no subsegment exists.



/******************************************************************************
 *  Empirical    Fill in the table below with actual running times in
 *  Analysis     seconds when reasonable (say 180 seconds or less).
 *               You can round to the nearest tenth of a second.
 *
 *  Estimate (using tilde notation) the running time (in seconds) of
 *  your two main functions as a function of the number of points N.
 *
 *  Explain how you derive any exponents.
 *****************************************************************************/

    
      N       brute       sorting
 ---------------------------------
    400		0.17		0.172
    800		1.076		0.508
   1600		7.881		0.929
   3200		67.758		2.596
   6400		543.102		9.718
   12800			40.982

   1000		1.84		0.611
   2000		16.795		1.249
   4000		135.236		3.929
   8000				15.319



Brute:    ~ N^3    because the running time increase nealy eightfold T(2N)/T(N) ~ 8
because I have a if statment of the two slopes (formed by three point) are the same,
so in most circumstances, only three for loops have been involved. 

Sorting:  ~ N^2 running time increase nealy fourfold 




/******************************************************************************
 *  Theoretical   Give the order of growth of the worst-case running
 *                time of your programs as a function of N. Justify
 *                your answer briefly.
 *****************************************************************************/

Brute: N^4 
the worst case is every four points could formed to one segments,
four nested loop have to be reached every time.

Sorting: (N^2)*logN    

Even through I have three loops which seems to be N^3, but in fact, the two
whiles just traverse N points once because the index i increases in both whiles. So, the
worst case comes from Sorting. We use merge sort for every point in the array.
So, n*log(n) will be occured N times.

But LogN running time increases nearly by constant, so it's unobvious in the
data above.



/******************************************************************************
 *  Known bugs / limitations. For example, if your program prints
 *  out different representations of the same line segment when there
 *  are 5 or more points on a line segment, indicate that here.
 *****************************************************************************/
Till now it seems not.


/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/
TA Lucas told me to use Stack to achieve the Iterable<PointSequence> property.
TA Hoang helped me with optimizing my data structure in the following aspect:
	To store the points that can form one segment, I first use ArrayList<Point>
because it can be resize if points added. But to natural sort it, I have to 
change it back to Point[], which is very bothering. Hoang gave me the idea that
record the first and the last index of the points' position, and then create 
Point[] after that, because at that time, we know the size of Point[] already.
	To comparing the just got PointSequence 'pointseg' with PointSequences
 already in segQueue, I don't know how to get item in queue without destroy it.
So I use tempps = segQueue.dequeue() and enqueue() after comparison. Hoang told
me I can use enhanced for loop (PointSequence seg: segQueue) to reach every item
without destroy the segQueue.
	Another aspect will be describe in the following question: 
Describe any serious problems you encountered. 


/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/
	First, the requirement and checklist of slopeTo() return values have to be read very
carefully. I didn't pay enough attention on that and in FastCollinear, after 
SLOPE_ORDER sorting by p, my p often randomly located in the array, not in the 
first place. And that brought me very complicated algorithm to solve this problem.
TA Hoang told me it may be the slopeTo() problem and he helped me with these return values.

	Second, My calCollinear() in FastCollinearPoint have three main steps 
(been stated in Step 2 question)to
finish the function, which makes 60 lines of code and very bad readibility.
However, if partite them into different methods, the code in additional private 
methods are just 'if 'and 'while' statments; besides, we will have more class-level 
parameters to transfer between these methods, which increase the memory. 

So should I partite them?


/******************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 *****************************************************************************/
I have no partner in this assignment



/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/
The problem we discussed in Lab seems very misleading in FastCollinearPoint,
because we can avoid anagrams and substring problem in the very beginning, 
 
But that is a valuable experience because we get to the best algorithm step-by-step.
I like this kind of questions in discussion very much.

We have a suggested Programming challenge ---- RunIdentifier class in checklist,
this helped me a lot with the fast algorithm. If this is not offered, I might not 
come up with this algorithm. 