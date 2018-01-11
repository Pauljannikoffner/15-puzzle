import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UI {

	private JFrame frame;
	private JPanel boardPanel;
	private JButton[][] tileButtons;
	private JPanel commandPanel;
	private JButton shuffle;
	private JButton solve;

	public UI(Board board, ActionListener listener) {
		frame = new JFrame("Fifteen-Puzzle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setSize(400, 500);

		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(4, 4));
		boardPanel.setBackground(Color.WHITE);

		tileButtons = new JButton[4][4];
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				tileButtons[x][y] = new JButton("" + board.getTiles()[x][y]);
				tileButtons[x][y].addActionListener(listener);
				tileButtons[x][y].setPreferredSize(new Dimension(100, 100));
				tileButtons[x][y].setEnabled(board.isMovable(x, y));
				boardPanel.add(tileButtons[x][y]);
			}
		}

		frame.add(boardPanel, BorderLayout.PAGE_START);

		commandPanel = new JPanel();
		commandPanel.setLayout(new FlowLayout());
		commandPanel.setBackground(Color.WHITE);

		shuffle = new JButton("shuffle");
		shuffle.addActionListener(listener);
		commandPanel.add(shuffle);

		solve = new JButton("solve");
		solve.addActionListener(listener);
		commandPanel.add(solve);

		frame.add(commandPanel, BorderLayout.PAGE_END);

		frame.setVisible(true);
	}

	public void display(Board board) {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				tileButtons[x][y].setText("" + board.getTiles()[x][y]);
				tileButtons[x][y].setEnabled(board.isMovable(x, y));
			}
		}
	}

	public JButton getTileButtons(int x, int y) {
		return tileButtons[x][y];
	}

	public JButton getShuffle() {
		return shuffle;
	}

	public JButton getSolve() {
		return solve;
	}
}
