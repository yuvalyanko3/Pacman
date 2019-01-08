package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import javax.imageio.ImageIO;

import controller.Game;
import controller.SysData;
import model.AnswerGhost;
import model.BasicGhost;
import model.GameObject;
import model.Pacman;
import model.Question;
import model.SmartGhost;

public class SpriteInit {

	private Game game;
	private String type;
	private BufferedImage[] pacman;
	private BufferedImage[] deadpacman;
	private BufferedImage[] smartGhost;
	private BufferedImage[] answerGhost;
	private BufferedImage[] regualrGhostOne;
	private BufferedImage[] regualrGhostTwo;

	public SpriteInit(Game game, String typeGame) {
		this.game = game;
		this.type = typeGame;
		pacman = new BufferedImage[8];
		deadpacman = new BufferedImage[12];
		smartGhost = new BufferedImage[8];
		answerGhost = new BufferedImage[8];
		regualrGhostOne = new BufferedImage[8];
		regualrGhostTwo = new BufferedImage[8];
		initPacman();
		initeDeadPacman();
		initGhosts();
		initSprites();
	}

	/**
	 * initializes pacman textures
	 */
	private void initPacman() {
		File file = new File("assets/images/" + type + "/pacman/");
		File[] files = file.listFiles();
		int i = 0;
		for (File f : files) {
			try {
				pacman[i++] = ImageIO.read(new File("assets/images/" + type + "/pacman/" + f.getName()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * initializes dead pacman textures
	 */
	private void initeDeadPacman() {
		File file = new File("assets/images/" + type + "/death/");
		File[] files = file.listFiles();
		int i = 0;
		for (File f : files) {
			try {
				deadpacman[i++] = ImageIO.read(new File("assets/images/" + type + "/death/" + f.getName()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * initializes ghosts textures
	 */
	private void initGhosts() {

		String a = "a";
		String b = "b";
		String c = "c";
		String d = "d";

		int i = 0;
		File file = new File("assets/images/" + type + "/ghosts/");
		File[] files = file.listFiles();
		for (File f : files) {
			try {
				if (f.getName().contains(a))
					answerGhost[i++] = ImageIO.read(new File("assets/images/" + type + "/ghosts/" + f.getName()));
				else if (f.getName().contains(b))
					smartGhost[i++] = ImageIO.read(new File("assets/images/" + type + "/ghosts/" + f.getName()));
				else if (f.getName().contains(c))
					regualrGhostOne[i++] = ImageIO.read(new File("assets/images/" + type + "/ghosts/" + f.getName()));
				else if (f.getName().contains(d))
					regualrGhostTwo[i++] = ImageIO.read(new File("assets/images/" + type + "/ghosts/" + f.getName()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (i == 8)
				i = 0;
		}
	}

	/**
	 * assigns the right textures to the object
	 */
	private void initSprites() {
		Iterator<GameObject> iterator = game.getHandler().getGameObjects().iterator();
		boolean flag = true;
		while (iterator.hasNext()) {
			GameObject temp = iterator.next();
			if (temp.getId().equals(ObjectId.Pacman)) {
				((Pacman) temp).setTextures(pacman);
				((Pacman) temp).initAnimation();
				((Pacman) temp).setStartingAnimation();
				// ((Pacman)temp).setTextures(deadpacman);
				((Pacman) temp).setDeadTextures(deadpacman);
				((Pacman) temp).deathAnimation();
			} else if (temp.getId().equals(ObjectId.BasicGhost)) {
				if (flag)
					((BasicGhost) temp).setTextures(regualrGhostOne);
				else
					((BasicGhost) temp).setTextures(regualrGhostTwo);
				((BasicGhost) temp).initAnimation();
				((BasicGhost) temp).setStartingAnimation();
				flag = !flag;
			} else if (temp.getId().equals(ObjectId.SmartGhost)) {
				((SmartGhost) temp).setTextures(smartGhost);
				((SmartGhost) temp).initAnimation();
				((SmartGhost) temp).setStartingAnimation();
			} else if (temp.getId().equals(ObjectId.AnswerGhost)) {
				((AnswerGhost) temp).setTextures(answerGhost);
				((AnswerGhost) temp).initAnimation();
				((AnswerGhost) temp).setStartingAnimation();
			}

		}

		for (Question q : SysData.getInstance().getGameQuestions()) {
			BufferedImage[] img = new BufferedImage[2];
			for (AnswerGhost g : q.getGhosts()) {
				if (g.getAnswer().equals("1")) {
					img[0] = answerGhost[0];
					img[1] = answerGhost[1];
				} else if (g.getAnswer().equals("2")) {
					img[0] = answerGhost[2];
					img[1] = answerGhost[3];
				} else if (g.getAnswer().equals("3")) {
					img[0] = answerGhost[4];
					img[1] = answerGhost[5];
				} else if (g.getAnswer().equals("4")) {
					img[0] = answerGhost[6];
					img[1] = answerGhost[7];
				}
				g.setTextures(Arrays.copyOf(img, img.length));
				g.initAnimation();
			}
		}
	}

}
