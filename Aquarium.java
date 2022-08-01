import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Aquarium represents a single problem in the game Aquarium.
 * @author Aditi Malu 22526301
 * @author Lyndon While 
 * @version 2020
 */
public class Aquarium
{
    private int   size;         // the board is size x size
    private int[] columnTotals; // the totals at the top of the columns, left to right
    private int[] rowTotals;    // the totals at the left of the rows, top to bottom 
    
    // the board divided into aquariums, numbered from 1,2,3,...
    // spaces with the same number are part of the same aquarium
    private int[][] aquariums;
    // the board divided into spaces, each empty, water, or air
    private Space[][] spaces;

    /**
     * Constructor for objects of class Aquarium. 
     * Creates, initialises, and populates all of the fields.
     */
    public Aquarium(String filename)
    {
        int line = 1;
        BufferedReader in;
    	try{
    	    in = new BufferedReader(new FileReader(filename));
    	    String s;
    	    int r = 0;
    	    while((s = in.readLine()) != null){
    	    	if(line==1) {
    	    		int[] nums = parseLine(s);
    	    		columnTotals = new int[nums.length];
    	        	size = nums.length;
    	        	aquariums = new int[size][size];
    	        	spaces = new Space[size][size];
    	        	
    	        	int i =0;
    	        	for(int a:nums){
    	        		columnTotals[i++]=a;
    	        	}
    	        	
    	    		line =2;
    	    	}else if(line==2) {
    	    		int[] nums = parseLine(s);
    	        	rowTotals = new int[nums.length];
    	        	
    	        	int i =0;
    	        	for(int a:nums ){
    	        		rowTotals[i++]=a;
    	        	}
    	    		
    	    		line = 3;
    	    	}else if(line==3) {
    	    		line = 4;
    	    	}else {
    	    		int[] nums = parseLine(s);
    	    		for(int c = 0;c < nums.length;c++) {
    	    			aquariums[r][c]= nums[c]; 
    	    		}
    	    		r++;
    	    	}
    	    }
    	    in.close();
    	}catch(Exception e){
    	    e.printStackTrace();
    	}
    	
    	
    	for(int r=0;r<size;r++) {
    		for(int c=0;c<size;c++) {
        		spaces[r][c] = Space.EMPTY;
        	}
    	}
    }
    
    /**
     * Uses the provided example file on the LMS page.
     */
    public Aquarium()
    {
        this("Examples/a6_1.txt");
    }

    /**
     * Returns an array containing the ints in s, 
     * each of which is separated by one space. 
     * e.g. if s = "1 299 34 5", it will return {1,299,34,5} 
     */
    public static int[] parseLine(String s)
    {
    	int[] numbers;
    	String[] nums = s.split(" ");
    	numbers = new int[nums.length];
    	
    	int i =0;
    	for(String a:nums ){
    		numbers[i++]=Integer.parseInt(a);
    	}
    	
        return numbers;
    }
    
    /**
     * Returns the size of the puzzle.
     */
    public int getSize()
    {
        return this.size;
    }
    
    /**
     * Returns the column totals.
     */
    public int[] getColumnTotals()
    {
        return this.columnTotals;
    }
    
    /**
     * Returns the row totals.
     */
    public int[] getRowTotals()
    {
        return this.rowTotals;
    }
    
    /**
     * Returns the board in aquariums.
     */
    public int[][] getAquariums()
    {
        return this.aquariums;
    }
    
    /**
     * Returns the board in spaces.
     */
    public Space[][] getSpaces()
    {
        return this.spaces;
    }
    
    /**
     * Performs a left click on Square r,c if the indices are legal, o/w does nothing. 
     * A water space becomes empty; other spaces become water. 
     */
    public void leftClick(int r, int c)
    {
        if((r>=0 && r<size)&&(c>=0 && c<size)) {
	    	if(spaces[r][c]==Space.WATER) {
	    		spaces[r][c]=Space.EMPTY;
	    	}else {
	    		spaces[r][c]=Space.WATER;
	    	}
    	}
    }
    
    /**
     * Performs a right click on Square r,c if the indices are legal, o/w does nothing. 
     * An air space becomes empty; other spaces become air. 
     */
    public void rightClick(int r, int c)
    {
        if((r>=0 && r<size)&&(c>=0 && c<size)) {
	    	if(spaces[r][c]==Space.AIR) {
	    		spaces[r][c]=Space.EMPTY;
	    	}else {
	    		spaces[r][c]=Space.AIR;
	    	}
    	}
    }
    
    /**
     * Empties all of the spaces.
     */
    public void clear()
    {
        for(int r=0;r<size;r++) {
    		for(int c=0;c<size;c++) {
        		spaces[r][c] = Space.EMPTY;
        	}
    	}
    }
}
