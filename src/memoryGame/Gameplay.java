package memoryGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import implementations.QueueD;

import javax.swing.JPanel;

import lib.GraphicsLib;
import lib.QueueLib;

public class Gameplay extends JPanel implements KeyListener {

	// ----------------------------
	// ####   Graphic values   ####
	// ----------------------------
	// # Board
	private int boardWidth = 700;
	private int boardHeight = 600;
	// ---------------------------

	// -----------------------------
	// ####       General       ####
	// ----------------------------- 
	// # play: used for when the game is on
	private boolean play = false;
	// # guess: used for when it's the user's time to type the sequence
	private boolean guess = false;
	// # score: counts the # of consecutive numbers a user types correctly. 
	// # It's reset each time a sequence is changed. 
	private int score = 0;
	// # numbers: a queue to keep the sequence of numbers.  
	private QueueD numbers;
	// # numbersAux: used to help with operations done on numbers.  
	private QueueD numbersAux;
	// # numbersInQueue: is incremented every time we add a new number to 
	// # the sequence.
	private int numbersInQueue = 0;
	// # vkMapping: maps the key value (array index) with the key code. Used
	// # to check if the typed number is the right one for the sequence.
	private int[] vkMapping = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57};
	// ----------------------------	
	
	
	
	public Gameplay() {
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		resetGame();
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		// Creating a rectangles to be used to center text in board.  
		Rectangle rect = new Rectangle(0, 0, boardWidth, boardHeight);
		Rectangle rect2 = new Rectangle(0, 50, boardWidth, boardHeight);
		
		
		// Background
		g.setColor(Color.black);
		g.fillRect(0, 0, boardWidth, boardHeight);
		
		if(!play) {
			
			// If the game is stopped but there are numbers in the queue,  
			// the user has lost. Show the score and restart message.
			if(!numbers.isEmpty()) {
				g.setColor(Color.red);
				GraphicsLib.drawCenteredString(g, "Incorrect! You got " + score + " out of " + numbersInQueue, 
						rect, new Font("serif", Font.BOLD, 25));
				GraphicsLib.drawCenteredString(g, "Press Enter to restart", rect2, 
						new Font("serif", Font.BOLD, 20));
				
			} else {
				// If the game is stopped but there aren't any numbers in the queue,  
				// the user is at the beginning of the game. 
				g.setColor(Color.red);
				GraphicsLib.drawCenteredString(g, "Press Enter to start", rect, 
						new Font("serif", Font.BOLD, 25));
			}
		}
		
		// Display number sequence.
		if(play && !guess && !numbers.isEmpty()) {
			g.setColor(Color.YELLOW);
			GraphicsLib.drawCenteredString(g, showNumbersAsString(), rect, 
					new Font("serif", Font.BOLD, 25));
		}
		
		// Display "Go!" message while the user types their guesses.
		if(play && guess) {
			g.setColor(Color.GREEN);
			GraphicsLib.drawCenteredString(g, "Go!", rect, new Font("serif", Font.BOLD, 25));
		}
		
				
	}
	
	// Generate a new random number, add it to the sequence and show the whole
	// series to the user. 
	private void play() {
		
		guess = false;
		
		QueueLib.moveTo(numbersAux, numbers);
		
		getNewNumber();
		repaint();
		new java.util.Timer().schedule( 
	        new java.util.TimerTask() {
	            @Override
	            public void run() {
	            	guess = true;
	            	repaint();
	            }
	        }, 
	        2000 
		);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		// If the user pressed Enter and the game is stopped (play = false), 
		// start the game. 
		if(!play && e.getKeyCode() == KeyEvent.VK_ENTER) {
			play = true;
			resetGame();
			play();
		}
		
		// Check if the user's input corresponds to the correct number on the
		// sequence. 
		if(guess && !numbers.isEmpty() && e.getKeyCode() == vkMapping[numbers.first()]) {
			
			numbersAux.add(numbers.first());
			numbers.remove();
			score++;
			if(numbers.isEmpty()) {
				score = 0;
				play();
			}
			
		} else if (guess && e.getKeyCode() != vkMapping[numbers.first()]){
			play = false;
			guess = false;
			
			repaint();
		}
		
	}
	
	// Generates new random number and adds it to the queue. 
	private void getNewNumber() {
		Random r = new Random();
		int num = r.ints(0, 10).findFirst().getAsInt();
		
		numbers.add(num);
		numbersInQueue++;
	}
	
	// Generates a string with the numbers on the queue.
	private String showNumbersAsString() {
		
		String numbersStr = "";
		int numAux;
		
		while(!numbers.isEmpty()) {
			numAux = numbers.first();
			numbers.remove();
			
			numbersStr = numbersStr + " " + numAux;
			numbersAux.add(numAux);
			
		}
		
		QueueLib.moveTo(numbersAux, numbers);
		
		return numbersStr;
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	// Initialize all attributes to their original values. 
	private void resetGame() {

		numbers = new QueueD();
		numbers.initializeQueue();
		numbersAux = new QueueD();
		numbersAux.initializeQueue();
		
		score = 0;
		numbersInQueue = 0;
	}
	
}
