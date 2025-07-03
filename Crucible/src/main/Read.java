// In the main package
package main;

//Imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

// Read class
public class Read {

	// Read constructor
	public String[][] input(String num) {
		
		// Initialize variables
		InputStream c = getClass().getResourceAsStream("/read/input" + num + ".txt");
		InputStream is = getClass().getResourceAsStream("/read/input" + num + ".txt");
		BufferedReader check = new BufferedReader(new InputStreamReader(c));
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		// "Try" is used in case the files can't be found
		try {
			
			// This coding segment determines the size of our 2D array in both directions: row and column
			int row = 1;
			int collumn = check.readLine().split("").length;
			while(check.readLine() != null) {
				row++; 
			}
			
			// Initialize 2D array
			String[][] arr = new String[row][collumn];
			
			// Fill the 2D array with the input
			for(int i = 0; i < arr.length; i++) {
				arr[i] = br.readLine().split("");
			}
			
			// Return the 2D array
			return arr;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Return nothing if the file is not found
		return null;
		
	}
	
}
