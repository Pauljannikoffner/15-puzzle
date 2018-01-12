import java.util.HashSet;

/**
 * Representation of a board, model as in MVC pattern.
 * 
 * @author Paul Jannik Offner
 * @version 12.01.2018
 */
public class Board {

	private int[][] tiles;
	private boolean custom;

	public Board() {
		tiles = new int[4][4];
		custom = false;
	}

	public Board(int[][] tiles) {
		this.tiles = tiles;
		custom = false;
	}

	public void setToEmptyState() {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				tiles[x][y] = 0;
			}
		}
	}

	/**
	 * Sets the board to the goal state
	 */
	public void setToGoalState() {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				tiles[x][y] = 4 * x + y + 1;
			}
		}
		tiles[3][3] = 0;
	}

	/**
	 * Returns the boards which arise from all possible next moves.
	 * 
	 * @return All possible next boards
	 */
	public HashSet<Board> possibleNextBoards() {
		HashSet<Board> result = new HashSet<Board>();

		int x = blankX();
		int y = blankY();

		if (x > 0) {
			Board board = this.clone();
			board.move(x - 1, y);
			result.add(board);
		}
		if (x < 3) {
			Board board = this.clone();
			board.move(x + 1, y);
			result.add(board);
		}
		if (y > 0) {
			Board board = this.clone();
			board.move(x, y - 1);
			result.add(board);
		}
		if (y < 3) {
			Board board = this.clone();
			board.move(x, y + 1);
			result.add(board);
		}

		return result;
	}

	/**
	 * Returns the hamming-distance of the board (value of 0 to 9, +1 for each
	 * not-optimal tile).
	 * 
	 * @return hamming-distance of the board; 0 if goal is reached
	 */
	public int hammingDistance() {
		int result = 0;

		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (tiles[x][y] != 0) {
					if (tiles[x][y] != (4 * x + y + 1)) {
						result++;
					}
				}
			}
		}

		return result;
	}

	/**
	 * Returns the manhatten-distance of the board (sum of the manhatten-distances
	 * of all tiles to their optimal positions).
	 * 
	 * @return manhatten-distance of the board; 0 if goal is reached
	 */
	public int manhattenDistance() {
		int result = 0;

		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (tiles[x][y] != 0) {
					result += Math.abs((tiles[x][y] - 1) / 4 - x);
					result += Math.abs((tiles[x][y] - 1) % 4 - y);
				}
			}
		}

		return result;
	}

	/**
	 * Indicates whether a tile is movable.
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * @return true if tile is movable; false otherwise
	 */
	public boolean isMovable(int x, int y) {
		return (Math.abs(x - blankX()) + Math.abs(y - blankY())) == 1;
	}

	/**
	 * Moves the tile, that is located at the transfered coordinates.
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 */
	public void move(int x, int y) {
		int blankX = blankX();
		int blankY = blankY();

		tiles[blankX][blankY] = tiles[x][y];
		tiles[x][y] = 0;
	}

	/**
	 * Places the next custom tile at the given position
	 * 
	 * @param tileX
	 *            x-coordinate
	 * @param tileY
	 *            y-coordinate
	 */
	public void nextCustomTile(int tileX, int tileY) {
		int tileValue = 0;
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (tiles[x][y] > tileValue) {
					tileValue = tiles[x][y];
				}
			}
		}
		tileValue++;
		tiles[tileX][tileY] = tileValue;
	}

	/**
	 * Indicates whether the customization is completed
	 * 
	 * @return true if customization completed; false otherwise
	 */
	public boolean customizationCompleted() {
		int highestTileValue = 0;
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (tiles[x][y] > highestTileValue) {
					highestTileValue = tiles[x][y];
				}
			}
		}
		return highestTileValue == 15;
	}

	public int[][] getTiles() {
		return tiles;
	}

	public boolean isCustom() {
		return custom;
	}

	public void setCustom(boolean custom) {
		this.custom = custom;
	}

	@Override
	public Board clone() {
		int[][] clonedTiles = new int[4][4];

		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				clonedTiles[x][y] = tiles[x][y];
			}
		}

		return new Board(clonedTiles);
	}

	/**
	 * Returns the x-coordinate of the blank tile.
	 * 
	 * @return x-coordinate of the blank tile.
	 */
	private int blankX() {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (tiles[x][y] == 0) {
					return x;
				}
			}
		}
		return -1;
	}

	/**
	 * Returns the y-coordinate of the blank tile.
	 * 
	 * @return y-coordinate of the blank tile.
	 */
	private int blankY() {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (tiles[x][y] == 0) {
					return y;
				}
			}
		}
		return -1;
	}
}
