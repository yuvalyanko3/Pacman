package model;

import java.util.HashMap;
import java.util.Iterator;

import controller.Game;
import utils.Consts;
import utils.Key;
import utils.ObjectId;

public class GameState {
	//represents the candies on the board
	private boolean[][] candyState;
	//saves the ghost last location
	private HashMap<BasicGhost, Key> ghostsState;
	//saves pacman x coordinates
	private int pacmanX;
	//saves pacman y coordinates
	private int pacmanY;
	//current game
	private Game game;

	public GameState(Game game) {
		candyState = new boolean[Consts.SIZE][Consts.SIZE];
		ghostsState = new HashMap<>();
		this.game = game;
		pacmanX = game.getPacman().getStartingX();
		pacmanY = game.getPacman().getStartinyY();
	}

	/**
	 * initializes the array to be true if there is a candy in this location on the
	 * board, false otherwise
	 */
	public void init() {
		for (int i = 0; i < Consts.SIZE; i++)
			for (int j = 0; j < Consts.SIZE; j++) {
				int temp = game.getMaze().getMazeGrid()[i][j];
				if (temp == Consts.BASIC_CANDY || temp == Consts.SILVER_CANDY || temp == Consts.QUESTION_CANDY
						|| temp == Consts.POISON_CANDY)
					candyState[i][j] = true;
				else
					candyState[i][j] = false;
			}
	}

	/**
	 * saves the ghosts last location
	 */
	public void setGhostsLocation() {
		Iterator<BasicGhost> iterator = game.getGhosts().iterator();
		while (iterator.hasNext()) {
			BasicGhost temp = iterator.next();
			if (temp.getId() == ObjectId.SmartGhost)
				ghostsState.put((SmartGhost) temp, new Key(temp.getX(), temp.getY()));
			else if (temp.getId() == ObjectId.BasicGhost)
				ghostsState.put((BasicGhost) temp, new Key(temp.getX(), temp.getY()));
		}
	}

	/**
	 * saves pacman last location
	 */
	public void setPacmanLocation() {
		pacmanX = game.getPacman().getX();
		pacmanY = game.getPacman().getY();
	}

	public boolean[][] getCandyState() {
		return candyState;
	}

	public HashMap<BasicGhost, Key> getGhostsState() {
		return ghostsState;
	}

	public int getPacmanX() {
		return pacmanX;
	}

	public int getPacmanY() {
		return pacmanY;
	}

}
