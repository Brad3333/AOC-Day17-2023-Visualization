// In the main package
package main;

// Imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

// Main class
public class Main {

	public static void main(String[] args) {
		
		// Create a window 
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Crucible");
		
		// Create a panel and add it to the window
		Panel panel = new Panel();
		window.add(panel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		// Start the thread
		panel.startThread();
		
	}

}

// Panel class
@SuppressWarnings("serial")
class Panel extends JPanel implements Runnable {
	
	// Declare final variables and initialize
	public final int originalTileSize = 2;
	public final int scale = 2;
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 141;
	public final int maxScreenRow = 141;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	public final int FPS = 20;
	
	// Declare variables
	private int count, state;
	private String[][] grid;
	private ArrayList<String[][]> draw, draw2;
	
	// Declare objects
	private Thread thread;
	private TileManager tiles;
	private Read r;

	// Panel constructor
	public Panel() {
	
		// Initialize objects and variables
		r = new Read();
		grid = r.input("");
		tiles = new TileManager(this);
		draw = (new Draw((new Calc(grid)).getGrid(), grid)).getSequence();
		draw2 = (new Draw((new Calc2(grid)).getGrid(), grid)).getSequence();
		count = 0;
		state = 0;
		
		// Set stuff for the window
		this.setPreferredSize(new Dimension(screenHeight, screenWidth));
		this.setBackground(Color.WHITE);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		
	}
	
	// This method starts the thread
	public void startThread() {
		
		// Initialize thread and start
		thread = new Thread(this);
		thread.start();
		
	}

	// This method runs while thread is initialized
	public void run() {
	
		// Initialize variables for the timing logic
		double drawInterval = 1000000000/(FPS);
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		// Runs until program is shut down
		while(thread != null) {
			
			// Edits the variables for timing logic
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			// This updates and repaints every declared FPS
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			// This is to get a visual output of the FPS
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
			
		}
		
	}
	
	// This method updates what to draw in the window
	public void update() {
		count++;
		if(state == 0 && count > draw.size() + 30) {
			state++;
			count = 0;
		}
	}
	
	// This method draws the graphics in the window
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		if(count < draw.size() && state == 0) tiles.draw(g2, draw.get(count));
		else if(state == 0) tiles.draw(g2, draw.get(draw.size() - 1));
		else if(state == 1 && count < draw2.size()) tiles.draw(g2, draw2.get(count));
		else tiles.draw(g2,  draw2.get(draw2.size() - 1));
		g2.dispose();
	}

}
