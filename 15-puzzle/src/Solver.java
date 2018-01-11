import java.util.LinkedList;
import java.util.Random;

/**
 * Manipulating the board
 * 
 * @author paulj
 */
public class Solver {

	private LinkedList<LinkedList<Board>> frontier;

	public Solver() {
		frontier = new LinkedList<LinkedList<Board>>();
	}

	/**
	 * Shuffles the board by making random moves.
	 * 
	 * @param board
	 *            board to shuffle
	 * @param times
	 *            amount of random moves
	 * @return shuffled board
	 */
	public Board shuffle(Board board, int times) {
		for (int i = 0; i < times; i++) {
			int random = new Random().nextInt(board.possibleNextBoards().size());
			int item = 0;
			for (Board next : board.possibleNextBoards()) {
				if (random == item) {
					board = next;
				} else {
					item++;
				}
			}
		}
		return board;
	}

	/**
	 * Indicates whether a board has a solution.
	 * 
	 * @param board
	 *            the reference board
	 * @return true if this board has a solution; false otherwise
	 */
	public boolean isSolvable(Board board) {
		return true;
	}

	/**
	 * Solves the puzzle using a*-search
	 * 
	 * @param board
	 *            Puzzle to solve
	 * @return boards
	 */
	public LinkedList<Board> solve(Board board) {
		if (isSolvable(board)) {
			LinkedList<Board> path = new LinkedList<Board>();
			path.add(board);
			frontier.addFirst(path);

			while (path.getFirst().manhattenDistance() > 0) {
				path = removeBestPathFromFrontier();
				addNewPaths(path);
			}
			System.out.println("Puzzle solved");
			return removeBestPathFromFrontier();
		} else {
			System.out.println("This Puzzle is not solvable");
			return null;
		}
	}

	/**
	 * Removes best path from frontier
	 * 
	 * @return best path
	 */
	private LinkedList<Board> removeBestPathFromFrontier() {
		LinkedList<Board> result = null;
		int best = Integer.MAX_VALUE;
		for (int i = 0; i < frontier.size(); i++) {
			if (frontier.get(i).getFirst().manhattenDistance() < best) {
				result = frontier.get(i);
				best = result.getFirst().manhattenDistance();
			}
		}
		frontier.remove(result);
		return result;
	}

	/**
	 * Adds new paths to the frontier
	 * 
	 * @param path
	 *            latest removed path
	 */
	private void addNewPaths(LinkedList<Board> path) {
		for (Board nextBoard : path.getFirst().possibleNextBoards()) {
			LinkedList<Board> nextPath = new LinkedList<Board>();
			for (int i = 0; i < path.size(); i++) {
				nextPath.add(i, path.get(i).clone());
			}
			nextPath.addFirst(nextBoard);
			frontier.addFirst(nextPath);
		}
	}
}
