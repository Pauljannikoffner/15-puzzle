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
				LinkedList<Board> path = solver.solve(board);
				board = path.getFirst();
				for (int i = path.size(); i > 0; i--) {
					System.out.println("");
					for (int x = 0; x < 4; x++) {
						for (int y = 0; y < 4; y++) {
							System.out.print(prettyOutput(path.get(i - 1).getTiles()[x][y]));
						}
						System.out.println("");
					}
					ui.display(path.get(i - 1));
				}
				System.out.println("");
			} else {
				System.out.println("This Puzzle is not solvable.");
			}
		} else if (e.getSource().equals(ui.getCustomBoard())) {
			board.setCustom(true);
			board.setToEmptyState();
			ui.customization();
			ui.displayCustom(board);
		} else {
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					if (e.getSource().equals(ui.getTileButtons(x, y))) {
						if (board.isCustom()) {
							board.nextCustomTile(x, y);
							ui.displayCustom(board);
							if (board.customizationCompleted()) {
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

	private String prettyOutput(int i) {
		int length = String.valueOf(i).length();
		if (length == 1) {
			return " " + i + " ";
		} else {
			return i + " ";
		}
	}
}
