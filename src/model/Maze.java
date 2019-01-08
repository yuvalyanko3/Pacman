package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import controller.Game;
import controller.Handler;
import controller.ObjectFactory;
import controller.SysData;
import utils.Consts;
import utils.Key;
import utils.ObjectId;

public class Maze {
	//////////////////////////////////// CONSTS//////////////////////////////
	private static final int ROWS = 21;
	private static final int COLS = 21;
	
	//saves the maze build
	protected int[][] mazeGrid;
	private Game game;
	//path to the maze text file
	private String txtPath;
	//path to the maze background image
	private String imgPath;
	//number of candies in the maze
	private int numOfCandies;
	private PosionCandy posionCandy;

	public Maze(String mazeName, String type, Game game) {
		txtPath = getTxtLocation(mazeName);
		imgPath = getImgLocation(mazeName);
		this.game = game;
		mazeGrid = initMaze(txtPath);
		setNumOfCandies(type);
		initObjects();
		// initAnswerGhosts();
	}

	/**
	 * reads the maze data from a text file into a two dimensional array
	 * 
	 * @param path
	 * @return a two dimensional array (int)
	 */
	private int[][] initMaze(String path) {
		int[][] toReturn = new int[ROWS][COLS];
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			int i = 0;
			while (line != null) {
				int j = 0;
				for (char c : line.toCharArray()) {
					toReturn[i][j] = Character.getNumericValue(c);
					j++;
				}
				i++;
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	/**
	 * initialize the game objects according the the numbers that was assigned to
	 * them
	 */
	private void initObjects() {
		Handler handler = game.getHandler();
		ObjectFactory of = new ObjectFactory();
		ArrayList<BasicGhost> ghosts = new ArrayList<>();
		// add all candies to the game
		for (int y = 0; y < ROWS; y++) {
			for (int x = 0; x < COLS; x++) {
				if (mazeGrid[y][x] == Consts.BASIC_CANDY) {
					BasicCandy bc = (BasicCandy) of.createObject(ObjectId.BasicCandy, x * Consts.GRID_SIZE,
							y * Consts.GRID_SIZE, game);
					handler.addGameObject(bc);
					handler.addMapCandy(new Key(bc.getGridX(), bc.getGridY()), bc);
				}
				if (mazeGrid[y][x] == Consts.SILVER_CANDY) {
					BasicCandy bc = (BasicCandy) of.createObject(ObjectId.SilverCandy, x * Consts.GRID_SIZE,
							y * Consts.GRID_SIZE, game);
					handler.addGameObject(bc);
					handler.addMapCandy(new Key(bc.getGridX(), bc.getGridY()), bc);
				} else if (mazeGrid[y][x] == Consts.QUESTION_CANDY) {
					QuestionCandy qc = (QuestionCandy) of.createObject(ObjectId.QuestionCandy, x * Consts.GRID_SIZE,
							y * Consts.GRID_SIZE, game);
					handler.addGameObject(qc);
					handler.addMapCandy(new Key(qc.getGridX(), qc.getGridY()), qc);
				} else if (mazeGrid[y][x] == Consts.POISON_CANDY) {
					PosionCandy pc = (PosionCandy) of.createObject(ObjectId.PoisonCandy, x * Consts.GRID_SIZE,
							y * Consts.GRID_SIZE, game);
					handler.addGameObject(pc);
					handler.addMapCandy(new Key(pc.getGridX(), pc.getGridY()), pc);
					this.posionCandy = pc;
				}
			}
		}
		// add pacman and the ghosts to the game
		for (int y = 0; y < ROWS; y++) {
			for (int x = 0; x < COLS; x++) {
				if (mazeGrid[y][x] == Consts.PACMAN) {
					Pacman pac = (Pacman) of.createObject(ObjectId.Pacman, x * Consts.GRID_SIZE, y * Consts.GRID_SIZE,
							game);
					handler.addGameObject(pac);
					game.setPacman(pac);

				} else if (mazeGrid[y][x] == Consts.BASIC_GHOST) {
					BasicGhost bg = (BasicGhost) of.createObject(ObjectId.BasicGhost, x * Consts.GRID_SIZE,
							y * Consts.GRID_SIZE, game);
					handler.addGameObject(bg);
					ghosts.add(bg);
				} else if (mazeGrid[y][x] == Consts.SMART_GHOST) {
					SmartGhost sg = (SmartGhost) of.createObject(ObjectId.SmartGhost, x * Consts.GRID_SIZE,
							y * Consts.GRID_SIZE, game);
					handler.addGameObject(sg);
					ghosts.add((SmartGhost) sg);
				}
			}
		}
		// add the relevant answer ghosts to the game
		ArrayList<AnswerGhost> answerGhosts = new ArrayList<>();
		for (Question gq : SysData.getInstance().getGameQuestions()) {
			for (int i = 0; i < gq.getAnswer().getAnswers().size(); i++) {
				AnswerGhost ag = (AnswerGhost) of.createObject(ObjectId.AnswerGhost, 0, 0, game);
				// sets the question the this specific ghost
				ag.setQuestion(gq);
				// sets the answer number the this specific ghost
				ag.setAnswer(String.valueOf(i + 1));
				answerGhosts.add(ag);
				handler.getAnswerGhosts().add(ag);
			}
			gq.setGhosts(new ArrayList<>(answerGhosts));
			answerGhosts.clear();
		}
		game.setGhosts(ghosts);
	}

	/**
	 * return the proper text file location
	 * 
	 * @param mazeName
	 * @return
	 */
	private String getTxtLocation(String mazeName) {
		return new String("assets/mazes/maze-txt/" + mazeName + ".txt");
	}

	/**
	 * returns the proper background image file location
	 * 
	 * @param mazeName
	 * @return
	 */
	private String getImgLocation(String mazeName) {
		return new String("assets/mazes/maze-img/" + mazeName + ".png");
	}

	public void tick() {
	}

	public void render(Graphics g) {
		g.drawImage(getBackground(), 0, 0, null);
	}

	private BufferedImage getBackground() {
		BufferedImage toReturn = null;
		try {
			toReturn = ImageIO.read(new File(imgPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	public int[][] getMazeGrid() {
		return mazeGrid;
	}

	public int getNumOfCandies() {
		return numOfCandies;
	}

	public void setNumOfCandies(String type) {
		if (type == "jungle")
			this.numOfCandies = Consts.JUNGLE_CANDIES;
		else if (type == "normal")
			this.numOfCandies = Consts.NORMAL_CANDIES;
		else if (type == "space")
			this.numOfCandies = Consts.SPACE_CANDIES;
	}

	public PosionCandy getPosionCandy() {
		return posionCandy;
	}

}
