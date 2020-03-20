package Item;

import java.awt.Color;
import java.awt.Graphics;

public class Grid {

	private final int indentY = 25 * 2;
	private final int indentX = 25;
	private final int borderY = 25 * 24 + indentY;
	private final int borderX = 25 * 10 + indentX;
	
	public static final int BLOCKSIZE = 25;
	private static final int GRIDHEIGHT = 24, GRIDWIDTH = 10;
	private int[][] board = new int[GRIDHEIGHT][GRIDWIDTH];
	
	
	public Grid() {
	}
	
	public void paintGrid(Graphics g){
		g.setColor(Color.DARK_GRAY);
		for (int i = 0; i <= getGRIDHEIGHT() - 4; i++) {
			g.drawLine(getIndentX(), i * BLOCKSIZE + (getIndentY() + (BLOCKSIZE * 4)), getGRIDWIDTH()* BLOCKSIZE + BLOCKSIZE,
					i * BLOCKSIZE + (getIndentY() + (Grid.BLOCKSIZE * 4)));
		}
	
		for (int i = 0; i <= getGRIDWIDTH(); i++) {
			g.drawLine(i * BLOCKSIZE + BLOCKSIZE, (getIndentY() + (BLOCKSIZE * 4)), i * BLOCKSIZE + BLOCKSIZE,
					(getGRIDHEIGHT() - 4) * BLOCKSIZE + (getIndentY() + (BLOCKSIZE * 4)));
		}
	}
	
	public int getBLOCKSIZE() {
		return BLOCKSIZE;
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
	public int getBorderX() {
		return borderX;
	}

	public int[][] getBoard() {
		return board;
	}
}
