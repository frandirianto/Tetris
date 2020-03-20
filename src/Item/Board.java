package Item;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.Timer;

import Frame.MainMenuFrame;
import Helper.Helper;
import Panel.MainMenuPanel;

public class Board extends JPanel implements KeyListener {
	
	private BufferedImage blocksImage;
	private BufferedImage backgroundImage;
	private BufferedImage frameImage;
	private BufferedImage gameOverImage;
	
	private int score = 0;
	private String scoreString = "0";
	
	public Grid grid;
	private Index index;
	
	private boolean gameOver;
	private boolean shiftPressed; 
	private boolean shiftPieceAvail; 
	private boolean playerShifted; 
	private boolean shifted; 

	private final Piece piece[] = new Piece[7];
	private CurrentPiece currentPiece; 
	private Piece nextPiece;
	private Piece holdPiece = null;
	private Timer timer;
	
	public Board() {
		gameOver= shiftPressed = shiftPieceAvail =  playerShifted =  shifted = false;
		
		grid = new Grid();
		index = new Index();
		initLayout();
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < grid.getBoard()[row].length; col++) {
				grid.getBoard()[row][col] = 0;
			}
		}
	}
	
	private void initLayout(){
		setBackground(Helper.backgroundColor);
		readImage();
		setTimer();
		setPieces();
	}
	
	private void readImage(){
		backgroundImage = Helper.getImage("../Sprites/totoro.png");
		gameOverImage = Helper.getImage("../Sprites/game_over.jpg");
		frameImage = Helper.getImage("../Sprites/totoroFrames.png");
		blocksImage = Helper.getImage("../Sprites/tetris_blocks_21.png");
	}
	
	private void setTimer(){
		timer = new Timer(1000/60, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				update();
				repaint();
			}
		});
		timer.start();
	}

	private void setPieces(){
		piece[0] = new Piece(blocksImage.getSubimage(0, 0, Grid.BLOCKSIZE, Grid.BLOCKSIZE),
				new int[][] { { 1, 1, 1, 1 } }, this, 1); 
		piece[1] = new Piece(blocksImage.getSubimage(1 * Grid.BLOCKSIZE, 0, Grid.BLOCKSIZE, Grid.BLOCKSIZE),
				new int[][] { { 1, 1, 0 }, { 0, 1, 1 } }, this, 2); 
		piece[2] = new Piece(blocksImage.getSubimage(2 * Grid.BLOCKSIZE, 0, Grid.BLOCKSIZE, Grid.BLOCKSIZE),
				new int[][] { { 0, 1, 1 }, { 1, 1, 0 } }, this, 3); 
		piece[3] = new Piece(blocksImage.getSubimage(3 * Grid.BLOCKSIZE, 0, Grid.BLOCKSIZE, Grid.BLOCKSIZE),
				new int[][] { { 1, 0, 0 }, { 1, 1, 1 } }, this, 4); 
		piece[4] = new Piece(blocksImage.getSubimage(4 * Grid.BLOCKSIZE, 0, Grid.BLOCKSIZE, Grid.BLOCKSIZE),
				new int[][] { { 0, 0, 1 }, { 1, 1, 1 } }, this, 5); 
		piece[5] = new Piece(blocksImage.getSubimage(5 * Grid.BLOCKSIZE, 0, Grid.BLOCKSIZE, Grid.BLOCKSIZE),
				new int[][] { { 0, 1, 0 }, { 1, 1, 1 } }, this, 6); 
		piece[6] = new Piece(blocksImage.getSubimage(6 * Grid.BLOCKSIZE, 0, Grid.BLOCKSIZE, Grid.BLOCKSIZE),
				new int[][] { { 1, 1 }, { 1, 1 } }, this, 7);
		
		index.setCurIDX(Helper.randomNum(0, 6));
		
		currentPiece = new CurrentPiece(piece[index.getCurIDX()].getBlock(), piece[index.getCurIDX()].getCoords().clone(), this,
				piece[index.getCurIDX()].getColor()); 
		
		getNextPiece();
	}
	
	public void update() {
		if (gameOver) timer.stop();
		currentPiece.update();
	}

	public void paint(Graphics g) {
		super.paint(g);
		createBackground(g);
		currentPiece.render(g,"Current");
		
		for (int row = 0; row < grid.getBoard().length; row++)
			for (int col = 0; col < grid.getBoard()[0].length; col++)
				if (grid.getBoard()[row][col] != 0)
					g.drawImage(blocksImage.getSubimage((grid.getBoard()[row][col] - 1) * Grid.BLOCKSIZE, 0, Grid.BLOCKSIZE, Grid.BLOCKSIZE),
							col * Grid.BLOCKSIZE + grid.getIndentX(), row * Grid.BLOCKSIZE + grid.getIndentY(), null);

		nextPiece.render(g,"Next");
		if (holdPiece != null && (shiftPieceAvail || playerShifted)) holdPiece.render(g,"Hold");

		g.drawImage(frameImage, 390, 65, null);
		createScore(g);
		grid.paintGrid(g);
		
		if (gameOver) g.drawImage(gameOverImage, 10, 150, 280, 500, null);
	}
	
	private void createBackground(Graphics g){
		g.drawImage(backgroundImage, 40, 320, null);
		g.setColor(Helper.color1);
		g.setColor(new Color(255, 255, 255, 100));
		g.fillRect(grid.getIndentX(), grid.getIndentY() + 4 * Grid.BLOCKSIZE, grid.getGRIDWIDTH() * Grid.BLOCKSIZE, (grid.getGRIDHEIGHT() - 4) * Grid.BLOCKSIZE);
	}
	
	private void createScore(Graphics g){
		g.setFont(new Font("Agent Red", Font.BOLD, 20));
		g.setColor(Color.BLACK);
		g.drawString("LINES CLEARED: " + scoreString, 50, 40);
	}

	public void getNextPiece() {
		do {
			index.setNextIDX(Helper.randomNum(0, 6));
		} while (index.getNextIDX() == index.getCurIDX());

		nextPiece = new Piece(piece[index.getNextIDX()].getBlock(), piece[index.getNextIDX()].getCoords(), this, piece[index.getNextIDX()].getColor());
	}

	public void getPiece() {
		if (shiftPressed && !shifted) {
			shifted = true;
			index.setCurIDX(currentPiece.getColor() - 1);
			if (index.getCurIDX() == index.getHoldIDX())
				currentPiece.setcY(grid.getIndentY() + 4 * Grid.BLOCKSIZE - currentPiece.getCoords().length * Grid.BLOCKSIZE);
			
			else {
				int temp = index.getHoldIDX();
				index.setHoldIDX(index.getCurIDX());
				holdPiece = new Piece(piece[index.getHoldIDX()].getBlock(), piece[index.getHoldIDX()].getCoords(), this,
						piece[index.getHoldIDX()].getColor());

				if (shiftPieceAvail) {
					index.setCurIDX(temp);
					currentPiece = new CurrentPiece(piece[index.getCurIDX()].getBlock(), piece[index.getCurIDX()].getCoords(), this,
							piece[index.getCurIDX()].getColor());
					index.setHoldIDX(holdPiece.getColor() - 1);
				} else if (!shiftPieceAvail) {
					index.setCurIDX(nextPiece.getColor() - 1);
					currentPiece = new CurrentPiece(piece[index.getNextIDX()].getBlock(), piece[index.getNextIDX()].getCoords(), this,
							piece[index.getNextIDX()].getColor());

					getNextPiece();
				}
				holdPiece.render(getGraphics(),"Hold");
			}
			shiftPieceAvail = true;
			shiftPressed = false;
		} else if (!shiftPressed) {
			shifted = false;
			currentPiece = new CurrentPiece(piece[index.getNextIDX()].getBlock(), piece[index.getNextIDX()].getCoords(), this,
					piece[index.getNextIDX()].getColor());
			getNextPiece();
		}

		for (int i = 0; i < grid.getGRIDWIDTH(); i++)
			if (grid.getBoard()[3][i] != 0) {
				gameOver = true;
				break;
			}
	}

	

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) currentPiece.rotate();
		if (e.getKeyCode() == KeyEvent.VK_DOWN) currentPiece.speedDown();
		if (e.getKeyCode() == KeyEvent.VK_LEFT) currentPiece.setdX(-Grid.BLOCKSIZE);
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) currentPiece.setdX(Grid.BLOCKSIZE);
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			if (!shifted) {
				shiftPressed = true;
			} else {
				playerShifted = true;
				shifted = true;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (gameOver) {
				MainMenuPanel.clip.stop();
				new MainMenuFrame();

			} else 	currentPiece.placeDown();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			currentPiece.normalSpeed();
		}
	}
	

	public boolean isShiftPressed() {
		return shiftPressed;
	}

	public Piece getHoldPiece() {
		return holdPiece;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score += score;
		scoreString = "" + this.score;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}
