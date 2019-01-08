package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.AnswerGhost;
import model.BasicCandy;
import model.BasicGhost;
import model.GameObject;
import model.Pacman;
import model.Player;
import model.Question;
import utils.Consts;
import utils.Key;
import view.GameDialog;
import view.GameWindow;

public class GameLogic {

	private Game game;
	private static GameLogic instance;

	private GameLogic() {
	}

	/**
	 * creates the GameLogic instance if it is not already exists
	 * 
	 * @return instance of GameLogic
	 */
	public static GameLogic getInstance() {
		if (instance == null)
			instance = new GameLogic();
		return instance;
	}

	/**
	 * add score to the specific player
	 * 
	 * @param player
	 * @param score
	 */
	public void addScore(Player player, int score, String flag) {
		player.setScore(score);
		int num = player.getCandiesEaten();
		player.setCandiesEaten(++num);
		if (game.isP1Turn()) {
			GameWindow.getGameWindow().getLabel("score1").setText(String.valueOf(player.getScore()));
			if (flag.equals("correct")) {
				showStatusAnswer("p1", flag);
				int c = player.getCorrectAnswer();
				player.setCorrectAnswer(++c);
			} else if (flag.equals("incorrect"))
				showStatusAnswer("p1", flag);
		} else {
			if (flag.equals("correct")) {
				int c = player.getCorrectAnswer();
				player.setCorrectAnswer(++c);
				showStatusAnswer("p2", flag);
			} else if (flag.equals("incorrect"))
				showStatusAnswer("p2", flag);
			GameWindow.getGameWindow().getLabel("score2").setText(String.valueOf(player.getScore()));
		}
	}

