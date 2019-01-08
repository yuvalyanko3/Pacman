package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controller.Game;
import controller.GameLogic;
import controller.SysData;
import model.Player;
import utils.Consts;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JRootPane;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {

	private static final int WIDTH = 680;
	private static final int HEIGHT = 680;
	private JLabel playerName1;
	private Game game;
	private JLabel toReturn;
	private JLabel socers1;
	private JLabel socers2;
	private JLabel timePlayer1;
	private JLabel timePlayer2;
	private JLabel cTimer1;
	private JLabel cTimer2;
	public static GameWindow instance;
	private ArrayList<JLabel> playerOneLife;
	private ArrayList<JLabel> playerTwoLife;
	private Player p1;
	private Player p2;
	private Timer timer;
	private int interval;
	private boolean isTimerRunning;
	private JLabel lblCorrectAnswer1;
	public JFrame bp = new BlackPanel();

	public GameWindow(String title, Game game, Player p1, Player p2) {
		super(title);
		instance = this;
		this.game = game;
		this.p1 = p1;
		this.p2 = p2;
		this.isTimerRunning = false;
		playerOneLife = new ArrayList<>();
		playerTwoLife = new ArrayList<>();
		setBounds(150, 15, 1019, 700);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.BLACK);

		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		game.setBounds(175, 10, Game.WIDTH, Game.HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		getContentPane().add(game);
		game.start();
		game.getPacman().setPlayer(p1);

		JLabel pacman1 = new JLabel("New label");
		pacman1.setIcon(new ImageIcon("assets/images/pacman.png"));
		pacman1.setBounds(866, 23, 72, 66);
		getContentPane().add(pacman1);

		playerName1 = new JLabel(p1.getUserName());
		playerName1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		playerName1.setForeground(Color.WHITE);
		playerName1.setBounds(866, 97, 132, 34);
		getContentPane().add(playerName1);

		socers1 = new JLabel(String.valueOf(p1.getScore()));
		socers1.setForeground(Color.WHITE);
		socers1.setFont(new Font("Tahoma", Font.PLAIN, 22));
		socers1.setBounds(866, 179, 72, 34);
		getContentPane().add(socers1);

		lblCorrectAnswer1 = new JLabel("<html>Correct <br>Answer!</html>");
		lblCorrectAnswer1.setForeground(new Color(192, 192, 192));
		lblCorrectAnswer1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCorrectAnswer1.setBounds(866, 224, 153, 59);
		getContentPane().add(lblCorrectAnswer1);
		lblCorrectAnswer1.setVisible(false);

		JLabel P1life1 = new JLabel("");
		playerOneLife.add(P1life1);
		P1life1.setIcon(new ImageIcon("assets/images/life.png"));
		P1life1.setBounds(866, 139, 29, 39);
		getContentPane().add(P1life1);

		JLabel P1life2 = new JLabel("");
		playerOneLife.add(P1life2);
		P1life2.setIcon(new ImageIcon("assets/images/life.png"));
		P1life2.setBounds(908, 139, 29, 39);
		getContentPane().add(P1life2);

		JLabel P1life3 = new JLabel("");
		playerOneLife.add(P1life3);
		P1life3.setIcon(new ImageIcon("assets/images/life.png"));
		P1life3.setBounds(949, 139, 29, 39);
		getContentPane().add(P1life3);

		if (p2 != null) {
			JLabel pacman2 = new JLabel("New label");
			pacman2.setIcon(new ImageIcon("assets/images/pacman2.png"));
			pacman2.setBounds(31, 24, 72, 66);
			getContentPane().add(pacman2);

			JLabel playerName2 = new JLabel(p2.getUserName());
			playerName2.setForeground(Color.WHITE);
			playerName2.setFont(new Font("Tahoma", Font.PLAIN, 22));
			playerName2.setBounds(31, 98, 132, 34);
			getContentPane().add(playerName2);

			JLabel P2life1 = new JLabel("");
			playerTwoLife.add(P2life1);
			P2life1.setBounds(31, 140, 29, 39);
			P2life1.setIcon(new ImageIcon("assets/images/life.png"));
			getContentPane().add(P2life1);

			JLabel P2life2 = new JLabel("");
			playerTwoLife.add(P2life2);
			P2life2.setBounds(73, 140, 29, 39);
			P2life2.setIcon(new ImageIcon("assets/images/life.png"));
			getContentPane().add(P2life2);

			JLabel P2life3 = new JLabel("");
			playerTwoLife.add(P2life3);
			P2life3.setBounds(114, 140, 29, 39);
			P2life3.setIcon(new ImageIcon("assets/images/life.png"));
			getContentPane().add(P2life3);

			socers2 = new JLabel(String.valueOf(p2.getScore()));
			socers2.setForeground(Color.WHITE);
			socers2.setFont(new Font("Tahoma", Font.PLAIN, 22));
			socers2.setBounds(31, 180, 72, 34);
			getContentPane().add(socers2);

			timePlayer2 = new JLabel(String.valueOf(Consts.ROUND_TIME), SwingConstants.CENTER);
			timePlayer2.setForeground(new Color(30, 144, 255));
			timePlayer2.setFont(new Font("Tahoma", Font.PLAIN, 65));
			timePlayer2.setBounds(32, 325, 115, 73);
			getContentPane().add(timePlayer2);
			timePlayer2.setVisible(false);

			timePlayer1 = new JLabel(String.valueOf(Consts.ROUND_TIME), SwingConstants.CENTER);
			timePlayer1.setForeground(new Color(30, 144, 255));
			timePlayer1.setFont(new Font("Tahoma", Font.PLAIN, 65));
			timePlayer1.setBounds(875, 325, 115, 73);
			getContentPane().add(timePlayer1);

			cTimer1 = new JLabel("New label");
			cTimer1.setIcon(new ImageIcon(GameWindow.class.getResource("/images/timer1.png")));
			cTimer1.setBounds(862, 294, 139, 136);
			getContentPane().add(cTimer1);

			cTimer2 = new JLabel("New label");
			cTimer2.setIcon(new ImageIcon(GameWindow.class.getResource("/images/timer1.png")));
			cTimer2.setBounds(20, 294, 139, 136);
			getContentPane().add(cTimer2);
			cTimer2.setVisible(false);
		}

		JButton btnFinish = new JButton("Finish");
		btnFinish.setOpaque(true);
		btnFinish.setForeground(Color.WHITE);
		btnFinish.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnFinish.setContentAreaFilled(false);
		btnFinish.setBackground(Color.BLACK);
		btnFinish.setBounds(890, 653, 129, 23);
		getContentPane().add(btnFinish);
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!game.isPaused()) {
					pullThePlug();
					Start.menu.setContentPane(Start.menu.contentPane);
					Start.menu.setVisible(true);
				}
			}
		});

		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!game.isPaused()) {
					bp.setVisible(true);
					JFrame j = new GameDialog(game, 0);
					j.setVisible(true);
					game.setPaused(!game.isPaused());

				}
			}
		});
		btnPause.setOpaque(true);
		btnPause.setForeground(Color.WHITE);
		btnPause.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnPause.setContentAreaFilled(false);
		btnPause.setBackground(Color.BLACK);
		btnPause.setBounds(890, 611, 129, 23);
		getContentPane().add(btnPause);

		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setText("40");
		lblNewLabel.setBackground(Color.black);
		lblNewLabel.setBounds(-149, 0, 1019, 700);
		lblNewLabel.setIcon(new ImageIcon("assets/images/game.png"));
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(GameWindow.class.getResource("/images/backGame.png")));
		lblNewLabel_1.setBounds(0, 0, 1019, 700);
		getContentPane().add(lblNewLabel_1);
		if (!game.isSingleGame())
			GameLogic.getInstance().startTurn();
	}

	/**
	 * shuts down the game and clears variables
	 */
	public void pullThePlug() {
		SysData.getInstance().clear();
		if (!game.isSingleGame())
			stopTimer();
		game.setIsRunning(false);
		setVisible(false);
	}

	public JLabel getLabel(String type) {
		switch (type) {
		case "score1":
			toReturn = socers1;
			break;
		case "score2":
			toReturn = socers2;
			break;
		case "time1":
			toReturn = timePlayer1;
			break;
		case "time2":
			toReturn = timePlayer2;
			break;
		case "correctAnswer1":
			toReturn = lblCorrectAnswer1;
			break;
		case "circle1":
			toReturn = cTimer1;
			break;
		case "circle2":
			toReturn = cTimer2;
			break;
		}
		return toReturn;
	}

	public void setTimer(JLabel label) {
		if (p2 != null) {
			int delay = 1000;
			int period = 1000;
			timer = new Timer();
			interval = Consts.ROUND_TIME;
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					if (!game.isPaused())
						label.setText(String.valueOf(setInterval()));
					// if (interval == Consts.ROUND_TIME)
					// label.setForeground(new Color(30, 144, 255));
					if (interval == 10)
						label.setForeground(new Color(193, 39, 45));
					if (interval == 0)
						GameLogic.getInstance().switchPlayer(false);
				}
			}, delay, period);
		}
	}

	/**
	 * stops the current timer
	 */
	public void stopTimer() {
		if (isTimerRunning) {
			timer.cancel();
			isTimerRunning = false;
		}
	}

	/**
	 * resets the timer to starting values
	 */
	public void resetTimer() {
		if (p2 != null) {
			if (game.isP1Turn()) {
				timePlayer2.setText(String.valueOf(Consts.ROUND_TIME));
				timePlayer2.setForeground(new Color(30, 144, 255));
				timePlayer2.setVisible(false);
				cTimer2.setVisible(false);
				timePlayer1.setVisible(true);
				cTimer1.setVisible(true);
			} else {
				timePlayer1.setText(String.valueOf(Consts.ROUND_TIME));
				timePlayer1.setForeground(new Color(30, 144, 255));
				timePlayer2.setVisible(true);
				cTimer2.setVisible(true);
				timePlayer1.setVisible(false);
				cTimer1.setVisible(false);
			}
		}
	}

	private int setInterval() {
		if (interval == 1)
			stopTimer();
		return --interval;
	}

	public static GameWindow getGameWindow() {
		return instance;
	}

	public Game currentGame() {
		return game;
	}

	public ArrayList<JLabel> getOneLifes() {
		return playerOneLife;
	}

	public ArrayList<JLabel> getTwoLifes() {
		return playerTwoLife;
	}

	public Player getP1() {
		return p1;
	}

	public void setP1(Player p1) {
		this.p1 = p1;
	}

	public Player getP2() {
		return p2;
	}

	public void setP2(Player p2) {
		this.p2 = p2;
	}

	public JLabel getTimePlayer1() {
		return timePlayer1;
	}

	public JLabel getTimePlayer2() {
		return timePlayer2;
	}

	public boolean isTimerRunning() {
		return isTimerRunning;
	}

	public void setTimerRunning(boolean running) {
		this.isTimerRunning = running;
	}
}
