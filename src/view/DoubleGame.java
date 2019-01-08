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
public class DoubleGame extends JPanel {
	private JTextField userName1;
	private JTextField userName2;

	/**
	 * Create the panel.
	 */
	public DoubleGame() {
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

		userName1 = new JTextField();
		userName1.setBackground(SystemColor.menu);
		userName1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		userName1.setBounds(654, 179, 231, 39);
		panel.add(userName1);
		userName1.setColumns(10);
		userName1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (userName1.getText().length() >= 8) // limit textfield to 8 characters
					e.consume();
			}
		});

		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnStart.setBackground(Color.WHITE);
		btnStart.setContentAreaFilled(false);
		btnStart.setOpaque(true);
		btnStart.setBounds(712, 477, 117, 48);
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

		userName2 = new JTextField();
		userName2.setBackground(SystemColor.menu);
		userName2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					start();
				}
			}
		});
		userName2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		userName2.setColumns(10);
		userName2.setBounds(654, 378, 231, 39);
		panel.add(userName2);
		userName2.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (userName2.getText().length() >= 8) // limit textfield to 10 characters
					e.consume();
			}
		});

		JLabel label = new JLabel();
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setIcon(new ImageIcon("assets/images/dubleGame.png"));
		label.setBounds(0, -13, 1017, 695);
		panel.add(label);
	}

	private void start() {
		if (userName1.getText().equals("") || userName2.getText().equals(""))
			JOptionPane.showMessageDialog(null, "Please enter player name!", "Error", JOptionPane.ERROR_MESSAGE);
		else if (userName2.getText().equals(userName1.getText()))
			JOptionPane.showMessageDialog(null, "Please enter different names!", "Error", JOptionPane.ERROR_MESSAGE);
		else {
			Player p1 = new Player(userName1.getText());
			Player p2 = new Player(userName2.getText());
			JPanel choose = new ChooseMaze(p1, p2);
			setVisible(false);
			Start.menu.setContentPane(choose);
			Start.menu.contentPane.setVisible(true);
		}
	}
}
