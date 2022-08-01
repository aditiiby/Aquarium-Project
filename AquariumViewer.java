
/**
 * AquariumViewer represents an interface for playing a game of Aquarium.
 * @author Aditi Malu 22526301
 * @author Lyndon While
 * @version 2020
 */
import java.awt.*;
import java.awt.event.*; 
import javax.swing.SwingUtilities;

public class AquariumViewer implements MouseListener
{
    private final int BOXSIZE = 40;          // the size of each square
    private final int OFFSET  = BOXSIZE * 2; // the gap around the board
    private       int WINDOWSIZE;            // set this in the constructor 
    
    private Aquarium puzzle; // the internal representation of the puzzle
    private int        size; // the puzzle is size x size
    private SimpleCanvas sc; // the display window

    /**
     * Main constructor for objects of class AquariumViewer.
     * Sets all fields, and displays the initial puzzle.
     */
    public AquariumViewer(Aquarium puzzle)
    {
        this.puzzle = puzzle;
    	this.size = puzzle.getSize();
    	this.WINDOWSIZE = (this.BOXSIZE*this.size)+(this.OFFSET*2);
    	this.sc =  new SimpleCanvas("Aquarium",this.WINDOWSIZE,this.WINDOWSIZE,Color.WHITE);
    	this.sc.addMouseListener(this);
    }
    
    /**
     * Selects from among the provided files in folder Examples. 
     * xyz selects axy_z.txt. 
     */
    public AquariumViewer(int n)
    {
        this(new Aquarium("Examples/a" + n / 10 + "_" + n % 10 + ".txt"));
    }
    
    /**
     * Uses the provided example file on the LMS page.
     */
    public AquariumViewer()
    {
        this(61);
    }
    
    /**
     * Returns the current state of the puzzle.
     */
    public Aquarium getPuzzle()
    {
        return this.puzzle;
    }
    
    /**
     * Returns the size of the puzzle.
     */
    public int getSize()
    {
        return this.size;
    }

    /**
     * Returns the current state of the canvas.
     */
    public SimpleCanvas getCanvas()
    {
        return this.sc;
    }
    
    /**
     * Displays the initial puzzle; see the LMS page for the format.
     */
    private void displayPuzzle()
    {
        displayGrid();
    	displayNumbers();
    	displayAquariums();
    	displayButtons();
    }
    
    /**
     * Displays the grid in the middle of the window.
     */
    public void displayGrid()
    {
        for(int i = 0;i<=size;i++) {
    		//horizontal lines
    		this.sc.drawLine(OFFSET, OFFSET+(BOXSIZE*i), (OFFSET+(size*BOXSIZE)), OFFSET+(BOXSIZE*i), Color.black);
    		//vertical lines
    		this.sc.drawLine(OFFSET+(BOXSIZE*i), OFFSET,OFFSET+(BOXSIZE*i), (OFFSET+(size*BOXSIZE)), Color.black);
    	}
    }
    
    /**
     * Displays the numbers around the grid.
     */
    public void displayNumbers()
    {
        for(int i =0;i<size;i++) {
    		//draw column totals
    		this.sc.drawString(puzzle.getColumnTotals()[i], (OFFSET+BOXSIZE/2)+(BOXSIZE*i), OFFSET-(BOXSIZE/2), Color.BLACK);
    		//draw row totals
    		this.sc.drawString(puzzle.getRowTotals()[i], OFFSET-(BOXSIZE/2), (OFFSET+BOXSIZE/2)+(BOXSIZE*i), Color.BLACK);
    	}
    }
    
    /**
     * Displays the aquariums.
     */
    public void displayAquariums()
    {
        for(int i = 0;i<=size;i++) {
    		//horizontal lines
    		this.sc.drawLine(OFFSET, OFFSET+(BOXSIZE*i), (OFFSET+(size*BOXSIZE)), OFFSET+(BOXSIZE*i), Color.red);
    		//vertical lines
    		this.sc.drawLine(OFFSET+(BOXSIZE*i), OFFSET,OFFSET+(BOXSIZE*i), (OFFSET+(size*BOXSIZE)), Color.red);
    	}
    	
    	for(int r=0;r<size;r++) {
    		for(int c=0;c<size;c++) {
    			//for left line
    			if((c-1)>=0 && puzzle.getAquariums()[r][c]==puzzle.getAquariums()[r][c-1]){
    					this.sc.drawLine(OFFSET+(BOXSIZE*c), OFFSET+(BOXSIZE*r), OFFSET+(BOXSIZE*c), OFFSET+(BOXSIZE*r)+BOXSIZE, Color.black);
    			}
    			//for top line
    			if((r-1)>=0 && puzzle.getAquariums()[r][c]==puzzle.getAquariums()[r-1][c]) {
    				this.sc.drawLine(OFFSET+(BOXSIZE*c), OFFSET+(BOXSIZE*r), OFFSET+(BOXSIZE*c)+BOXSIZE, OFFSET+(BOXSIZE*r), Color.black);
    			}
    		}
    	}
    }
    
