package source;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener {
	private BufferedImage blocks;
	private BufferedImage bg;
	private BufferedImage frame2;
	
	private BufferedImage gO;
	private int score = 0;
	private String scoreString = "0";
	private final int indentY = 25 * 2;
	private final int indentX = 25;
	private final int borderY = 25 * 24 + indentY;

	private final int borderX = 25 * 10 + indentX;
	public static final int BLOCKSIZE = 25;
	private static final int GRIDHEIGHT = 24, GRIDWIDTH = 10;
	private int[][] board = new int[GRIDHEIGHT][GRIDWIDTH];
	private int curIDX;
	private int holdIDX = 7;
	private int nextIDX;

	private final Piece piece[] = new Piece[7];
	private CurrentPiece currentPiece; 
	private Piece nextPiece;
	private Piece holdPiece = null;
	private Timer timer;

	private final int delay = 1000 / 60;
	private boolean gameOver = false;
	private boolean shiftPressed = false; 
	private boolean shiftPieceAvail = false; 
	private boolean playerShifted = false; 
	private boolean shifted = false; 

	public Board() {
		setBackground(Color.getHSBColor(0.53358333f, 0.5697f, 0.9569f));

		try {
			bg = ImageIO.read(Board.class.getResource("../Sprites/totoro.png"));
			gO = ImageIO.read(Board.class.getResource("../Sprites/game_over.jpg"));
			frame2 = ImageIO.read(Board.class.getResource("../Sprites/totoroFrames.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < board[row].length; col++) {
				board[row][col] = 0;
			}
		}
		try {
			blocks = ImageIO.read(Board.class.getResource("../Sprites/tetris_blocks_21.png")); 
		} catch (IOException e) {
			e.printStackTrace();
		}

		timer = new Timer(delay, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				update();
				repaint();
			}
		});

		timer.start();

		pieces();
	}

	private void pieces(){
		piece[0] = new Piece(blocks.getSubimage(0, 0, BLOCKSIZE, BLOCKSIZE), new int[][] { { 1, 1, 1, 1 } }, this, 1); 

		piece[1] = new Piece(blocks.getSubimage(1 * BLOCKSIZE, 0, BLOCKSIZE, BLOCKSIZE),
				new int[][] { { 1, 1, 0 }, { 0, 1, 1 } }, this, 2); 

		piece[2] = new Piece(blocks.getSubimage(2 * BLOCKSIZE, 0, BLOCKSIZE, BLOCKSIZE),
				new int[][] { { 0, 1, 1 }, { 1, 1, 0 } }, this, 3); 

		piece[3] = new Piece(blocks.getSubimage(3 * BLOCKSIZE, 0, BLOCKSIZE, BLOCKSIZE),
				new int[][] { { 1, 0, 0 }, { 1, 1, 1 } }, this, 4); 

		piece[4] = new Piece(blocks.getSubimage(4 * BLOCKSIZE, 0, BLOCKSIZE, BLOCKSIZE),
				new int[][] { { 0, 0, 1 }, { 1, 1, 1 } }, this, 5); 

		piece[5] = new Piece(blocks.getSubimage(5 * BLOCKSIZE, 0, BLOCKSIZE, BLOCKSIZE),
				new int[][] { { 0, 1, 0 }, { 1, 1, 1 } }, this, 6); 

		piece[6] = new Piece(blocks.getSubimage(6 * BLOCKSIZE, 0, BLOCKSIZE, BLOCKSIZE),
				new int[][] { { 1, 1 }, { 1, 1 } }, this, 7); 

		curIDX = Helper.randomNum(0, 6);
		currentPiece = new CurrentPiece(piece[curIDX].getBlock(), piece[curIDX].getCoords().clone(), this,
				piece[curIDX].getColor()); 
		
		getNextPiece();
	}
	
	public void update() {
		if (gameOver) {
			timer.stop();

		}
		currentPiece.update();
	}

	public void paint(Graphics g) {
		super.paint(g);
			g.drawImage(bg, 40, 320, null);
		g.setColor(Color.getHSBColor((float) 0.472, (float) 0.5, (float) 0.76));
			g.setColor(new Color(255, 255, 255, 100));
		g.fillRect(indentX, indentY + 4 * BLOCKSIZE, GRIDWIDTH * BLOCKSIZE, (GRIDHEIGHT - 4) * BLOCKSIZE);
		currentPiece.render(g);
		for (int row = 0; row < board.length; row++)
			for (int col = 0; col < board[0].length; col++)
				if (board[row][col] != 0)
					g.drawImage(blocks.getSubimage((board[row][col] - 1) * BLOCKSIZE, 0, BLOCKSIZE, BLOCKSIZE),
							col * BLOCKSIZE + indentX, row * BLOCKSIZE + indentY, null);

		nextPiece.render(g);
		if (holdPiece != null && (shiftPieceAvail || playerShifted))
			holdPiece.render(g);
		g.drawImage(frame2, 390, 65, null);

		g.setFont(new Font("Agent Red", Font.BOLD, 20));
		g.setColor(Color.BLACK);
		g.drawString("LINES CLEARED: " + scoreString, 50, 40);

	
		g.setColor(Color.DARK_GRAY);
		for (int i = 0; i <= GRIDHEIGHT - 4; i++) {
			g.drawLine(indentX, i * BLOCKSIZE + (indentY + (BLOCKSIZE * 4)), GRIDWIDTH * BLOCKSIZE + BLOCKSIZE,
					i * BLOCKSIZE + (indentY + (BLOCKSIZE * 4)));
		}
	
		for (int i = 0; i <= GRIDWIDTH; i++) {
			g.drawLine(i * BLOCKSIZE + BLOCKSIZE, (indentY + (BLOCKSIZE * 4)), i * BLOCKSIZE + BLOCKSIZE,
					(GRIDHEIGHT - 4) * BLOCKSIZE + (indentY + (BLOCKSIZE * 4)));
		}

		
		if (gameOver) g.drawImage(gO, 10, 150, 280, 500, null);
	}

	public void getNextPiece() {
		do {
			nextIDX = Helper.randomNum(0, 6);
		} while (nextIDX == curIDX);

		nextPiece = new Piece(piece[nextIDX].getBlock(), piece[nextIDX].getCoords(), this,
				piece[nextIDX].getColor());

	}

	public void getPiece() {
		if (shiftPressed && !shifted) {
			shifted = true;
			curIDX = currentPiece.getColor() - 1;
			if (curIDX == holdIDX)
				currentPiece.setcY(indentY + 4 * BLOCKSIZE - currentPiece.getCoords().length * BLOCKSIZE);
			else {
				int temp = holdIDX;
				holdIDX = curIDX;
				holdPiece = new Piece(piece[holdIDX].getBlock(), piece[holdIDX].getCoords(), this,
						piece[holdIDX].getColor());

				if (shiftPieceAvail) {
					curIDX = temp;
					currentPiece = new CurrentPiece(piece[curIDX].getBlock(), piece[curIDX].getCoords(), this,
							piece[curIDX].getColor());
					holdIDX = holdPiece.getColor() - 1;
				} else if (!shiftPieceAvail) {
					curIDX = nextPiece.getColor() - 1;
					currentPiece = new CurrentPiece(piece[nextIDX].getBlock(), piece[nextIDX].getCoords(), this,
							piece[nextIDX].getColor());

					getNextPiece();
				}
				holdPiece.render(getGraphics());
			}
			shiftPieceAvail = true;
			shiftPressed = false;
		} else if (!shiftPressed) {
			shifted = false;
			currentPiece = new CurrentPiece(piece[nextIDX].getBlock(), piece[nextIDX].getCoords(), this,
					piece[nextIDX].getColor());
			getNextPiece();
		}

		for (int i = 0; i < GRIDWIDTH; i++)
			if (board[3][i] != 0) {
				gameOver = true;
				break;
			}
	}

	public int[][] getBoard() {
		return board;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			currentPiece.rotate();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			currentPiece.speedDown();
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			currentPiece.setdX(-BLOCKSIZE);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			currentPiece.setdX(BLOCKSIZE);
		}
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

			} else
				currentPiece.placeDown();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			currentPiece.normalSpeed();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public int getGRIDHEIGHT() {
		return GRIDHEIGHT;
	}

	public int getGRIDWIDTH() {
		return GRIDWIDTH;
	}

	public int getIndentY() {
		return indentY;
	}

	public int getIndentX() {
		return indentX;
	}

	public int getBorderY() {
		return borderY;
	}

	public boolean getGameOver() {
		return gameOver;
	}

	public int getBorderX() {
		return borderX;
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

}
