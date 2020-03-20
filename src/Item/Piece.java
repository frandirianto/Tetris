package Item;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Piece {
	
	protected BufferedImage block;
	protected Board board;
	protected int coords[][];
	protected int color;
	protected int dX = 0;
	protected int cX, cY;

	public Piece(BufferedImage block, int[][] coords, Board board, int color) {
		super();
		this.block = block;
		this.coords = coords;
		this.board = board;
		this.color = color;
	}

	protected void render(Graphics g, String name){
		if(name.equals("Next")){
			for (int y = 0; y < coords.length; y++) {
				for (int x = 0; x < coords[y].length; x++) {
					if (coords[y][x] != 0 && coords[0].length != 4 && coords[0].length != 2)
						g.drawImage(block, x * Grid.BLOCKSIZE + 425, y * Grid.BLOCKSIZE + 2 * board.grid.getIndentY() - 5,
								null);
					else if (coords[y][x] != 0 && coords[0].length == 4)
						g.drawImage(block, x * Grid.BLOCKSIZE + 415, y * Grid.BLOCKSIZE + 2 * board.grid.getIndentY(), null);
					else if (coords[y][x] != 0)
						g.drawImage(block, x * Grid.BLOCKSIZE + 440, y * Grid.BLOCKSIZE + 2 * board.grid.getIndentY() - 5,
								null);
				}
			}
		}
		else if (name.equals("Hold")){
			for (int y = 0; y < coords.length; y++) {
				for (int x = 0; x < coords[y].length; x++) {
					if (coords[y][x] != 0 && coords[0].length != 4 && coords[0].length != 2)
						g.drawImage(block, x * Grid.BLOCKSIZE + 425, y * Grid.BLOCKSIZE + 4 * board.grid.getIndentY() - 5,
								null);
					else if (coords[y][x] != 0 && coords[0].length == 4)
						g.drawImage(block, x * Grid.BLOCKSIZE + 415, y * Grid.BLOCKSIZE + 4 * board.grid.getIndentY(), null);
					else if (coords[y][x] != 0)
						g.drawImage(block, x * Grid.BLOCKSIZE + 440, y * Grid.BLOCKSIZE + 4 * board.grid.getIndentY() - 5,
								null);
				}
			}
		}
	}
	
	protected void render(Piece p, Graphics g) {
		for (int y = 0; y < coords.length; y++) {
			for (int x = 0; x < coords[y].length; x++) {
				if (coords[y][x] != 0)
					g.drawImage(block, x * Grid.BLOCKSIZE + cX-1, y * Grid.BLOCKSIZE + cY, null);

			}
		}
	}

	public int getColor() {
		return color;
	}

	public BufferedImage getBlock() {
		return block;
	}

	public int[][] getCoords() {
		return coords;
	}

	public void setdX(int dX) {
		this.dX = dX;
	}

	public int getcX() {
		return cX;
	}

	public int getcY() {
		return cY;
	}

	public void setcY(int cY) {
		this.cY = cY;
	}

}
