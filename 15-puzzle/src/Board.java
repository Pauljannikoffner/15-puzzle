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
		initialize();
	}

	public int[][] getTiles() {
		return tiles;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Board) {
			Board board = (Board) o;
			boolean result = true;
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					result = result && board.getTiles()[x][y] == tiles[x][y];
				}
			}
			return result;
		} else {
			return false;
		}
	}

	private void initialize() {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				tiles[x][y] = y * 4 + x + 1;
			}
		}
		tiles[3][3] = 0;
	}
}
