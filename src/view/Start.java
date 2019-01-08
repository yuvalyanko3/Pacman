package view;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;

@SuppressWarnings("serial")
public class Start extends JFrame {
	public static Menu menu = new Menu();
	private JPanel contentPane;
	public static Start frame;
	private JLabel blackScreen = new JLabel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Start();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Start() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 15, 1019, 688);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);

		blackScreen = new JLabel("");
		blackScreen.setBounds(0, 0, 1017, 688);
		blackScreen.setIcon(new ImageIcon("assets/images/blackScreen.png"));
		blackScreen.setBackground(Color.BLACK);
		blackScreen.setVisible(false);
		contentPane.add(blackScreen);

		JLabel lblNewLabel_1 = new JLabel("Press anywhere to continue");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(750, 634, 177, 14);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setVisible(false);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Start.class.getResource("/images/zebra.png")));
		lblNewLabel_2.setBounds(0, 0, 1019, 669);
		contentPane.add(lblNewLabel_2);
		lblNewLabel_2.setVisible(false);

		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(0, 0, 1019, 688);
		lblNewLabel.setIcon(new ImageIcon("assets/images/start.png"));
		contentPane.add(lblNewLabel);
		lblNewLabel.setVisible(false);
		// display first screen
		int delay = 1000;
		new java.util.Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				lblNewLabel_2.setVisible(true);
				this.cancel();
			}
		}, delay);

		new java.util.Timer().scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

			}
		}, 10, 3000);

		int delay2 = 3000;
		new java.util.Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				lblNewLabel_2.setVisible(false);
				int delay3 = 500;
				new java.util.Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						lblNewLabel.setVisible(true);
						lblNewLabel_1.setVisible(true);
						this.cancel();
					}
				}, delay3);
				this.cancel();
			}
		}, delay2);

		lblNewLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				JFrame ys = new YoutubeSearch();
				ys.setVisible(true);
				blackScreen.setVisible(true);
			}
		});
	}
}