    /**
     * Displays the buttons below the grid.
     */
    public void displayButtons()
    {
        this.sc.drawString("SOLVED?", OFFSET, OFFSET+(BOXSIZE*size)+(BOXSIZE/2), Color.black);
    	this.sc.drawString("CLEAR", OFFSET+(BOXSIZE*(size-1)), OFFSET+(BOXSIZE*size)+(BOXSIZE/2), Color.black);
    }
    
    /**
     * Updates the display of Square r,c. 
     * Sets the display of this square to whatever is in the squares array. 
     */
    public void updateSquare(int r, int c)
    {
    	if(puzzle.getSpaces()[r][c]==Space.EMPTY) {
    		this.sc.drawRectangle(OFFSET+(BOXSIZE*c)+1, OFFSET+(BOXSIZE*r)+1,OFFSET+(BOXSIZE*c)+BOXSIZE-1, OFFSET+(BOXSIZE*r)+BOXSIZE-1, Color.white);
    	}else if(puzzle.getSpaces()[r][c]==Space.WATER) {
    		this.sc.drawRectangle(OFFSET+(BOXSIZE*c)+1, OFFSET+(BOXSIZE*r)+1,OFFSET+(BOXSIZE*c)+BOXSIZE-1, OFFSET+(BOXSIZE*r)+BOXSIZE-1, Color.cyan);
    	}else {
    		this.sc.drawRectangle(OFFSET+(BOXSIZE*c)+1, OFFSET+(BOXSIZE*r)+1,OFFSET+(BOXSIZE*c)+BOXSIZE-1, OFFSET+(BOXSIZE*r)+BOXSIZE-1, Color.white);
    		this.sc.drawDisc(OFFSET+(BOXSIZE*c)+BOXSIZE/2, OFFSET+(BOXSIZE*r)+BOXSIZE/2, 5, Color.red);
    	}
    }
    
    /**
     * Responds to a mouse click. 
     * If it's on the board, make the appropriate move and update the screen display. 
     * If it's on SOLVED?,   check the solution and display the result. 
     * If it's on CLEAR,     clear the puzzle and update the screen display. 
     */
    public void mousePressed(MouseEvent e) 
    {
        if((e.getX()>=OFFSET && e.getX()<OFFSET+(BOXSIZE*size)) && (e.getY()>=OFFSET && e.getY()<OFFSET+(BOXSIZE*size))) {
    		
    		for(int r=0;r<size;r++) {
        		for(int c=0;c<size;c++) {
		    		if((e.getX()>=OFFSET+(BOXSIZE*c) && e.getX()<OFFSET+(BOXSIZE*c)+BOXSIZE) && (e.getY()>=OFFSET+(BOXSIZE*r) && e.getY()<OFFSET+(BOXSIZE*r)+BOXSIZE)) {
	        			if(SwingUtilities.isLeftMouseButton(e)){
			        		puzzle.leftClick(r, c);
			        		updateSquare(r, c);
			        	}
			        	else if(SwingUtilities.isRightMouseButton(e)){
			        		puzzle.rightClick(r, c);
			        		updateSquare(r, c);
			        	}
	        			break;
		    		}
        		}
        	}
    	}else if((e.getX()>=OFFSET && e.getX()<OFFSET+BOXSIZE) && (e.getY()>=OFFSET+(BOXSIZE*size)+10 && e.getY()<(OFFSET+(BOXSIZE*size)+20))) {
    		this.sc.drawRectangle(OFFSET, OFFSET+(BOXSIZE*size)+20, OFFSET+(BOXSIZE*size), OFFSET+(BOXSIZE*size)+45,Color.white);
    		this.sc.drawString(CheckSolution.isSolution(this.puzzle), OFFSET, OFFSET+(BOXSIZE*size)+40, Color.black);
    	}else if((e.getX()>=OFFSET+(BOXSIZE*(size-1)) && e.getX()<OFFSET+(BOXSIZE*size)) && (e.getY()>=OFFSET+(BOXSIZE*size)+10 && e.getY()<(OFFSET+(BOXSIZE*size)+20))) {
    		this.puzzle.clear();
    		for(int r=0;r<size;r++) {
        		for(int c=0;c<size;c++) {
        			updateSquare(r, c);
        		}
        	}
    		this.sc.drawRectangle(OFFSET, OFFSET+(BOXSIZE*size)+20, OFFSET+(BOXSIZE*size), OFFSET+(BOXSIZE*size)+45,Color.white);
    	}
    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    
    //~~~~ uncomment this code below and run this class to check your project graphically
    //note: this method is not required for submission
    
    public static void main(String[] args) {
    	AquariumViewer aqv = new AquariumViewer();
    	aqv.displayPuzzle();
    	for(int r=0;r<aqv.size;r++) {
    		for(int c=0;c<aqv.size;c++) {
    			aqv.updateSquare(r, c);
    		}
    	}
    }
}
