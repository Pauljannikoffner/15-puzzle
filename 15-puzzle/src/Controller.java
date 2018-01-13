import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * Controller as in MVC pattern.
 * 
 * @author Paul Jannik Offner
 * @version 12.01.2018
 */
public class Controller implements ActionListener {
	
	private UI ui;
	private Solver solver;
	private Board board;

	public Controller() {
		board = new Board();
		board.setToGoalState();
		solver = new Solver();
		ui = new UI(board, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(ui.getShuffle())) {
			board = solver.shuffle(board, 5);
			ui.display(board);
		} else if (e.getSource().equals(ui.getSolve())) {
			if (solver.isSolvable(board)) {
				ui.solving();
				LinkedList<Board> goalPath = solver.solve(board);
				board = goalPath.getFirst();
				printPathsToConsole(goalPath);
				ui.solvingCompleted();
				showGoalPath(goalPath);
			} else {
				System.out.println("This Puzzle is not solvable.");
			}
		} else if (e.getSource().equals(ui.getCustom())) {
			board.setCustom(true);
			board.setToEmptyState();
			ui.customization();
			ui.display(board);
		} else {
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					if (e.getSource().equals(ui.getTileButtons(x, y))) {
						if (board.isCustom()) {
							board.nextCustomTile(x, y);
							ui.display(board);
							if (board.isCustomizationCompleted()) {
								board.setCustom(false);
								ui.customizationCompleted();
								ui.display(board);
							}
						} else {
							board.move(x, y);
							ui.display(board);
						}
					}
				}
			}
		}
	}

	private void printPathsToConsole(LinkedList<Board> goalPath) {
		for (int i = goalPath.size(); i > 0; i--) {
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					System.out.print(prettyOutput(goalPath.get(i - 1).getTiles()[x][y]));
				}
				System.out.print(System.lineSeparator());
			}
			System.out.print(System.lineSeparator());
		}
	}

	private String prettyOutput(int tileValue) {
		int length = String.valueOf(tileValue).length();
		if (length == 1) {
			return " " + tileValue + " ";
		} else {
			return tileValue + " ";
		}
	}

	private void showGoalPath(LinkedList<Board> goalPath) {
		for (int i = goalPath.size(); i > 0; i--) {
			ui.display(goalPath.get(i - 1));
		}
	}
}
