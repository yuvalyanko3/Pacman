package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;

import controller.Game;
import model.Question;
import utils.Consts;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class GameDialog extends JFrame {

	private final JPanel contentPanel = new JPanel();
	public JButton btnPlay;
	private JLabel questionText;
	private JTextPane textAnswe;
	private JLabel level;
	private Timer timer;
	private int interval = 3;
	private JLabel timerLbl;
	private Game game;
	private Question questionToShow = new Question(null); // question of candy
	private JLabel lblSocers_1;

	/**
	 * Create the dialog.
	 * 
	 * @param game
	 */
	public GameDialog(Game g, int flag) {
		game = g;
		/*--------------------------------------------Pause Panel-------------------------------------------------*/
		if (flag == 0) {
			setBounds(500, 300, 373, 194);
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(null);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setUndecorated(true);
			getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			setAlwaysOnTop(true);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

			JPanel panel_1 = new JPanel();
			panel_1.setBounds(0, 0, 373, 194);
			contentPanel.add(panel_1);
			panel_1.setLayout(null);

			JButton btnPlay_1 = new JButton("Play");
			btnPlay_1.setBounds(259, 146, 74, 23);
			panel_1.add(btnPlay_1);
			btnPlay_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
			btnPlay_1.setBackground(Color.WHITE);
			btnPlay_1.setContentAreaFilled(false);
			btnPlay_1.setOpaque(true);
			btnPlay_1.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					btnPlay_1.setBackground(Color.LIGHT_GRAY);
				}
				public void mouseExited(MouseEvent e) {
					btnPlay_1.setBackground(Color.WHITE);
				}
			});

			JLabel lblNewLabel = new JLabel("\r\n");
			lblNewLabel.setBounds(0, 0, 373, 194);
			panel_1.add(lblNewLabel);
			lblNewLabel.setIcon(new ImageIcon(GameDialog.class.getResource("/images/pause.png")));
			btnPlay_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					GameWindow.getGameWindow().bp.setVisible(false);
					game.setPaused(!game.isPaused());
					setVisible(false);
				}
			});
			
			
			/*--------------------------------------------Question Panel-------------------------------------------------*/
		} else if (flag == 1) { 
			setBounds(500, 300, 564, 426);
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(null);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setUndecorated(true);
			getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			setAlwaysOnTop(true);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

			JPanel panel = new JPanel();
			panel.setBounds(0, 0, 564, 426);
			contentPanel.add(panel);
			panel.setLayout(null);

			questionText = new JLabel("New label");
			questionText.setBounds(30, 32, 400, 92);
			panel.add(questionText);
			questionText.setForeground(new Color(255, 205, 0));
			questionText.setFont(new Font("Tahoma", Font.PLAIN, 20));
			questionText.setBounds(41, 32, 389, 92);
			questionText.setText("<html>" + questionToShow.toString() + "</html>");

			textAnswe = new JTextPane();
			textAnswe.setBackground(Color.BLACK);
			textAnswe.setText((String) null);
			textAnswe.setForeground(Color.WHITE);
			textAnswe.setFont(new Font("Tahoma", Font.PLAIN, 18));
			textAnswe.setBounds(51, 114, 467, 224);
			panel.add(textAnswe);
			textAnswe.setOpaque(false);

			JButton btnReady = new JButton("I'm Ready!");
			btnReady.setOpaque(true);
			btnReady.setContentAreaFilled(false);
			btnReady.setOpaque(true);
			btnReady.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnReady.setContentAreaFilled(false);
			btnReady.setBackground(Color.WHITE);
			btnReady.setBounds(221, 354, 129, 39);
			panel.add(btnReady);
			btnReady.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					GameWindow.getGameWindow().bp.setVisible(false);
					game.setPaused(!game.isPaused());
					setVisible(false);
				}
			});
			btnReady.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					btnReady.setBackground(Color.LIGHT_GRAY);
				}
				public void mouseExited(MouseEvent e) {
					btnReady.setBackground(Color.WHITE);
				}
			});

			level = new JLabel("Level: ");
			level.setFont(new Font("Tahoma", Font.PLAIN, 20));
			level.setVerticalAlignment(SwingConstants.TOP);
			level.setForeground(Color.WHITE);
			level.setBounds(474, 22, 90, 45);
			panel.add(level);

			JLabel lblNewLabel_2 = new JLabel("New label");
			lblNewLabel_2.setIcon(new ImageIcon(GameDialog.class.getResource("/images/question.png")));
			lblNewLabel_2.setBounds(0, 0, 564, 426);
			panel.add(lblNewLabel_2);
			
			
			/*--------------------------------------------Game Over Panel-------------------------------------------------*/
		} else if (flag == 2) {
			setBounds(500, 300, 545, 369);
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(null);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setUndecorated(true);
			getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			setAlwaysOnTop(true);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

			JPanel panel2 = new JPanel();
			panel2.setBounds(0, 0, 545, 369);
			contentPanel.add(panel2);
			panel2.setLayout(null);

			JButton btnNew = new JButton("New Game");
			btnNew.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnNew.setBounds(135, 296, 143, 34);
			btnNew.setBackground(Color.WHITE);
			btnNew.setContentAreaFilled(false);
			btnNew.setOpaque(true);
			panel2.add(btnNew);
			btnNew.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { 
					GameWindow.getGameWindow().pullThePlug();
					GameWindow.getGameWindow().bp.setVisible(false);
					setVisible(false);
					JPanel singleGame = new SingleGame();
					Start.menu.setContentPane(singleGame);
					Start.menu.setVisible(true);
				}
			});
			btnNew.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					btnNew.setBackground(Color.LIGHT_GRAY);
				}
				public void mouseExited(MouseEvent e) {
					btnNew.setBackground(Color.WHITE);
				}
			});

			JButton btnBack = new JButton("Back to Menu");
			btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnBack.setBounds(288, 296, 142, 34);
			btnBack.setBackground(Color.WHITE);
			btnBack.setContentAreaFilled(false);
			btnBack.setOpaque(true);
			panel2.add(btnBack);
			btnBack.addActionListener(new ActionListener() { // back to menu screen
				public void actionPerformed(ActionEvent e) {
					GameWindow.getGameWindow().pullThePlug();
					GameWindow.getGameWindow().bp.setVisible(false);
					setVisible(false);
					Start.menu.setContentPane(Start.menu.contentPane);
					Start.menu.setVisible(true);
				}
			});
			btnBack.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					btnBack.setBackground(Color.LIGHT_GRAY);
				}
				public void mouseExited(MouseEvent e) {
					btnBack.setBackground(Color.WHITE);
				}
			});

			JLabel lblNewLabel_3 = new JLabel("New label");
			lblNewLabel_3.setIcon(new ImageIcon(GameDialog.class.getResource("/images/gameOver.png")));
			lblNewLabel_3.setBounds(0, 0, 545, 369);
			panel2.add(lblNewLabel_3);
			
			
			/*--------------------------------------------Winner Panel-------------------------------------------------*/
		} else if (flag == 3) { 
			setBounds(500, 300, 545, 369);
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(null);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setUndecorated(true);
			getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			setAlwaysOnTop(true);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

			JPanel panel2 = new JPanel();
			panel2.setBounds(0, 0, 545, 369);
			contentPanel.add(panel2);
			panel2.setLayout(null);

			lblSocers_1 = new JLabel("Scores: " + game.getP1().getScore());
			lblSocers_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblSocers_1.setForeground(SystemColor.text);
			lblSocers_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblSocers_1.setBounds(209, 298, 136, 33);
			panel2.add(lblSocers_1);

			JButton btnNext = new JButton("Next");
			btnNext.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnNext.setBounds(446, 25, 76, 27);
			btnNext.setBackground(Color.WHITE);
			btnNext.setContentAreaFilled(false);
			btnNext.setOpaque(true);
			panel2.add(btnNext);
			btnNext.addActionListener(new ActionListener() { // back to menu screen
				public void actionPerformed(ActionEvent e) {
					GameWindow.getGameWindow().pullThePlug();
					GameWindow.getGameWindow().bp.setVisible(false);
					setVisible(false);
					Start.menu.setContentPane(Start.menu.contentPane);
					Start.menu.setVisible(true);
				}
			});
			btnNext.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnNext.setBackground(Color.LIGHT_GRAY);
			}
			public void mouseExited(MouseEvent e) {
				btnNext.setBackground(Color.WHITE);
			}
		});
			
			JLabel lblNewLabel_3 = new JLabel("New label");
			lblNewLabel_3.setIcon(new ImageIcon(GameDialog.class.getResource("/images/youWin.png")));
			lblNewLabel_3.setBounds(0, 0, 545, 369);
			panel2.add(lblNewLabel_3);
			
			
			/*--------------------------------------------Turn Next Panel-------------------------------------------------*/
		} else if (flag == 4) { 
			setBounds(500, 300, 280, 222);
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(null);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setUndecorated(true);
			getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			setAlwaysOnTop(true);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

			JPanel panel2 = new JPanel();
			panel2.setBounds(0, 0, 280, 222);
			contentPanel.add(panel2);
			panel2.setLayout(null);

			JButton btnNext = new JButton("Next");
			btnNext.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnNext.setBounds(450, 25, 72, 23);
			btnNext.setBackground(new Color(255, 205, 0));
			btnNext.setContentAreaFilled(false);
			btnNext.setOpaque(true);
			panel2.add(btnNext);
			btnNext.addActionListener(new ActionListener() { // back to menu screen
				public void actionPerformed(ActionEvent e) {
					GameWindow.getGameWindow().pullThePlug();
					GameWindow.getGameWindow().bp.setVisible(false);
					setVisible(false);
					Start.menu.setContentPane(Start.menu.contentPane);
					Start.menu.setVisible(true);
				}
			});

			timerLbl = new JLabel("3");
			timerLbl.setForeground(new Color(30, 144, 255));
			timerLbl.setFont(new Font("Tahoma", Font.PLAIN, 60));
			timerLbl.setBounds(177, 136, 48, 62);
			panel2.add(timerLbl);
			setTimer(timerLbl);

			JLabel lblNewLabel_3 = new JLabel("New label");
			lblNewLabel_3.setIcon(new ImageIcon(GameDialog.class.getResource("/images/nextTurn.png")));
			lblNewLabel_3.setBounds(0, 0, 280, 222);
			panel2.add(lblNewLabel_3);
		} 
		
		/*--------------------------------------------Finish Double Game Panel-------------------------------------------------*/
		else if (flag == 5) {
			setBounds(500, 300, 545, 369);
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(null);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setUndecorated(true);
			getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			setAlwaysOnTop(true);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

			JPanel panel2 = new JPanel();
			panel2.setBounds(0, 0, 545, 369);
			contentPanel.add(panel2);
			panel2.setLayout(null);

			JButton btnNew = new JButton("Yes!");
			btnNew.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnNew.setBounds(147, 296, 120, 33);
			btnNew.setBackground(Color.WHITE);
			btnNew.setContentAreaFilled(false);
			btnNew.setOpaque(true);
			panel2.add(btnNew);
			btnNew.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { // back to double game screen
					GameWindow.getGameWindow().pullThePlug();
					resetPlayersData();
					int ng = game.getP2().getNumOfGames();
					game.getP2().setNumOfGames(++ng);
					JPanel choose = new ChooseMaze(game.getP1(), game.getP2());
					GameWindow.getGameWindow().bp.setVisible(false);
					setVisible(false);
					Start.menu.setContentPane(choose);
					Start.menu.setVisible(true);
				}
			});
			btnNew.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					btnNew.setBackground(Color.LIGHT_GRAY);
				}
				public void mouseExited(MouseEvent e) {
					btnNew.setBackground(Color.WHITE);
				}
			});
			
			JButton btnBack = new JButton("Back to Menu");
			btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnBack.setBounds(283, 296, 127, 33);
			btnBack.setBackground(Color.WHITE);
			btnBack.setContentAreaFilled(false);
			btnBack.setOpaque(true);
			panel2.add(btnBack);
			btnBack.addActionListener(new ActionListener() { // back to menu screen
				public void actionPerformed(ActionEvent e) {
					GameWindow.getGameWindow().pullThePlug();
					JPanel stat = new Statistics(game.getP1(), game.getP2());
					GameWindow.getGameWindow().bp.setVisible(false);
					setVisible(false);
					Start.menu.setContentPane(stat);
					Start.menu.setVisible(true);
					GameWindow.getGameWindow().pullThePlug();
				}
			});
			btnBack.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					btnBack.setBackground(Color.LIGHT_GRAY);
				}
				public void mouseExited(MouseEvent e) {
					btnBack.setBackground(Color.WHITE);
				}
			});
			
			JLabel lblName1 = new JLabel("New label");
			lblName1.setForeground(Color.WHITE);
			lblName1.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblName1.setBounds(321, 117, 180, 38);
			lblName1.setText(game.getP1().getUserName());
			panel2.add(lblName1);
			
			JLabel lblName2 = new JLabel("New label");
			lblName2.setHorizontalAlignment(SwingConstants.RIGHT);
			lblName2.setForeground(Color.WHITE);
			lblName2.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblName2.setBounds(47, 117, 180, 38);
			lblName2.setText(game.getP2().getUserName());
			panel2.add(lblName2);
			
			JLabel lblScores1 = new JLabel("New label");
			lblScores1.setForeground(Color.WHITE);
			lblScores1.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblScores1.setBounds(321, 150, 180, 38);
			lblScores1.setText(String.valueOf(game.getP1().getScore()));
			panel2.add(lblScores1);
			
			JLabel lblScores2 = new JLabel("New label");
			lblScores2.setHorizontalAlignment(SwingConstants.RIGHT);
			lblScores2.setForeground(Color.WHITE);
			lblScores2.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblScores2.setBounds(47, 150, 180, 38);
			lblScores2.setText(String.valueOf(game.getP2().getScore()));
			panel2.add(lblScores2);

			JLabel lblNewLabel_3 = new JLabel("New label");
			lblNewLabel_3.setIcon(new ImageIcon(GameDialog.class.getResource("/images/finishDoubleGame.png")));
			lblNewLabel_3.setBounds(0, 0, 545, 369);
			panel2.add(lblNewLabel_3);
		}
	}

	/**
	 * set the question from the candy
	 */
	public void setQuestionToShow(Question q) {
		this.questionToShow = q;
		textAnswe.setText(questionToShow.getAnswer().toString());
		level.setText("<html>Level: " + questionToShow.getLevel() + "</html>");
		questionText.setText("<html>" + questionToShow.toString() + "</html>");
	}
	
	/**
	 * reset player data for a new game
	 */
	public void resetPlayersData () {
		game.getP1().setLifeScore(Consts.STARTING_LIFE);
		game.getP1().setCandiesEaten(0);
		game.getP1().setFinishCandies(false);
		game.getP2().setLifeScore(Consts.STARTING_LIFE);
		game.getP2().setCandiesEaten(0);
		game.getP2().setFinishCandies(false);
	}

	public void setTimer(JLabel label) {
		int delay = 1000;
		int period = 1000;
		timer = new Timer();
		interval = 3;
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				timerLbl.setText(String.valueOf(setInterval()));
				if (interval == 0) {
					setVisible(false);
					GameWindow.getGameWindow().bp.setVisible(false);
				}
			}
		}, delay, period);
	}

	private int setInterval() {
		if (interval == 1) {
			timer.cancel();
		}
		return --interval;
	}

	public int getInterval() {
		return interval;
	}
}
