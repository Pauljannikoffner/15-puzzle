import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class Controller implements ActionListener {

	private UI ui;
	private Solver solver;
	private Board board;

	public Controller() {
		board = new Board();
		board.goalState();
		board.testState();
		solver = new Solver();
		ui = new UI(board, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(ui.getShuffle())) {
			System.out.println("Hallo");
		} else if (e.getSource().equals(ui.getSolve())) {
			LinkedList<Board> path = solver.solve(board);
			System.out.println("Solution:");
			for (int i = path.size(); i > 1; i--) {
				System.out.println("");
				for (int x = 0; x < 4; x++) {
					for (int y = 0; y < 4; y++) {
						System.out.print(prettyOutput(path.get(i - 1).getTiles()[x][y]));
					}
					System.out.println("");
				}
				ui.move(path.get(i - 1));
			}
		} else {
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					if (e.getSource().equals(ui.getTileButtons(x, y))) {

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