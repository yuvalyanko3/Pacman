package view;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import controller.SysData;
import utils.Consts;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Menu extends JFrame {
	public JPanel contentPane;
	private static Menu instance;
	// boolean mute = true;

	public static Menu getInstance() {
		if (instance == null)
			instance = new Menu();
		return instance;
	}

	/**
	 * Create the frame.
	 */
	public Menu() {

		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		SysData.getInstance().readJson();
		setPreferredSize(new Dimension(1019, 688));
		setMaximumSize(new Dimension(1019, 688));
		setMinimumSize(new Dimension(1019, 688));
		setLocationRelativeTo(null);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				windowClosing2(e);
			}
		});

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btndoubleButton = new JButton("VERSUS");
		btndoubleButton.setBackground(Color.WHITE);
		btndoubleButton.setContentAreaFilled(false);
		btndoubleButton.setOpaque(true);
		btndoubleButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btndoubleButton.setBounds(510, 514, 190, 48);
		contentPane.add(btndoubleButton);
		btndoubleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(SysData.getInstance().getQuestions().size() < (Consts.SINGLE_QUESTIONS_NUM * 2))
					JOptionPane.showMessageDialog(null, "You should have at least " + (Consts.SINGLE_QUESTIONS_NUM * 2) + " questions in order to play.", "Error", JOptionPane.ERROR_MESSAGE);
				else
				{
					JPanel doubleGame = new DoubleGame();
					contentPane.setVisible(false);
					setContentPane(doubleGame);
				}
			}
		});

		JButton btnSingleGame = new JButton("Single Game");
		btnSingleGame.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSingleGame.setBackground(Color.WHITE);
		btnSingleGame.setContentAreaFilled(false);
		btnSingleGame.setOpaque(true);
		btnSingleGame.setBounds(297, 514, 198, 48);
		contentPane.add(btnSingleGame);
		btnSingleGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(SysData.getInstance().getQuestions().size() < Consts.SINGLE_QUESTIONS_NUM)
					JOptionPane.showMessageDialog(null, "You should have at least " + Consts.SINGLE_QUESTIONS_NUM + " questions in order to play.", "Error", JOptionPane.ERROR_MESSAGE);
				else
				{
					JPanel singleGame = new SingleGame();
					contentPane.setVisible(false);
					setContentPane(singleGame);
				}
			}
		});

		JButton btnManageQuestions = new JButton("Manage Questions");
		btnManageQuestions.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnManageQuestions.setBackground(Color.WHITE);
		btnManageQuestions.setContentAreaFilled(false);
		btnManageQuestions.setOpaque(true);
		btnManageQuestions.setBounds(510, 573, 190, 48);
		contentPane.add(btnManageQuestions);
		btnManageQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel manageQues = new ManageQues();
				contentPane.setVisible(false);
				setContentPane(manageQues);
			}
		});

		JButton btnGamesHistory = new JButton("Games History");
		btnGamesHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JPanel history = new HistoryGames();
				contentPane.setVisible(false);
				setContentPane(history);
			}
		});

		btnGamesHistory.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnGamesHistory.setBackground(Color.WHITE);
		btnGamesHistory.setContentAreaFilled(false);
		btnGamesHistory.setOpaque(true);
		btnGamesHistory.setBounds(295, 573, 200, 48);
		contentPane.add(btnGamesHistory);

		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(0, -20, 1019, 719);
		lblNewLabel.setIcon(new ImageIcon("assets/images/menu.png"));
		contentPane.add(lblNewLabel);
	}

	protected void windowClosing2(WindowEvent e) {
		int answer = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Close", JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION)
		{
			this.setVisible(false);
			this.dispose();
			System.exit(0);
		}
	}
}
