import java.util.HashSet;

/**
 * Representation of a board.
 * 
 * @author paulj
 */
public class Board {

	private int[][] tiles;

	/**
	 * Creates a new board and initializes it in the goal state.
	 */
	public Board() {
		tiles = new int[4][4];
	}

	/**
	 * Initializes the board in the goal state
	 */
	public void initialize() {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				tiles[x][y] = 4 * y + x + 1;
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
	 * not-optimal tile; returns 0 if goal is reached).
	 * 
	 * @return hamming-distance of the board
	 */
	public int hammingDistance() {
		int result = 0;

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (tiles[x][y] != (4 * y + x + 1)) {
					result++;
				}
			}
		}

		return result;
	}

	/**
	 * Returns the manhatten-distance of the board (sum of the manhatten-distances
	 * of all tiles to their optimal positions).
	 * 
	 * @return manhatten-distance of the board
	 */
	public int manhattenDistance() {
		int result = 0;

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				int tile = tiles[x][y];
				result += Math.abs(tile / 4 - (x + 1) / 4);
				result += Math.abs(tile % 4 - (y + 1) % 4);
			}
		}

		return result;
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

	public int[][] getTiles() {
		return tiles;
	}

	public void setTiles(int[][] tiles) {
		this.tiles = tiles;
	}

	@Override
	public Board clone() {
		Board result = new Board();

		int[][] clonedTiles = new int[4][4];

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				clonedTiles[x][y] = tiles[x][y];
			}
		}

		result.setTiles(clonedTiles);

		return result;
	}

	/**
	 * Returns the x-coordinate of the blank tile.
	 * 
	 * @return x-coordinate of the blank tile.
	 */
	private int blankX() {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
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
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (tiles[x][y] == 0) {
					return y;
				}
			}
		}
		return -1;
	}
}
