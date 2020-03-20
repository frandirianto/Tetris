package Item;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class CurrentPiece extends Piece {

	private int normalSpeed;
	private int speedDown;
	private int currentSpeed;
	private long time;
	private long lastTime;
	private boolean collisionY;
	private boolean collisionX;
	private boolean spacePressed;

	public CurrentPiece(BufferedImage block, int[][] coords, Board board, int color) {
		super(block, coords, board, color);
		getSpeed();
		getTime();
		getCoordinate();
	}
	
	@Override
	public void render(Graphics g, String name) {
		render(this, g);
	}

	private void getSpeed(){
		currentSpeed = normalSpeed = 1000;
		speedDown = normalSpeed/10;
		collisionY = collisionX = spacePressed = false;
	}
	
	private void getTime(){
		time = 0;
		lastTime = System.currentTimeMillis();
	}
	
	private void getCoordinate(){
		cX = board.grid.getIndentX() + (board.grid.getGRIDWIDTH() / 2 - (coords[0].length / 2)) * Grid.BLOCKSIZE;
		cY = board.grid.getIndentY() + 4 * Grid.BLOCKSIZE - coords.length * Grid.BLOCKSIZE;
	}
	
	
	public void update() {
		time += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if (board.isShiftPressed()) {
			board.getPiece();
		}
		
		checkCollisionX();
		checkCollisionY();
		
		dX = 0;
		collisionX = false;
	}

	private void checkCollisionY(){
		if (collisionY) {
			if (time > currentSpeed) {
				time = 0;
			}
			for (int row = 0; row < coords.length; row++)
				for (int col = 0; col < coords[row].length; col++)
					if (coords[row][col] != 0)
						board.grid.getBoard()[((cY - board.grid.getIndentY()) / Grid.BLOCKSIZE)
								+ row][((cX - board.grid.getIndentX()) / Grid.BLOCKSIZE) + col] = color;
			checkLine();
			board.getPiece();
		}

		
		if (cY + Grid.BLOCKSIZE + (coords.length * Grid.BLOCKSIZE) <= board.grid.getBorderY()) {
			for (int row = 0; row < coords.length; row++)
				for (int col = 0; col < coords[row].length; col++)
					if (coords[row][col] != 0) {
						if (board.grid.getBoard()[((cY - board.grid.getIndentY()) / Grid.BLOCKSIZE) + row
								+ 1][((cX - board.grid.getIndentX()) / Grid.BLOCKSIZE) + col] != 0) {
							collisionY = true;
							if (spacePressed)
								cY -= 1;
						}
					}
			if (spacePressed) currentSpeed = 1;
			if (time > currentSpeed) {
				cY += Grid.BLOCKSIZE;
				time = 0;
			}
		} else {
			collisionY = true;
		}
	}

	private void checkCollisionX(){
		if (cX + dX >= board.grid.getIndentX() && cX + dX + coords[0].length * Grid.BLOCKSIZE <= board.grid.getBorderX()) {
			for (int row = 0; row < coords.length; row++)
				for (int col = 0; col < coords[row].length; col++)
					if (coords[row][col] != 0) {
						if (board.grid.getBoard()[((cY - board.grid.getIndentY()) / Grid.BLOCKSIZE)
								+ row][((cX - board.grid.getIndentX()) / Grid.BLOCKSIZE) + col
										+ (dX / Grid.BLOCKSIZE)] != 0)
							collisionX = true;
					}
			if (!collisionX)
				cX += dX;
		}
	}

	public void rotate() {
		int[][] rotatedMatrix = null;

		rotatedMatrix = getTranspose(coords);
		rotatedMatrix = getReverseMatrix(rotatedMatrix);

		if (cX < board.grid.getIndentX() || cX + (rotatedMatrix[0].length * Grid.BLOCKSIZE) > board.grid.getBorderX()
				|| cY + (rotatedMatrix.length * Grid.BLOCKSIZE) < board.grid.getIndentY()
				|| cY + (rotatedMatrix.length * Grid.BLOCKSIZE) > board.grid.getBorderY()) {
			
			while (cX + (rotatedMatrix[0].length * Grid.BLOCKSIZE) > board.grid.getBorderX()) cX -= Grid.BLOCKSIZE;
		}

		for (int row = 0; row < rotatedMatrix.length; row++)
			for (int col = 0; col < rotatedMatrix[row].length; col++)
				if (rotatedMatrix[row][col] != 0) {
					if (board.grid.getBoard()[((cY - board.grid.getIndentY()) / Grid.BLOCKSIZE) + row
							+ 1][((cX - board.grid.getIndentX()) / Grid.BLOCKSIZE) + col] != 0) {
						return;
					}
				}

		coords = rotatedMatrix;
	}

	private int[][] getTranspose(int[][] matrix) {
		int[][] newMatrix = new int[matrix[0].length][matrix.length];

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				newMatrix[j][i] = matrix[i][j];
			}
		}
		
		return newMatrix;
	}

	private int[][] getReverseMatrix(int[][] matrix) {
		int middle = matrix.length / 2;

		for (int i = 0; i < middle; i++) {
			int[] m = matrix[i];
			matrix[i] = matrix[matrix.length - i - 1];
			matrix[matrix.length - i - 1] = m;
		}
		
		return matrix;
	}

	private void checkLine() {
		int temp = 0;
		int height = board.grid.getBoard().length - 1;
		
		for (int i = height; i > 0; i--) {
			int count = 0;
			for (int j = 0; j < board.grid.getBoard()[0].length; j++) {
				if (board.grid.getBoard()[i][j] != 0) count++;
				board.grid.getBoard()[height][j] = board.grid.getBoard()[i][j];
			}
			if (count < board.grid.getBoard()[0].length) {
				height--;
				temp++;
			}
		}
	
		board.setScore((23 - temp));
	}
	

	public void normalSpeed() {
		currentSpeed = normalSpeed;
	}

	public void speedDown() {
		currentSpeed = speedDown;
	}

	public void placeDown() {
		spacePressed = true;
	}

	public boolean isCollisionY() {
		return collisionY;
	}

	public boolean isSpacePressed() {
		return spacePressed;
	}

}
