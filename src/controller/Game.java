package controller;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import model.AnswerGhost;
import model.BasicGhost;
import model.Maze;
import model.Pacman;
import model.Player;
import utils.KeyInput;
import utils.SpriteInit;
import view.GameWindow;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {

	public static final int WIDTH = 672;
	public static final int HEIGHT = 672;

	// the thread for the game
	private Thread thread;
	// the handler for the game. updates all the objects.
	private Handler handler;
	// the current maze for the game
	private Maze maze;
	// gets the input from the user
	private KeyInput input;
	// to check if the game loop is running
	private boolean isRunning;
	// if the game is paused, the methods 'tick' and 'render' will not be executed.
	private boolean isPaused;
	// what type of game (normal, space, jungle)
	public String typeGame;
	private static int gameNumber;
	private Pacman pacman;
	// saves all the ghosts (except for answer ghosts)
	private ArrayList<BasicGhost> ghosts;
	// saves answer ghosts that are currently on the screen
	private ArrayList<AnswerGhost> answerGhosts;
	private Player p1;
	private Player p2;
	// used to know if its a single or a double game
	private boolean singleGame;
	// true if its player one turn
	private boolean p1Turn;
	// true if the game is over
	private boolean isGameOver;

	public Game(String type, Player p1, Player p2) {
		setGameNumber();
		// sets the type of game selected (noraml, space, jungle)
		typeGame = type;
		this.p1 = p1;
		this.p2 = p2;
		p1Turn = true;
		// if player 2 is null set single game to true
		this.singleGame = (this.p2 == null) ? true : false;
		isGameOver = false;
		init();
	}

	private void init() {
		isRunning = false;
		isPaused = false;
		// init new handler
		handler = new Handler();
		input = new KeyInput();
		// add the key listener we created to this game
		this.addKeyListener(input);
		// create the proper maze according to the type of game
		maze = new Maze("maze_" + typeGame, typeGame, this);
		this.p1.initGame(this);
		if (!singleGame) {
			this.p2.initGame(this);
		}
		new SpriteInit(this, typeGame);
		GameLogic.getInstance().setGame(this);
	}

	/**
	 * this method contains the game loop
	 */
	@SuppressWarnings("unused")
	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 64;

		int ticks = 0;
		int frames = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		// while game is running keep updating the game
		while (isRunning) {

			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while (delta >= 1) {

				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				if (!isPaused)
					render();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				// System.out.println(ticks + " ticks, " + frames + " frames");
				frames = 0;
				ticks = 0;
			}
			if (isPaused)
				this.requestFocus();
		}
		stop();
	}

	/**
	 * updates the objects in fixed time intervals
	 */
	private void tick() {
		input.tick();
		if(!isPaused) {
			handler.tick();
			if (!isGameOver && !getPacman().isDead())
				GameLogic.getInstance().isGameOver();	
		}
	}

	/**
	 * repaint the objects to the screen in fixed time intervals
	 * 
	 * @param g
	 */
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		maze.render(g);
		handler.render(g);
		g.dispose();
		bs.show();
	}

	/**
	 * starts the thread
	 */
	public synchronized void start() {
		if (!isRunning) {
			thread = new Thread(this);
			thread.start();
			isRunning = true;
		}
	}

	/**
	 * stops the thread
	 */
	public synchronized void stop() {
		try {
			thread.join();
			isRunning = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * set the timer for the current player
	 */
	public void setTimer() {
		GameWindow w = GameWindow.getGameWindow();
		// it timer isnt running yet, set timer running to true, else stop the timer
		if (!w.isTimerRunning())
			w.setTimerRunning(true);
		else
			w.stopTimer();
		// set timer for player one, else set timer for player two
		if (p1Turn)
			w.setTimer(w.getLabel("time1"));
		else
			w.setTimer(w.getLabel("time2"));
		// if timer was already running, reset timer
		if (w.isTimerRunning())
			w.resetTimer();
	}

	public Handler getHandler() {
		return handler;
	}

	public KeyInput getInput() {
		return input;
	}

	public Maze getMaze() {
		return maze;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean paused) {
		isPaused = paused;
	}

	public void setIsRunning(boolean b) {
		this.isRunning = b;
	}

	public Pacman getPacman() {
		return pacman;
	}

	public void setPacman(Pacman pacman) {
		this.pacman = pacman;
	}

	public ArrayList<BasicGhost> getGhosts() {
		return ghosts;
	}

	public void setGhosts(ArrayList<BasicGhost> ghosts) {
		this.ghosts = ghosts;
	}

	public Player getCurrantPlayer() {
		return p1Turn ? p1 : p2;
	}

	public boolean isP1Turn() {
		return p1Turn;
	}

	public void setP1Turn(boolean p1Turn) {
		this.p1Turn = p1Turn;
	}

	public boolean isSingleGame() {
		return singleGame;
	}

	public void setSingleGame(boolean singleGame) {
		this.singleGame = singleGame;
	}

	public Player getP1() {
		return p1;
	}

	public Player getP2() {
		return p2;
	}

	public int getGameNumber() {
		// getting latest game number from json
		String lastgameNumber = SysData.getInstance().maxGameNumber();
		gameNumber = Integer.parseInt(lastgameNumber) + 1;
		return gameNumber;
	}

	@SuppressWarnings("static-access")
	public void setGameNumber() {
		this.gameNumber++;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public ArrayList<AnswerGhost> getAnswerGhosts() {
		return answerGhosts;
	}

	public void setAnswerGhosts(ArrayList<AnswerGhost> answerGhosts) {
		this.answerGhosts = answerGhosts;
	}

}