	/**
	 * show if the player answered correct / incorrect
	 * 
	 * @param p
	 *            the player
	 * @param c
	 *            status answer
	 */
	public void showStatusAnswer(String p, String c) {
		JLabel l = GameWindow.getGameWindow().getLabel("correctAnswer1");
		if (c.equals("correct"))
			l.setText("<html>Correct<br>Answer!</html>");
		else if (c.equals("incorrect"))
			l.setText("<html>Incorrect<br>Answer!</html>");
		if (p.equals("p1"))
			l.setBounds(866, 224, 153, 59);
		else
			l.setBounds(31, 224, 153, 59);
		l.setVisible(true);
		int delay = 3000;
		new java.util.Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				l.setVisible(false);
				this.cancel();
			}
		}, delay);
	}

	/**
	 * open the question screen after a player collided with a question candy
	 */
	public void showQuestion(Question q) {
		game = GameWindow.getGameWindow().currentGame();
		int num = game.getCurrantPlayer().getAnswersDone();
		game.getCurrantPlayer().setAnswersDone(++num);
		GameWindow.getGameWindow().bp.setVisible(true);
		JFrame j = new GameDialog(game, 1);
		game.setPaused(!game.isPaused());
		((GameDialog) j).setQuestionToShow(q);
		j.setVisible(true);
	}

	/**
	 * resets pacman and the ghosts to their original location.
	 * 
	 * @param pacman
	 * @param ghosts
	 */
	public void resetLocation(Pacman pacman, ArrayList<BasicGhost> ghosts, boolean death) {
		pacman.setX(pacman.getStartingX());
		pacman.setY(pacman.getStartinyY());
		// resets ghosts if player have died or if its the player first turn
		if (death || game.getCurrantPlayer().isFirstTurn())
			for (BasicGhost bg : ghosts) {
				bg.setX(bg.getStartingX());
				bg.setY(bg.getStartinyY());
			}
	}

	/**
	 * removes one point of life from the given player
	 * 
	 * @param player
	 */
	public void removeLife(Player player) {
		if (player.getLifeScore() > 0) {
			// get the labels for the current player
			ArrayList<JLabel> lifes = game.isP1Turn() ? GameWindow.getGameWindow().getOneLifes()
					: GameWindow.getGameWindow().getTwoLifes();
			// remove the labels from the screen
			JLabel temp = lifes.get(lifes.size() - 1);
			temp.setVisible(false);
			lifes.remove(temp);
			// remove player life count by 1
			player.setLifeScore(player.getLifeScore() - 1);
			// save game history for the player
			if (lifes.isEmpty()) {
				game.setPaused(true);
			}
		}
	}

	/**
	 * add an answer ghost to the board.
	 * 
	 * @param ghost
	 */
	public void addAnswerGhost(AnswerGhost ghost) {
		Random rand = new Random();
		// get a random point in the maze
		int x = rand.nextInt(Consts.SIZE);
		int y = rand.nextInt(Consts.SIZE);
		// get pacman location on the maze
		int pacmanX = game.getPacman().getGridX();
		int pacmanY = game.getPacman().getGridY();
		// the loop will run until it finds a place to set the ghost (according to the
		// maze rules)
		while ((Math.abs(x - pacmanX) < 3) || (Math.abs(y - pacmanY) < 3) || game.getMaze().getMazeGrid()[y][x] == 1
				|| game.getMaze().getMazeGrid()[y][x] == 0) {
			x = rand.nextInt(Consts.SIZE);
			y = rand.nextInt(Consts.SIZE);
		}
		// set ghosts x and y location and set it to render
		ghost.setX(x * Consts.GRID_SIZE);
		ghost.setY(y * Consts.GRID_SIZE);
		ghost.setToRender(true);
	}

	/**
	 * remove all answer ghosts from the board
	 * 
	 * @param ghosts
	 */
	public void removeAnswerGhosts(ArrayList<AnswerGhost> ghosts) {
		for (AnswerGhost ghost : ghosts) {
			ghost.setX(0);
			ghost.setY(0);
			ghost.setToRender(false);
		}
		game.getPacman().setQuestionActive(false);
	}

	/**
	 * remove an object from the board
	 * 
	 * @param player
	 * @param obj
	 */
	public void destroyObject(Player player, GameObject obj) {
		// sets the object to false in the player game state (used in multiplayer)
		player.getGameState().getCandyState()[obj.getGridY()][obj.getGridX()] = false;
		obj.setToRender(false);
	}

	/**
	 * sets the map to the last state it was for the player (in multiplayer mode
	 * only)
	 * 
	 * @param player
	 */
	public void repaintMap(Player player) {
		boolean[][] candy = player.getGameState().getCandyState();
		HashMap<BasicGhost, Key> ghosts = player.getGameState().getGhostsState();
		HashMap<Key, BasicCandy> hashCandy = game.getHandler().getCandyMap();
		// get the candy location according to the key from the hashmap, and set it to
		// render
		// according to the data in the candy two dimensional array
		for (Key k : hashCandy.keySet()) {
			GameObject temp = hashCandy.get(k);
			temp.setToRender(candy[k.getY()][k.getX()]);
		}
		// get the ghosts from the hashmap and set its location
		for (BasicGhost b : ghosts.keySet()) {
			b.setX(ghosts.get(b).getX());
			b.setY(ghosts.get(b).getY());
		}
		// sets pacman to its last known location
		game.getPacman().setX(game.getCurrantPlayer().getGameState().getPacmanX());
		game.getPacman().setY(game.getCurrantPlayer().getGameState().getPacmanY());
	}

	/**
	 * start player next turn in multiplayer mode
	 */
	public void startTurn() {
		game.setPaused(true);
		// displays the countdown timer for the next round
		GameWindow.getGameWindow().bp.setVisible(true);
		new GameDialog(game, 4).setVisible(true);
		// starts the round after 3 seconds
		new java.util.Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				if (!game.isSingleGame())
					game.setTimer();
				else {
					String s = "/images/timer2.png";
					// when one player finish the game, set the other player time to correct image.
					GameWindow w = GameWindow.getGameWindow();
					JLabel label = game.isP1Turn() ? w.getLabel("time1") : w.getLabel("time2");
					JLabel clabel = game.isP1Turn() ? w.getLabel("circle1") : w.getLabel("circle2");
					label.setText("");
					label.setVisible(true);
					clabel.setIcon(new ImageIcon(GameWindow.class.getResource(s)));
					clabel.setVisible(true);
					label = game.isP1Turn() ? w.getLabel("time2") : w.getLabel("time1");
					label.setVisible(false);
					clabel = game.isP1Turn() ? w.getLabel("circle2") : w.getLabel("circle1");
					clabel.setVisible(false);
					w.stopTimer();
				}
				game.setPaused(false);
				this.cancel();
			}
		}, 3000);
	}

	/**
	 * the method will run every time pacman dies
	 * @param poisonCandy
	 * @param addScore
	 */
	public void deathSequence(boolean poisonCandy, boolean addScore) {
		// stop the timer after player death (only in multiplayer)
		if (!game.isSingleGame())
			GameWindow.getGameWindow().stopTimer();
		// if pacman is dead, start death animation
		if (game.getPacman().isDead())
			game.getPacman().startDeathAnim(poisonCandy, addScore);
		else { // only after the death animation ends, do this block of code
			// remove life to the current player
			removeLife(game.getCurrantPlayer());
			// reset objects location
			resetLocation(game.getPacman(), game.getGhosts(), true);
			// if its not a single game, switch player
			if (!game.isSingleGame()) {
				// if the player has finished the maze
				if (game.getCurrantPlayer().getCandiesEaten() == game.getMaze().getNumOfCandies())
					allCandiesEaten(game.getCurrantPlayer(), game.getP2() == null ? true : false, true);
				switchPlayer(true);
			}
		}
	}

	/**
	 * switch to the next player turn (in multiplayer mode)
	 */
	public void switchPlayer(boolean fromDeath) {
		// if the players are still alive, and none of them finished all of the candies
		// on the board
		// treat the game as a multiplayer mode, else treat it as a single player
		if (game.getP1().getLifeScore() > 0 && game.getP2().getLifeScore() > 0 && !game.getP1().isFinishCandies()
				&& !game.getP2().isFinishCandies()) {
			game.getCurrantPlayer().getGameState().setPacmanLocation();
			game.getCurrantPlayer().getGameState().setGhostsLocation();
			if (!fromDeath)
				resetLocation(game.getPacman(), game.getGhosts(), false);
			// if there is question still active when the timer runs out,
			// reset the question
			if (game.getPacman().isQuestionActive()) {
				Pacman pacman = game.getPacman();
				int x = game.getPacman().getCurrentQuestion().getGridX();
				int y = game.getPacman().getCurrentQuestion().getGridY();
				game.getCurrantPlayer().getGameState().getCandyState()[y][x] = true;
				removeAnswerGhosts(pacman.getCurrentQuestion().getQuestion().getGhosts());
				pacman.getCurrentQuestion().getQuestion().setAssigned(false);
				game.getCurrantPlayer().setAnswersDone(game.getCurrantPlayer().getAnswersDone() - 1);
			}
			game.setP1Turn(!game.isP1Turn());
			repaintMap(game.getCurrantPlayer());
			startTurn();
		} else {
			game.setP1Turn(!game.isP1Turn());
			repaintMap(game.getCurrantPlayer());
			game.setSingleGame(true);
			startTurn();
		}
	}

	/**
	 * checks if game is over
	 * 
	 * @return true if game is over, false otherwise
	 */
	public void isGameOver() {
		boolean single = (game.getP2() == null) ? true : false;
		// boolean toReturn = false;
		// if single game
		if (single) {
			// if player life's equals to zero
			if (game.getP1().getLifeScore() == 0) {
				displayGameOverScreen(2);
				game.setGameOver(true);
				game.setPaused(true);
				SaveGameHistory(1);
				
			}
			// if player has eaten all candies on the board
			if (game.getMaze().getNumOfCandies() == game.getP1().getCandiesEaten()) {
				allCandiesEaten(game.getP1(), single, false);
				displayGameOverScreen(3);
				game.setGameOver(true);
				game.setPaused(true);
				SaveGameHistory(1);
				
				
			}
		}
		// if multiplayer game
		else {
			// if one of the players finished all the candies and the other player is still
			// in game, switch player
			if (game.getMaze().getNumOfCandies() == game.getP1().getCandiesEaten() && !game.getP1().isFinishCandies())
				allCandiesEaten(game.getP1(), single, false);
			if (game.getMaze().getNumOfCandies() == game.getP2().getCandiesEaten() && !game.getP2().isFinishCandies())
				allCandiesEaten(game.getP2(), single, false);

			// if both players life's equals to zero or
			// if both player has eaten all the candies on the board or
			// if player 1 life's is equal to zero and player 2 has eaten all the candies or
			// if player 2 life's is equal to zero and player 1 has eaten all the candies
			if ((game.getP1().getLifeScore() == 0 && game.getP2().getLifeScore() == 0) 
				   || (game.getP1().isFinishCandies() && game.getP2().isFinishCandies()) ) {
				displayGameOverScreen(5);
				game.setGameOver(true);
				game.setPaused(true);
				SaveGameHistory(3);
			}
			else if ( (game.getP1().getLifeScore() == 0 && game.getP2().isFinishCandies()))
			{
				displayGameOverScreen(5);
				game.setGameOver(true);
				game.setPaused(true);
				SaveGameHistory(3);	
			}
			else if ( (game.getP2().getLifeScore() == 0 && game.getP1().isFinishCandies())) {
			displayGameOverScreen(5);
			game.setGameOver(true);
			game.setPaused(true);
			SaveGameHistory(2);
			}
		}
	}

	/**
	 * Saves history of the game
	 * 
	 * @param i
	 *            is player number to save the history
	 */
	public void SaveGameHistory(int i) {
		if (i == 1) {
			// Player 1 game being saved after player 2
			if (game.getP2() != null && game.getP2().getLifeScore() < 1) {
				Player player = game.getP1();
				String p2gamenumber = String.valueOf(game.getGameNumber() - 1);
				String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(Calendar.getInstance().getTime());
				SysData.getInstance().addHistoryGame(p2gamenumber, player.getUserName(), timeStamp,
						String.valueOf(player.getAnswersDone()), String.valueOf(player.getScore()),
						String.valueOf(player.getCorrectAnswer()));
				//
			} else {
				// Player 1 game being saved first
				Player player = game.getP1();
				String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(Calendar.getInstance().getTime());
				SysData.getInstance().addHistoryGame(String.valueOf(game.getGameNumber()), player.getUserName(),
						timeStamp, String.valueOf(player.getAnswersDone()), String.valueOf(player.getScore()),
						String.valueOf(player.getCorrectAnswer()));
			}
		} else if (i == 2) {
			// Player 2 game being saved first
			if (game.getP1() != null && game.getP1().getLifeScore() > 0) {
				Player player = game.getP2();
				String p2gamenumber = String.valueOf(game.getGameNumber());
				String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(Calendar.getInstance().getTime());
				SysData.getInstance().addHistoryGame(p2gamenumber, player.getUserName(), timeStamp,
						String.valueOf(player.getAnswersDone()), String.valueOf(player.getScore()),
						String.valueOf(player.getCorrectAnswer()));
				Player player1 = game.getP1();
				String p2gamenumber1 = String.valueOf(game.getGameNumber() - 1);
				String timeStamp1 = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(Calendar.getInstance().getTime());
				SysData.getInstance().addHistoryGame(p2gamenumber1, player1.getUserName(), timeStamp1,
						String.valueOf(player1.getAnswersDone()), String.valueOf(player1.getScore()),
						String.valueOf(player1.getCorrectAnswer()));
				
			} else {
				// Player 2 game being saved after player 1
				Player player = game.getP2();
				String p2gamenumber = String.valueOf(game.getGameNumber() - 1);
				String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(Calendar.getInstance().getTime());
				SysData.getInstance().addHistoryGame(p2gamenumber, player.getUserName(), timeStamp,
						String.valueOf(player.getAnswersDone()), String.valueOf(player.getScore()),
						String.valueOf(player.getCorrectAnswer()));
			}
			
			
		}
		
		else if (i==3) {
			Player player = game.getP1();
			String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(Calendar.getInstance().getTime());
			SysData.getInstance().addHistoryGame(String.valueOf(game.getGameNumber()), player.getUserName(),
					timeStamp, String.valueOf(player.getAnswersDone()), String.valueOf(player.getScore()),
					String.valueOf(player.getCorrectAnswer()));	
			Player player2 = game.getP2();
			String p2gamenumber = String.valueOf(game.getGameNumber() - 1);
			String timeStamp2 = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(Calendar.getInstance().getTime());
			SysData.getInstance().addHistoryGame(p2gamenumber, player2.getUserName(), timeStamp2,
					String.valueOf(player2.getAnswersDone()), String.valueOf(player2.getScore()),
					String.valueOf(player2.getCorrectAnswer()));
			
		}
		
		
		
	}

	/**
	 * display the proper screen after game is over
	 * 
	 * @param type
	 */
	public void displayGameOverScreen(int type) {
		GameWindow.getGameWindow().bp.setVisible(true);
		new GameDialog(game, type).setVisible(true);
	}

	/**
	 * add the bouns score to the given player. if the game was a multiplayer game
	 * AND the other player is still playing switch the turn.
	 * 
	 * @param player
	 * @param single
	 */
	private void allCandiesEaten(Player player, boolean single, boolean fromDeath) {
		player.setFinishCandies(true);
		addScore(player, Consts.MAZE_COMPLETION_BOUNS, "");
		if (!single && !fromDeath) {
			if (!game.isSingleGame()) {
				switchPlayer(false);
			}
		}
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
