package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Game;
import model.Player;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class Instructions extends JPanel {
	private JLabel label;

	/**
	 * Create the panel.
	 */
	public Instructions(Player p1, Player p2, String type) {
		this.setBounds(150, 0, 1017, 658);
		this.setLayout(null);
		String img = new String();

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBounds(0, 0, 1017, 658);
		add(panel);

		JButton btnPlay = new JButton("Let's Play!");
		btnPlay.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnPlay.setBackground(SystemColor.window);
		btnPlay.setContentAreaFilled(false);
		btnPlay.setOpaque(true);
		btnPlay.setBounds(433, 580, 142, 48);
		panel.add(btnPlay);
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game game = new Game(type, p1, p2);
				if (p1 != null) {
					p1.initGame(game);
					p1.getGameState().init();
				}
				if (p2 != null) {
					p2.initGame(game);
					p2.getGameState().init();
				}
				GameWindow gw = new GameWindow("", game, p1, p2);
				gw.setVisible(true);
				Start.menu.setVisible(false);
				Start.menu.dispose();
			}
		});
		btnPlay.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnPlay.setBackground(Color.LIGHT_GRAY);
			}

			public void mouseExited(MouseEvent e) {
				btnPlay.setBackground(Color.WHITE);
			}
		});

		label = new JLabel();
		label.setBackground(Color.BLACK);
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		// get the correct image for game type
		if (type == "normal") {
			img = "/images/instructions.png";
			label.setIcon(new ImageIcon(Instructions.class.getResource(img)));
		} else if (type == "space") {
			img = "/images/instructionsSpace.png";
			label.setIcon(new ImageIcon(Instructions.class.getResource(img)));
		} else if (type == "jungle") {
			img = "/images/instructionsJungle.png";
			label.setIcon(new ImageIcon(Instructions.class.getResource(img)));
		}
		label.setIcon(new ImageIcon(Instructions.class.getResource(img)));
		label.setBounds(0, 0, 1017, 658);
		panel.add(label);
	}
}
