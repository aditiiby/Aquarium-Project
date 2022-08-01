
/**
 * CheckSolution is a utility class which can check if
 * a board position in an Aquarium puzzle is a solution.
 * @author Aditi Malu 22526301
 * @author Lyndon While
 * @version 2020
 */
import java.util.Arrays; 

public class CheckSolution
{
    /**
     * Non-constructor for objects of class CheckSolution
     */
    private CheckSolution(){}
    
    /**
     * Returns the number of water squares in each row of Aquarium puzzle p, top down.
     */
    public static int[] rowCounts(Aquarium p)
    {
        int[] watersInRow = new int[p.getSize()];
    	for(int r=0;r<p.getSize();r++) {
    		watersInRow[r]=0;
    		for(int c=0;c<p.getSize();c++){
    			if(p.getSpaces()[r][c]==Space.WATER) {
    				watersInRow[r]+=1;
    			}
    		}
    	}
        return watersInRow;
    }
    
    /**
     * Returns the number of water squares in each column of Aquarium puzzle p, left to right.
     */
    public static int[] columnCounts(Aquarium p)
    {
        int[] waterInColumns = new int[p.getSize()];
    	for(int c=0;c<p.getSize();c++){
    		waterInColumns[c]=0;
    		for(int r=0;r<p.getSize();r++) {
    			if(p.getSpaces()[r][c]==Space.WATER) {
    				waterInColumns[c]+=1;
    			}
    		}
    	}
    	
    	
        return waterInColumns;
    }
    
    /**
     * Returns a 2-int array denoting the collective status of the spaces 
     * in the aquarium numbered t on Row r of Aquarium puzzle p. 
     * The second element will be the column index c of any space r,c which is in t, or -1 if there is none. 
     * The first element will be: 
     * 0 if there are no spaces in t on Row r; 
     * 1 if they're all water; 
     * 2 if they're all not-water; or 
     * 3 if they're a mixture of water and not-water. 
     */
    public static int[] rowStatus(Aquarium p, int t, int r)
    {
        int[] status = new int[2];
    	status[0] = 0;
    	status[1] = -1;
    	boolean allWater=true,allNonWater=true;
    	for(int c =0;c<p.getSize();c++) {
    		if(p.getAquariums()[r][c]==t) {
    			if(p.getSpaces()[r][c]==Space.WATER) {
    				allWater = false;
    			}
    			if(p.getSpaces()[r][c]!=Space.WATER) {
    				allNonWater = false;
    			}
    			status[1]=c;
    		}
    	}
    	if(status[1] != -1) {
	    	if(!allWater && allNonWater) {
	    		status[0] = 1;
	    	}else if(allWater && !allNonWater) {
	    		status[0] = 2;
	    	}else {
	    		status[0]=3;
	    	}
    	}
    	
        return status;
    }
    
    /**
     * Returns a statement on whether the aquarium numbered t in Aquarium puzzle p is OK. 
     * Every row must be either all water or all not-water, 
     * and all water must be below all not-water. 
     * Returns "" if the aquarium is ok; otherwise 
     * returns the indices of any square in the aquarium, in the format "r,c". 
     */
    public static String isAquariumOK(Aquarium p, int t)
    {
        for(int r=0;r<p.getSize();r++) {
    		int[] status = rowStatus(p, t, r);
    		if(status[0] == 2 && r-1>=0){
        		int[] status2 = rowStatus(p, t, r-1);
        		if(status2[0] == 1) {
        			return r+","+status2[1];
        		}
    		}else if(status[0]==3) {
    			return r+","+status[1];
    		}
    	}
        return "";
    }
    
    /**
     * Returns a statement on whether we have a correct solution to Aquarium puzzle p. 
     * Every row and column must have the correct number of water squares, 
     * and all aquariums must be OK. 
     * Returns three ticks if the solution is correct; 
     * otherwise see the LMS page for the expected results. 
     */
    public static String isSolution(Aquarium p)
    {
    	for(int i=0;i<p.getSize();i++) {
    		if(p.getRowTotals()[i]!=rowCounts(p)[i]) {
    			return "Row "+i+" is wrong";
    		}
		}
		 

    	for(int i=0;i<p.getSize();i++) { 
    		if(p.getColumnTotals()[i]!=columnCounts(p)[i]) {
	    		return "Column "+i+" is wrong";
	    	}
    	}
    	
    	
    	int max = p.getAquariums()[0][0];
		//finding max aquarium number
    	for(int r=0;r<p.getSize();r++) {
			for(int c=0;c<p.getSize();c++){
				if(max<p.getAquariums()[r][c]){
					max = p.getAquariums()[r][c];
				}
			}
    	}
    	
    	for(int t=1;t<=max;t++){
    		if(!isAquariumOK(p, t).equals("")) {
    			return "The aquarium at "+isAquariumOK(p, t)+" is wrong";
    		}
    	}
    	
        return "\u2713\u2713\u2713";
    }
}
