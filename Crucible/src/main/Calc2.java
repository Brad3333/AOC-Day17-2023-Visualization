// In the main package
package main;

//Imports
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

// Calc class
public class Calc2 {
	
	// State a nested class
	class State {
		
		// Declare variables and reference to previous "State"
		State previous;
		int r, c, hl, dr, dc, n;
        
		// State constructor
        State(int hl, int r, int c, int dr, int dc, int n, State previous) {
        	
        	// Initialize variables and previous "State"
        	this.previous = previous;
            this.r = r;
            this.c = c;
            this.hl = hl;
            this.dr = dr;
            this.dc = dc;
            this.n = n;
            
        }
		
	}
	
	// Declare variables
	private int[][] grid;
	private State ans = null;
	private String[][] newGrid;

	// Calc constructor
	public Calc2(String[][] g) {
		
		// Initializes grid
		int row = g.length;
		int col = g[0].length;
		grid = new int[row][col];
		for(int r = 0; r < row; r++) {
			for(int c = 0; c < col; c++) {
				grid[r][c] = Integer.parseInt(g[r][c]);
				
			} 
		}
		
		// Prints answer to part 1
		System.out.println(minHeatLoss());
		
		// Initializes newGrid
		newGrid = new String[g.length][g[0].length];
		for(int i = 0; i < newGrid.length; i++) {
			for(int j = 0; j < newGrid[0].length; j++) {
				newGrid[i][j] = g[i][j];
			}
		}
		
		// Traverses through the answers path
		while(ans.previous != null) {
			
			// Changes spots in newGrid to show the direction the crucible is traveling in
			switch(ans.dr + " " + ans.dc) {
			case "0 1":
				newGrid[ans.r][ans.c] = ">";
				break;
			case "1 0":
				newGrid[ans.r][ans.c] = "v";
				break;
			case "0 -1":
				newGrid[ans.r][ans.c] = "<";
				break;
			case "-1 0":
				newGrid[ans.r][ans.c] = "^";
				break;
			}
			ans = ans.previous;
		}
		
	}
	
	// This method returns the final answer grid
	public String[][] getGrid() {
		return newGrid;
	}
	
	// This method calculates the path and minimum heat loss for part 2
	public int minHeatLoss() {
		
		// Create a priority queue with the smallest heat loss at the top
	    PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(state -> state.hl));
	    
	    // Starting spot
	    pq.add(new State(0, 0, 0, 0, 0, 0, null));
	    
	    // Create a set to keep track of where we have already been
	    Set<String> seen = new HashSet<>();
	    
	    
	    // While there is more spots in the priority queue
	    while (!pq.isEmpty()) {
	    	
	    	// Current "State" is the top of our priority queue
	        State current = pq.poll();
	        
	        // Initializes variables using the current "State"
	        int hl = current.hl, r = current.r, c = current.c, 
	            dr = current.dr, dc = current.dc, n = current.n;
	        
	        // Check if we've reached the destination with at least 4 consecutive moves in a row
	        if (r == grid.length - 1 && c == grid[0].length - 1 && n >= 4) {
	        	ans = current;
	            return hl;
	        }
	        
	        // Skip if we've already processed this "State"
	        if (seen.contains(r + " " + c + " " + dr + " " + dc + " " + n)) {
	            continue;
	        }
	        
	        // Add the current "State" as visited
	        seen.add(r + " " + c + " " + dr + " " + dc + " " + n);
	        
	        // Continue in the same direction (if we haven't moved more than 10 steps in the same direction)
	        if (n < 10) {
	        	
	        	// Initialize the new row and column
	            int nr = r + dr;
	            int nc = c + dc;
	            
	            // If the new row and column are valid indexes add them to the priority queue 
	            if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length) {
	                pq.add(new State(hl + grid[nr][nc], nr, nc, dr, dc, n + 1, current));
	            }
	            
	        }
	        
	        // Try all possible new directions (right, down, left, up)
	        int[][] directions = {
	            {0, 1},  // Move right
	            {1, 0},  // Move down
	            {0, -1}, // Move left
	            {-1, 0}  // Move up
	        };
	        
	        // If we have moved at least 4 in the same direction already or we are standing still
	        if(n >= 4 || dr == 0 && dc == 0) {
	        
	        	// For all the directions
		        for (int[] direction : directions) {
		        	
		        	// Initialize new delta row and column
		            int ndr = direction[0];
		            int ndc = direction[1];
		            
		            // Make sure we are only turning 90 degrees
		            if ((ndr != dr || ndc != dc) && !(ndr == -dr && ndc == -dc)) {
		            	
		            	// Initialize new row and column
		                int nr = r + ndr;
		                int nc = c + ndc;
		                
		                // If the new row and column are valid indexes add them to the priority queue
		                if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length) {
		                    pq.add(new State(hl + grid[nr][nc], nr, nc, ndr, ndc, 1, current));  // Reset n to 1 when changing direction
		                }
		                
		            }
		        }
		    
	        }
	        
	    }
	    
	    // If no path is found
	    return -1; 
	}

}
