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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class ChooseMaze extends JPanel {
	private Player p1;
	private Player p2;

	/**
	 * Create the panel.
	 */
	public ChooseMaze(Player p1, Player p2) {
		this.setBounds(150, 0, 1017, 658);
		this.setLayout(null);
		this.p1 = p1;
		this.p2 = p2;

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBounds(0, 0, 1017, 658);
		add(panel);

		JButton btnBackButton = new JButton("Back");
		btnBackButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnBackButton.setContentAreaFilled(false);
		btnBackButton.setOpaque(true);
		btnBackButton.setBackground(Color.BLACK);
		btnBackButton.setForeground(Color.WHITE);
		btnBackButton.setBounds(936, 624, 81, 23);
		panel.add(btnBackButton);
		btnBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Start.menu.setContentPane(Start.menu.contentPane);
				Start.menu.contentPane.setVisible(true);
			}
		});

		JButton btnSpace = new JButton("Space");
		btnSpace.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnSpace.setBackground(new Color(255, 255, 255));
		btnSpace.setContentAreaFilled(false);
		btnSpace.setOpaque(true);
		btnSpace.setBounds(149, 509, 117, 48);
		panel.add(btnSpace);
		btnSpace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showInstructions("space");
			}
		});
		btnSpace.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnSpace.setBackground(Color.LIGHT_GRAY);

			}

			public void mouseExited(MouseEvent e) {
				btnSpace.setBackground(Color.WHITE);
			}
		});

		JButton btnNormal = new JButton("Normal");
		btnNormal.setContentAreaFilled(false);
		btnNormal.setOpaque(true);
		btnNormal.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnNormal.setContentAreaFilled(false);
		btnNormal.setBackground(new Color(255, 255, 255));
		btnNormal.setBounds(447, 509, 117, 48);
		panel.add(btnNormal);
		btnNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showInstructions("normal");
			}
		});
		btnNormal.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnNormal.setBackground(Color.LIGHT_GRAY);

			}

			public void mouseExited(MouseEvent e) {
				btnNormal.setBackground(Color.WHITE);
			}
		});

		JButton btnJungle = new JButton("Jungle");
		btnJungle.setContentAreaFilled(false);
		btnJungle.setOpaque(true);
		btnJungle.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnJungle.setContentAreaFilled(false);
		btnJungle.setBackground(new Color(255, 255, 255));
		btnJungle.setBounds(756, 509, 117, 48);
		panel.add(btnJungle);
		btnJungle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showInstructions("jungle");
			}
		});
		btnJungle.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnJungle.setBackground(Color.LIGHT_GRAY);

			}

			public void mouseExited(MouseEvent e) {
				btnJungle.setBackground(Color.WHITE);
			}
		});

		JLabel label = new JLabel();
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setIcon(new ImageIcon(ChooseMaze.class.getResource("/images/chooseMaze.png")));
		label.setBounds(0, -30, 1017, 688);
		panel.add(label);
	}

	/**
	 * show instructions screen to the correct game type
	 * 
	 * @param type
	 */
	public void showInstructions(String type) {
		JPanel ins = new Instructions(p1, p2, type);
		setVisible(false);
		Start.menu.setContentPane(ins);
		Start.menu.contentPane.setVisible(true);
	}
}