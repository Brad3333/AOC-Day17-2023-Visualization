// In the main package
package main;

//Imports
import java.util.ArrayList;

// Draw class
public class Draw {

	// Declares variables
	private String[][] grid, original;
	private ArrayList<String[][]> drawingSequence;
	
	// Draw constructor
	public Draw(String[][] grid, String[][] original) {
		
		// Initializes variables
		this.grid = grid;
		this.original = original;
		
		// calls the create method
		create();
	}
	
	// This method creates the drawing sequence and stores it in an ArrayList
	public void create() {
		
		// Initialize variables
		drawingSequence = new ArrayList<>();
		int row = grid.length - 1, col = grid[0].length - 1;
		
		// Add the first grid to draw
		drawingSequence.add(copy(original));
		
		// While we are not at the starting spot (0,0)
		while(row != 0 || col != 0) {
			
			// Create a new 2D array for our new grid
			String[][] add = copy(drawingSequence.getLast());
			
			// Change a value and add it to our drawing sequence
			add[row][col] = "0";
			drawingSequence.add(add);
			
			// Checks which way we are headed next and adjusts the row or column accordingly
			switch(grid[row][col]) {
			case ">":
				col -= 1;
				break;
			case "<":
				col += 1;
				break;
			case "^":
				row += 1;
				break;
			case "v":
				row -= 1;
				break;
			}
			
		}
		
		// Adds the starting spot grid to our drawing sequence
		String[][] add = copy(drawingSequence.getLast());
		add[0][0] = "0";
		drawingSequence.add(add);
		
	}
	
	// This method copies a 2D array so we don't mess with the original 2D array
	public String[][] copy(String[][] g) {
		String[][] copy = new String[g.length][g[0].length];
		for(int i = 0; i < g.length; i++) {
			for(int j = 0; j < g[0].length; j++) {
				copy[i][j] = g[i][j];
			}
		} return copy;
	}
	
	// This method returns the drawing sequence that was created in this class
	public ArrayList<String[][]> getSequence() {
		return drawingSequence;
	}
	
}
