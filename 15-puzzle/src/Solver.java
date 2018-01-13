import java.util.LinkedList;
import java.util.Random;

/**
 * Used to solve a puzzle.
 * 
 * @author Paul Jannik Offner
 * @version 12.01.2018
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
		int blankPosition = 0;
		int inversionCount = 0;

		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (board.getTiles()[x][y] == 0) {
					blankPosition = x;
				}
			}
		}

		int[] boardArray = new int[15];

		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (board.getTiles()[x][y] != 0) {
					for (int i = 0; i < boardArray.length; i++) {
						if (boardArray[i] == 0) {
							boardArray[i] = board.getTiles()[x][y];
							break;
						}
					}
				}
			}
		}
		
		for (int i = 0; i < boardArray.length - 1; i++) {
			if (boardArray[i] > boardArray[i+1]) {
				inversionCount++;
			}
		}

		return (blankPosition + inversionCount) % 2 == 1;
	}

	/**
	 * Solves the puzzle using a*-search
	 * 
	 * @param board
	 *            Puzzle to solve
	 * @return boards
	 */
	public LinkedList<Board> solve(Board board) {
		LinkedList<Board> path = new LinkedList<Board>();
		path.add(board);
		frontier.addFirst(path);

		while (bestPathInFrontier().getFirst().manhattenDistance() > 0) {
			path = bestPathInFrontier();
			frontier.remove(path);
			addNewPaths(path);
		}
		System.out.println("Puzzle solved! Solution:");
		return bestPathInFrontier();
	}

	/**
	 * Removes best path from frontier
	 * 
	 * @return best path
	 */
	private LinkedList<Board> bestPathInFrontier() {
		LinkedList<Board> result = null;
		int best = Integer.MAX_VALUE;
		for (int i = 0; i < frontier.size(); i++) {
			LinkedList<Board> path = frontier.get(i);
			if (path.getFirst().manhattenDistance() + path.size() < best) {
				result = frontier.get(i);
				best = result.getFirst().manhattenDistance() + path.size();
			}
		}
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
			if (!path.contains(nextBoard)) {
				LinkedList<Board> nextPath = new LinkedList<Board>();
				for (int i = 0; i < path.size(); i++) {
					nextPath.add(i, path.get(i).clone());
				}
				nextPath.addFirst(nextBoard);
				frontier.addFirst(nextPath);
			}
		}
	}
}
