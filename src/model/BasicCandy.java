package model;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.Game;
import model.GameObject;
import utils.Consts;
import utils.ObjectId;

public class BasicCandy extends GameObject {

	private boolean wasEaten;
	private int candyScore;
	private BufferedImage candy;
	private int boundsX;
	private int boundsY;
	private int boundsWidth;
	private int boundsHeight;

	public BasicCandy(int x, int y, ObjectId id, int candyScore, Game game) {
		super(x, y, id, game);
		this.candyScore = candyScore;
		initImage();
		setBoundOffest();
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		if (toRender)
			g.drawImage(candy, x, y, null);
	}

	@Override
	public Rectangle getBounds() {
		Rectangle toReturn = null;
		if (toRender)
			toReturn = new Rectangle(x + boundsX, y + boundsY, boundsWidth, boundsHeight);
		return toReturn;
	}

	private void initImage() {
		try {
			candy = ImageIO
					.read(new File("assets/images/" + game.typeGame + "/candies/" + this.id.toString() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * sets the size of the bounding box for each type of the candy objects
	 */
	private void setBoundOffest() {
		switch (id) {
		case BasicCandy:
			boundsX = Consts.BASIC_CANDY_X;
			boundsY = Consts.BASIC_CANDY_Y;
			boundsWidth = Consts.BASIC_CANDY_WIDTH;
			boundsHeight = Consts.BASIC_CANDY_HEIGHT;
			break;
		case SilverCandy:
			boundsX = Consts.SILVER_CANDY_X;
			boundsY = Consts.SILVER_CANDY_Y;
			boundsWidth = Consts.SILVER_CANDY_WIDTH;
			boundsHeight = Consts.SILVER_CANDY_HEIGHT;
			break;
		case QuestionCandy:
			boundsX = Consts.QUESTION_CANDY_X;
			boundsY = Consts.QUESTION_CANDY_Y;
			boundsWidth = Consts.QUESTION_CANDY_WIDTH;
			boundsHeight = Consts.QUESTION_CANDY_HEIGHT;
			break;
		case PoisonCandy:
			boundsX = Consts.POSION_CANDY_X;
			boundsY = Consts.POSION_CANDY_Y;
			boundsWidth = Consts.POSION_CANDY_WIDTH;
			boundsHeight = Consts.POSION_CANDY_HEIGHT;
			break;
		default:
		}
	}

	@Override
	public void collision() {

	}

	public boolean isWasEaten() {
		return wasEaten;
	}

	public void setWasEaten(boolean wasEaten) {
		this.wasEaten = wasEaten;
	}

	public int getCandyScore() {
		return candyScore;
	}

	public void setCandyScore(int score) {
		this.candyScore = score;
	}

	@Override
	public void initAnimation() {		
	}
}
