package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Player;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Statistics extends JPanel {

	/**
	 * Create the panel.
	 */
	public Statistics(Player p1, Player p2) {
		this.setBounds(150, 0, 1017, 658);
		this.setLayout(null);
		double perc1 = 0;
		double perc2 = 0;
		// Check to not divide in 0
		if (p1.getAnswersDone() > 0)
			perc1 = p1.getCorrectAnswer() * 100 / p1.getAnswersDone();
		if (p2.getAnswersDone() > 0)
			perc2 = p2.getCorrectAnswer() * 100 / p2.getAnswersDone();

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBounds(0, -14, 1017, 672);
		add(panel);

		JLabel bestPlayer = new JLabel("The best player", SwingConstants.CENTER);
		bestPlayer.setForeground(new Color(0, 0, 0));
		bestPlayer.setFont(new Font("Tahoma", Font.PLAIN, 22));
		bestPlayer.setBounds(452, 87, 122, 34);
		panel.add(bestPlayer);
		if (p1.getScore() > p2.getScore())
			bestPlayer.setText(p1.getUserName());
		if (p1.getScore() < p2.getScore())
			bestPlayer.setText(p2.getUserName());
		if (p1.getScore() == p2.getScore())
			bestPlayer.setText("Nobody");

		JLabel smartPlayer = new JLabel("The best player", SwingConstants.CENTER);
		smartPlayer.setForeground(new Color(0, 0, 0));
		smartPlayer.setFont(new Font("Tahoma", Font.PLAIN, 22));
		smartPlayer.setBounds(452, 515, 122, 34);
		panel.add(smartPlayer);
		if (perc1 > perc2)
			smartPlayer.setText(p1.getUserName());
		if (perc1 < perc2)
			smartPlayer.setText(p2.getUserName());
		if (perc1 == perc2)
			smartPlayer.setText("Nobody");

		JLabel percPlayer1 = new JLabel("100%", SwingConstants.CENTER);
		percPlayer1.setForeground(Color.BLACK);
		percPlayer1.setFont(new Font("Tahoma", Font.PLAIN, 80));
		percPlayer1.setBounds(680, 366, 213, 106);
		panel.add(percPlayer1);
		if (p1.getAnswersDone() > 0)
			percPlayer1.setText(String.valueOf(p1.getCorrectAnswer() * 100 / p1.getAnswersDone()) + "%");
		else
			percPlayer1.setText("0%");

		JLabel percPlayer2 = new JLabel("100%", SwingConstants.CENTER);
		percPlayer2.setForeground(new Color(0, 0, 0));
		percPlayer2.setFont(new Font("Tahoma", Font.PLAIN, 80));
		percPlayer2.setBounds(141, 366, 213, 106);
		panel.add(percPlayer2);
		if (p2.getAnswersDone() > 0)
			percPlayer2.setText(String.valueOf(p2.getCorrectAnswer() * 100 / p2.getAnswersDone()) + "%");
		else
			percPlayer2.setText("0%");

		JLabel numCorrect1 = new JLabel("0", SwingConstants.RIGHT);
		numCorrect1.setForeground(Color.BLACK);
		numCorrect1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		numCorrect1.setBounds(677, 471, 80, 34);
		panel.add(numCorrect1);
		numCorrect1.setText(p1.getCorrectAnswer() + "/" + p1.getAnswersDone());

		JLabel numCorrect2 = new JLabel("0", SwingConstants.RIGHT);
		numCorrect2.setForeground(Color.BLACK);
		numCorrect2.setFont(new Font("Tahoma", Font.PLAIN, 30));
		numCorrect2.setBounds(136, 472, 80, 34);
		panel.add(numCorrect2);
		numCorrect2.setText(p2.getCorrectAnswer() + "/" + p2.getAnswersDone());

		JLabel numGames = new JLabel("", SwingConstants.CENTER);
		numGames.setForeground(new Color(0, 0, 0));
		numGames.setFont(new Font("Tahoma", Font.PLAIN, 99));
		numGames.setBounds(455, 245, 108, 144);
		panel.add(numGames);
		numGames.setText(String.valueOf(p2.getNumOfGames()));

		JLabel scores1 = new JLabel("0/0", SwingConstants.CENTER);
		scores1.setForeground(Color.WHITE);
		scores1.setFont(new Font("Tahoma", Font.PLAIN, 80));
		scores1.setBounds(670, 180, 176, 86);
		panel.add(scores1);
		scores1.setText(String.valueOf(p1.getScore()));

		JLabel scores2 = new JLabel("0/0", SwingConstants.CENTER);
		scores2.setForeground(Color.WHITE);
		scores2.setFont(new Font("Tahoma", Font.PLAIN, 80));
		scores2.setBounds(180, 164, 176, 119);
		panel.add(scores2);
		scores2.setText(String.valueOf(p2.getScore()));

		JLabel player2 = new JLabel("Player Name", SwingConstants.CENTER);
		player2.setForeground(Color.WHITE);
		player2.setFont(new Font("Tahoma", Font.BOLD, 33));
		player2.setBounds(162, 100, 213, 119);
		panel.add(player2);
		player2.setText(p2.getUserName());

		JLabel Player1 = new JLabel("Player Name", SwingConstants.CENTER);
		Player1.setForeground(Color.WHITE);
		Player1.setFont(new Font("Tahoma", Font.BOLD, 33));
		Player1.setBounds(652, 100, 213, 119);
		panel.add(Player1);
		Player1.setText(p1.getUserName());

		JButton btnNext = new JButton("Next");
		btnNext.setOpaque(true);
		btnNext.setForeground(Color.WHITE);
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnNext.setContentAreaFilled(false);
		btnNext.setBackground(Color.BLACK);
		btnNext.setBounds(936, 638, 81, 23);
		panel.add(btnNext);
		btnNext.addActionListener(new ActionListener() { // back to menu
			public void actionPerformed(ActionEvent e) {
				Start.menu.setContentPane(Start.menu.contentPane);
				Start.menu.contentPane.setVisible(true);
			}
		});

		JLabel label = new JLabel("");
		label.setForeground(new Color(255, 255, 255));
		label.setIcon(new ImageIcon(Statistics.class.getResource("/images/stat.png")));
		label.setBounds(0, 14, 1017, 658);
		panel.add(label);
	}
}