import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * View as in MVC pattern.
 * 
 * @author Paul Jannik Offner
 * @version 12.01.2018
 */
public class UI {

	private JFrame frame;
	private JPanel boardPanel;
	private JButton[][] tileButtons;
	private JPanel commandPanel;
	private JButton shuffle;
	private JButton solve;
	private JButton custom;

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
				tileButtons[x][y].setFont(new Font("Arial", Font.PLAIN, 25));
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
		shuffle.setFont(new Font("Arial", Font.PLAIN, 25));
		commandPanel.add(shuffle);

		solve = new JButton("solve");
		solve.addActionListener(listener);
		solve.setFont(new Font("Arial", Font.PLAIN, 25));
		commandPanel.add(solve);

		custom = new JButton("custom");
		custom.addActionListener(listener);
		custom.setFont(new Font("Arial", Font.PLAIN, 25));
		commandPanel.add(custom);

		frame.add(commandPanel, BorderLayout.PAGE_END);

		frame.setVisible(true);
	}

	public void display(Board board) {
		if (board.isCustom()) {
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					tileButtons[x][y].setText("" + board.getTiles()[x][y]);
					tileButtons[x][y].setEnabled(board.getTiles()[x][y] == 0);
				}
			}
		} else {
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					tileButtons[x][y].setText("" + board.getTiles()[x][y]);
					tileButtons[x][y].setEnabled(board.isMovable(x, y));
				}
			}
		}
	}

	public void customization() {
		shuffle.setEnabled(false);
		solve.setEnabled(false);
		custom.setEnabled(false);
	}

	public void customizationCompleted() {
		shuffle.setEnabled(true);
		solve.setEnabled(true);
		custom.setEnabled(true);
	}

	public void solving() {
		shuffle.setEnabled(false);
		solve.setEnabled(false);
		custom.setEnabled(false);
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				tileButtons[x][y].setEnabled(false);
			}
		}
	}

	public void solvingCompleted() {
		shuffle.setEnabled(true);
		solve.setEnabled(true);
		custom.setEnabled(true);
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

	public JButton getCustom() {
		return custom;
	}
}
