/******************************************************************************
 *  Name:       Yuqing
 *  NetID:      yuqyang        yuqyang@clarku.edu
 *  Precept:    
 *
 *  Partner Name:       
 *  Partner NetID:      
 *  Partner Precept:    
 *
 *  Hours to complete assignment (optional):8h
 *
 ******************************************************************************/


/******************************************************************************
 *  Describe concisely your algorithm to compute the horizontal and
 *  vertical seam. 
 *****************************************************************************/
Take Vertical seam for example.

Calculate accumulated energies by adding the pixel to three pixels in position of lower left, straight below, and lower right. If the pixel is at the boundry of the pic, just accumulate it to two lower values, such as below and lower right if column == 0.

Then I got a matrix of accumulated energies called energyVertSumMatrix. Find the smallest sum at the bottom row, start from that pixel, trace backwords to find the path, which is the seam.

The way to trace that is go to the three pixels higher of it and choose the smallest one.

/******************************************************************************
 *  Give a formula (using tilde notation) for the running time 
 *  (in seconds) required to reduce image size by one row and a formula
 *  for the running time required to reduce image size by one column. 
 *  Both should be functions of W and H. Removal should involve exactly
 *  one call to the appropriate find method and one call to the 
 *  appropriate remove method. The randomPicture() method in SCUtility 
 *  may be useful.
 * 
 *  Justify your answer with sufficient data using large enough 
 *  W and H values. To dampen system effects, you may wish to perform
 *  many trials for a given value of W and H and average the results.
 *  
 *  Be sure to give the leading coefficient.
 *****************************************************************************/

(keep W constant) W = 300

 H           Row removal time (seconds)     Column removal time (seconds)
--------------------------------------------------------------------------
300...		0.312				0.233
600...		0.43				0.321
1200...		0.691				0.537
2400...		1.243				0.978
4800...		2.414				1.775
9600...		4.813				4.35
19200..		13.714				10.655
38400...	32.093				21.527


(keep H constant) H = 300

 W           Row removal time (seconds)     Column removal time (seconds)
--------------------------------------------------------------------------
300...		0.319				0.22	
600...		0.449				0.345
1200...		0.688				0.556				
2400...		1.305				0.954
4800...		2.22				1.921
9600...		5.702				3.498
19200...	15.019				8.764
38400...	28.627				26.504


Running time to remove one row as a function of both W and H:  ~ 

T(2N) / T(N) = 3
T(WH) = c F(N)^(3/2) = c WH^(3/2)


Running time to remove one column as a function of both W and H:  ~ 

T(2N) / T(N) = 2 The running time doubles when WH doubles, So its linear.
T = cF(N) = c WH



/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/
Remove the seam more than one times in ResizeDemo.

error comes in removeHorizontalSeam() or removeVerticalSeam().

/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/
Backwards method is from Binh.
Hoeng helped me find out that I didn't initialize the new picture after removeHorizontal / Vertical Seam ()

/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/

Same with the bug: Remove the seam more than one times in ResizeDemo.

error comes in removeHorizontalSeam() or removeVerticalSeam().

/******************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 *****************************************************************************/
I worked alone~


/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/

Description of 'wrap' in main page of assignment is not clear. Spent a lot of time to figure out what that means.

