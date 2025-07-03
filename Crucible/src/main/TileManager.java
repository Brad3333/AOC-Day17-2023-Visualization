// In the main package
package main;

//Imports
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

// TileManager class
public class TileManager {
	
	// Declare objects
	Panel p;
	Tile[] tile;
	
	// TileManager constructor
	public TileManager(Panel p) {
		
		// Initializes objects
		this.p = p;
		tile = new Tile[10];
		
		// Calls the getTileImage method
		getTileImage();
		
	}
	
	// Loads the pixel art as an image
	private void getTileImage() {
			
		// "Try" is used in case the files can't be found
		try {
			
			// Loads through the 10 images and adds them to the Tile array
			for(int i = 0; i < tile.length; i++) {
				tile[i] = new Tile();
				tile[i].image = ImageIO.read(getClass().getResourceAsStream("/colors/" + i + ".png"));
			}

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// This method draws the tiles
	public void draw(Graphics2D g2, String[][] grid) {
		
		// Initialize variables
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		// While the column and row are valid indexes
		while(col < p.maxScreenCol && row < p.maxScreenRow) {
			
			// Gets what image number we will be drawing
			int tileNum = Integer.parseInt(grid[row][col]);
			
			// Draws the image and adjusts the column and x position
			g2.drawImage(tile[tileNum].image, x, y, p.tileSize, p.tileSize, null);
			col++;
			x += p.tileSize;
			
			// If we are at the end of the column we reset and move to the next row
			if(col == p.maxScreenRow) {
				col = 0;
				x = 0;
				row++;
				y += p.tileSize;
			} 
			
		}
		
	}
	
	// Tile class
	class Tile {
		
		// Declares the image
		public BufferedImage image;

	}

}
