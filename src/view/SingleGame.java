package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Player;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class SingleGame extends JPanel {
	private JTextField userName;

	/**
	 * Create the panel.
	 */
	public SingleGame() {
		this.setBounds(150, 0, 1019, 688);
		this.setLayout(null);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBounds(0, 0, 1017, 688);
		add(panel);

		JButton btnBackButton = new JButton("Back");
		btnBackButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnBackButton.setContentAreaFilled(false);
		btnBackButton.setOpaque(true);
		btnBackButton.setBackground(Color.BLACK);
		btnBackButton.setForeground(Color.WHITE);
		btnBackButton.setBounds(936, 11, 81, 23);
		panel.add(btnBackButton);
		btnBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Start.menu.setContentPane(Start.menu.contentPane);
				Start.menu.contentPane.setVisible(true);
			}
		});

		userName = new JTextField();
		userName.setBackground(SystemColor.menu);
		userName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					start();
				}
			}
		});
		userName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		userName.setBounds(412, 449, 231, 39);
		panel.add(userName);
		userName.setColumns(10);
		userName.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (userName.getText().length() >= 8) // limit textfield to 8 characters
					e.consume();
			}
		});

		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnStart.setBackground(Color.WHITE);
		btnStart.setContentAreaFilled(false);
		btnStart.setOpaque(true);
		btnStart.setBounds(470, 522, 117, 48);
		panel.add(btnStart);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		btnStart.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				btnStart.setBackground(Color.LIGHT_GRAY);
			}

			public void mouseExited(MouseEvent e) {
				btnStart.setBackground(Color.WHITE);
			}
		});

		JLabel label = new JLabel();
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setIcon(new ImageIcon("assets/images/singleGame.png"));
		label.setBounds(0, -13, 1017, 695);
		panel.add(label);
	}

	/**
	 * start the game after enter click or mouse click
	 */
	private void start() {
		if (userName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Please enter player name!", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			Player p = new Player(userName.getText());
			JPanel choose = new ChooseMaze(p, null);
			setVisible(false);
			Start.menu.setContentPane(choose);
			Start.menu.contentPane.setVisible(true);
		}
	}
}
