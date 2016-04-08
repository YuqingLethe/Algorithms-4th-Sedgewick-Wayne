public class SeamCarver {
  private Picture pic;
  private Double[][] energyMatrix;

	
	/**
   * create a seam carver object based on the given picture
   * @param picture
   */
  public SeamCarver(Picture picture) {
    this.pic = new Picture(picture);
    energyMatrix = new Double[0][0];//initialize as 0 to know if has been calculated
  }
  
  /**
   * Current picture
   * @return
   */
  public Picture picture() {
    return pic;
  }
  
  /**
   * width of current picutre
   * @return the width 
   */
  public int width() {
    return pic.width();
	  
  }
  
  /**
   * height of current picture
   * @return intriguer of height 
   */
  public int height() {
    return pic.height();
	  
  }
  
  /**
   * The helper method for energy(int x, int y) to calculate energy of one pixel
   * @param col The column of the middle pixel
   * @param row The row of the middle pixel
   * @param colleft The column of the left pixel of the middle pixel
   * @param colright The column of the right pixel of the middle pixel
   * @param rowabove The row of the above pixel of the middle pixel
   * @param rowbelow The row of the below pixel of the middle pixel
   * @return the energy value of the middle pixel
   */
  private double getEnergy(int col, int row, int colleft, int colright, int rowabove, int rowbelow) {
	  //System.out.println("in getEnergy");
    int rx, gx, bx; //difference of Red,Green and Blue color
	int ry, gy, by; //difference of Red,Green and Blue color
	double xdiff, ydiff; // delta x and delta y
    rx = pic.get(colleft, row).getRed() - pic.get(colright, row).getRed();
    gx = pic.get(colleft, row).getGreen() - pic.get(colright, row).getGreen();
    bx = pic.get(colleft, row).getBlue() - pic.get(colright, row).getBlue();
    ry = pic.get(col, rowabove).getRed() - pic.get(col, rowbelow).getRed();
    gy = pic.get(col, rowabove).getGreen() - pic.get(col, rowbelow).getGreen();
    by = pic.get(col, rowabove).getBlue() - pic.get(col, rowbelow).getBlue();
    xdiff = Math.pow(rx, 2) + Math.pow(gx, 2) + Math.pow(bx, 2);
    ydiff = Math.pow(ry, 2) + Math.pow(gy, 2) + Math.pow(by, 2);
    return Math.sqrt(xdiff + ydiff);
  }
  
  
  /**
   * energy of pixel at column x and row y
   * @param x The column of the middle pixel
   * @param y The row of the middle pixel
   * @return the energy value of the middle pixel
   */
  public  double energy(int x, int y) {
	if (x < 0 || x > width() || y < 0 || y > height())
		throw new java.lang.IndexOutOfBoundsException();
	int colleft, colright, rowabove, rowbelow; //the index of pixels near it
	colleft = x - 1; colright = x + 1;
	rowabove = y - 1; rowbelow = y + 1;
	if (x == 0) {
	  colleft = width() - 1;
	}
	if (y == 0) {
	  rowabove = height() - 1;
	}
	if (x == width() - 1) {
	  colright = 0;
	}
	if (y == height() - 1) {
	  rowbelow = 0;
	}
	return getEnergy(x, y, colleft, colright, rowabove, rowbelow);
  }
  
  /**
   * To initialize the energyMatrix, copy energy to its place
   *  energies of every pixel in the pic
   */
  private void initialEnergyMatrix() {
	energyMatrix = new Double[height()][width()];
    for (int i = 0; i < height(); i++) {
      for (int j = 0; j < width(); j++) {
        energyMatrix[i][j] = energy(j, i);
      }
    }
  }

	  
  /**
   * Calculate Sum of Energy for findVerticalSeam(). 
   * The result will be store in a W*H matrix. 
   * Will use energyMatrix.
   */
  private int[] calEnergyVertSumMatrix() {
	if (energyMatrix.length == 0)
	  initialEnergyMatrix();
	
	Double[][] energyVertSumMatrix = new Double[height()][width()];
	for (int j = 0; j < width(); j++) {
	  energyVertSumMatrix[0][j] = energyMatrix[0][j];
	}
    for (int i = 1; i < height(); i++) {
      for (int j = 0; j < width(); j++) {
        energyVertSumMatrix[i][j] = Double.MAX_VALUE;  
      }
    }
    
    for (int i = 0; i < height() - 1; i++) {
      for (int j = 0; j < width(); j++) {
    	double left, bottom, right; //temporary energySum of the three pixels below
        bottom = energyMatrix[i + 1][j] + energyVertSumMatrix[i][j];
        energyVertSumMatrix[i + 1][j] = bottom < energyVertSumMatrix[i + 1][j] ? bottom : energyVertSumMatrix[i + 1][j];       
        if (j == 0) {
          right = energyMatrix[i + 1][j + 1] + energyVertSumMatrix[i][j];
          energyVertSumMatrix[i + 1][j + 1] = right < energyVertSumMatrix[i + 1][j + 1]? right : energyVertSumMatrix[i + 1][j + 1];
        }
        if (j == width() - 1) {
          left = energyMatrix[i + 1][j - 1] + energyVertSumMatrix[i][j];
          energyVertSumMatrix[i + 1][j - 1] = left < energyVertSumMatrix[i + 1][j - 1]? left : energyVertSumMatrix[i + 1][j - 1];
        }
        if (j != 0 && j != width() - 1) {
          right = energyMatrix[i + 1][j + 1] + energyVertSumMatrix[i][j];
          energyVertSumMatrix[i + 1][j + 1] = right < energyVertSumMatrix[i + 1][j + 1]? right : energyVertSumMatrix[i + 1][j + 1];
          left = energyMatrix[i + 1][j - 1] + energyVertSumMatrix[i][j];
          energyVertSumMatrix[i + 1][j - 1] = left < energyVertSumMatrix[i + 1][j - 1]? left : energyVertSumMatrix[i + 1][j - 1];
        }
      }
    } 
    
   //Return the column index of the start pixel in the last row
  	double min= Double.MAX_VALUE;
  	int minIndex = 0;
  	for (int i = 0; i < width(); i++) { 
        if (min > energyVertSumMatrix[height() - 1][i]) {
          min = energyVertSumMatrix[height() - 1][i];
          minIndex = i;
        }
      }
	//Trace the path backwards
	int[] seamPath = new int[height()]; //store
	seamPath[height() - 1] = minIndex;//store the last column index of the last row
	for (int i = height() - 2; i >= 0; i--) {
	  int pre = seamPath[i + 1];
	  if (pre == 0)
	   seamPath[i] = pre + findMinInThree(Double.MAX_VALUE, energyVertSumMatrix[i][pre], energyVertSumMatrix[i][pre + 1]);
	  if (pre == width() - 1)
	    seamPath[i] = pre + findMinInThree(energyVertSumMatrix[i][pre - 1], energyVertSumMatrix[i][pre], Double.MAX_VALUE);
	  if (pre != 0 && pre != width() - 1)
	   seamPath[i] = pre + findMinInThree(energyVertSumMatrix[i][pre - 1], energyVertSumMatrix[i][pre], energyVertSumMatrix[i][pre + 1]);
	}
	
	return seamPath;
  }
  
  
  /**
   * Calculate Sum of Energy for findHorizontalSeam(). 
   * The result will be store in a W*H matrix. 
   * Will use energyMatrix.
   */
  private int[] calEnergyHorizSumMatrix() {
	if (energyMatrix.length == 0)
		initialEnergyMatrix();
	Double[][] energyHorizSumMatrix = new Double[height()][width()];
	for (int i = 0; i < height(); i++) {
	  energyHorizSumMatrix[i][0] = energyMatrix[i][0];
	}
    for (int i = 0; i < height(); i++) {
      for (int j = 1; j < width(); j++) {
        energyHorizSumMatrix[i][j] = Double.MAX_VALUE;  
      }
    }
    
    for (int j = 0; j < width() - 1; j++) {
      for (int i = 0; i < height(); i++) {
    	double ra, right, rb; //temporary energySum of the right, rightabove and rightbelow pixels
        right = energyMatrix[i][j + 1] + energyHorizSumMatrix[i][j];
        energyHorizSumMatrix[i][j + 1] = right < energyHorizSumMatrix[i][j + 1] ? right : energyHorizSumMatrix[i][j + 1];       
        if (i == 0) {
          rb = energyMatrix[i + 1][j + 1] + energyHorizSumMatrix[i][j];
          energyHorizSumMatrix[i + 1][j + 1] = rb < energyHorizSumMatrix[i + 1][j + 1]? rb : energyHorizSumMatrix[i + 1][j + 1];
        }
        if (i == height() - 1) {
          ra = energyMatrix[i - 1][j + 1] + energyHorizSumMatrix[i][j];
          energyHorizSumMatrix[i - 1][j + 1] = ra < energyHorizSumMatrix[i - 1][j + 1]? ra : energyHorizSumMatrix[i - 1][j + 1];
        }
        if (i != 0 && i != height() - 1) {
    	  rb = energyMatrix[i + 1][j + 1] + energyHorizSumMatrix[i][j];
          energyHorizSumMatrix[i + 1][j + 1] = rb < energyHorizSumMatrix[i + 1][j + 1]? rb : energyHorizSumMatrix[i + 1][j + 1];
          ra = energyMatrix[i - 1][j + 1] + energyHorizSumMatrix[i][j];
          energyHorizSumMatrix[i - 1][j + 1] = ra < energyHorizSumMatrix[i - 1][j + 1]? ra : energyHorizSumMatrix[i - 1][j + 1];
        }
      }
    }
    
  //Return the row index of the start pixel in the last column
  	double min= Double.MAX_VALUE;
  	int minIndex = 0;
  	for (int i = 0; i < height(); i++) { 
  	    if (min > energyHorizSumMatrix[i][width() - 1]) {
  	      min = energyHorizSumMatrix[i][width() - 1];
  	      minIndex = i;
  	    }
  	}
  	
  	//Find the path backwards
  	int[] seamPath = new int[width()]; //store the seamPath
      seamPath[width() - 1] = minIndex;//store the last column index of the last row
      for (int i = width() - 2; i >= 0; i--) {
        int pre = seamPath[i + 1];
        if (pre == 0)
    	    seamPath[i] = pre + findMinInThree(Double.MAX_VALUE, energyHorizSumMatrix[pre][i], energyHorizSumMatrix[pre + 1][i]);
        if (pre == height() - 1)
          seamPath[i] = pre + findMinInThree(energyHorizSumMatrix[pre - 1][i], energyHorizSumMatrix[pre][i], Double.MAX_VALUE);
        if (pre != 0 && pre != height() - 1)
    	    seamPath[i] = pre + findMinInThree(energyHorizSumMatrix[pre - 1][i], energyHorizSumMatrix[pre][i], energyHorizSumMatrix[pre + 1][i]);
      }
      return seamPath;
  }
  
  /**
   * Return the place of the smallest value. -1 means the first value (top left),
   * 0 means the second (top), and 1 means the third value (top right)
   * @param left the first value of the three
   * @param middle the second value of the three
   * @param right the third value of the three
   * @return -1 if the first, 0 if second, 1 if third
   */
  private int findMinInThree(double left, double middle, double right) {
    double min = middle; //initialized the smallest value
    int minIndex = 0; //initialize the index of smallest as 0
    if (left < min) {
      min = left;
      minIndex = -1;
    }
    if (right < min) {
      min = right;
      minIndex = 1;
    }
    return minIndex;
    
  }
  /**
   * sequence of indices for horizontal seam
   * @return an integer array of the row index from left column to the right
   */
  public int[] findHorizontalSeam() {
	if (energyMatrix.length == 0) {     
	  calEnergyHorizSumMatrix();
	}
	
	//Find the path backwards
	int[] seamPath = calEnergyHorizSumMatrix(); 
	return seamPath;
  }

  /**
   * sequence of indices for vertical seam
   * @return an integer array of the column index from top column to the bottom
   */
  public int[] findVerticalSeam() {
	  /*
	if (energyMatrix.length == 0) {
	  calEnergyVertSumMatrix();
	}
	*/
	int[] seamPath = calEnergyVertSumMatrix();
    
    return seamPath;
  }
  
  private void checkSeam (int[] seam) {
    for (int i = 0; i < seam.length - 1; i++) {
      if (Math.abs(seam[i + 1] - seam[i]) > 1)
        throw new java.lang.IllegalArgumentException("two adjacent entries differ by more than 1");
    }
  }
  /**
   * remove horizontal seam from current picture
   * @param seam the integer array get from findHorizontalSeam
   */
  public void removeHorizontalSeam (int[] seam) {
    if (seam == null)
      throw new java.lang.NullPointerException("null seam");
    if (seam.length != width() || height() == 1)
      throw new java.lang.IllegalArgumentException("Length of seam is wrong");
    checkSeam(seam);
    
    Picture newpic = new Picture(pic.width(), pic.height() - 1);
    int rowIndex; //the increment of row of newpic
    for (int j = 0; j < width(); j++) {
      rowIndex = 0; //row index of new picture
      for (int i = 0; i < height(); i++) {       
        if (i == height() - 1)
          continue;
    	if (i == seam[j] && i != height() - 1)
          i++;        
        newpic.set(j, rowIndex, pic.get(j, i));
        rowIndex++;
      }
    }
    pic = new Picture(newpic);
    energyMatrix = new Double[0][0];
  }
  /**
   * remove vertical seam from current picture
   * @param seam the integer array get from findVerticalSeam
   */
  public void removeVerticalSeam (int [] seam) {
    if (seam == null)
      throw new java.lang.NullPointerException();
    if (seam.length != height() || width() == 1)
        throw new java.lang.IllegalArgumentException("Length of seam is wrong");
    checkSeam(seam);
    Picture newpic = new Picture(pic.width() - 1, pic.height());
    int colIndex;
    for (int i = 0; i < height(); i++) {
      colIndex = 0; //column index for new picture
      for (int j = 0; j < width(); j++) {        
    	if (j == width() - 1)
          continue;
    	if (j == seam[i] && j!= width() - 1)
          j++;       
        newpic.set(colIndex, i, pic.get(j, i));
        colIndex++;
      }
      
    } 
    this.pic = new Picture(newpic);
    energyMatrix = new Double[0][0];  
  }
 
  public static void main(String[] args) {
	     
  }

}
