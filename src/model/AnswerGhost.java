package model;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

import controller.Game;
import utils.Animation;
import utils.Consts;
import utils.ObjectId;

public class AnswerGhost extends BasicGhost {
	private Question question;
	private boolean wasEaten;
	private String answer;
	private Animation anim;
	private int dir;

	public AnswerGhost(int x, int y, int speed, ObjectId id, Question question, String answer, Game game) {
		super(x, y, speed, id, game);
		this.question = question;
		this.answer = answer;
		dir = getRandomDirection();
		toRender = false;
	}

	@Override
	public void tick() {
		if (toRender && !game.getPacman().isDead()) {
			anim.tick();
			// if the ghosts have run into a wall, get new direction
			if (!move(dir))
				dir = getRandomDirection();
		}
	}

	@Override
	public void render(Graphics g) {
		if (toRender)
			g.drawImage(anim.getCurrentFrame(), x, y, null);

	}

	/**
	 * checks if the object can move the to the next tile.if the object can move,
	 * update his x and y coordinates else block the move.
	 * 
	 * @return true if the object is still moving, false if the object hits a wall
	 */
	@Override
	public boolean move(int dir) {
		boolean toReturn = true;
		Rectangle rec = getBounds();
		// variables the represents the different vertexes of the object bounding box
		int xRight, xLeft, yUp, yDown;
		// move down
		if (dir == Consts.DOWN) {
			xLeft = rec.x / Consts.GRID_SIZE;
			xRight = (int) ((rec.x + rec.getWidth()) / Consts.GRID_SIZE);
			yDown = (rec.y + rec.height + speed) / Consts.GRID_SIZE;
			if (!checkCollision(xLeft, yDown, dir) && !checkCollision(xRight, yDown, dir))
				y += speed;
			else
				toReturn = false;
		}
		// move left
		else if (dir == Consts.LEFT) {
			xLeft = (rec.x - speed) / Consts.GRID_SIZE;
			yUp = ((rec.y) / Consts.GRID_SIZE);
			yDown = ((rec.y + rec.height) / Consts.GRID_SIZE);
			if (!checkCollision(xLeft, yUp, dir) && !checkCollision(xLeft, yDown, dir))
				x -= speed;
			else
				toReturn = false;
		}
		// move right
		else if (dir == Consts.RIGHT) {
			xRight = (int) ((rec.x + rec.getWidth() + speed) / Consts.GRID_SIZE);
			yUp = ((rec.y) / Consts.GRID_SIZE);
			yDown = ((rec.y + rec.height) / Consts.GRID_SIZE);
			if (!checkCollision(xRight, yUp, dir) && !checkCollision(xRight, yDown, dir))
				x += speed;
			else
				toReturn = false;
		}
		// move up
		else if (dir == Consts.UP) {
			xLeft = (int) ((rec.x) / Consts.GRID_SIZE);
			xRight = (int) ((rec.x + rec.width) / Consts.GRID_SIZE);
			yUp = (int) ((rec.y - speed) / Consts.GRID_SIZE);
			if (!checkCollision(xLeft, yUp, dir) && !checkCollision(xRight, yUp, dir))
				y -= speed;
			toReturn = false;
		}
		return toReturn;
	}

	@Override
	public void initAnimation() {
		int i = 0;
		BufferedImage[] toAdd = new BufferedImage[2];
		toAdd[0] = textures[i++];
		toAdd[1] = textures[i++];
		// create a new animation with the proper textures
		anim = new Animation(500, Arrays.copyOf(toAdd, toAdd.length));
	}

	/**
	 * return a random direction for the ghost movement
	 * 
	 * @return return an int that represents the direction. 2 for down, 4 for left,
	 *         6 for right, 8 for up
	 * 
	 */
	private int getRandomDirection() {
		Random rand = new Random();
		return (rand.nextInt(4) + 1) * 2;
	}

	/**
	 * checks the user answer
	 * 
	 * @param answer
	 * @return true if correct, false otherwise
	 */
	public boolean isCorrect(String answer) {
		return question.isCorrect(answer);
	}

	public Question getQuestion() {
		return question;
	}

	public String getQuestionString() {
		return question.getQuestionString();
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public boolean getWasEaten() {
		return wasEaten;
	}

	public void setWasEaten(boolean wasEaten) {
		this.wasEaten = wasEaten;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
