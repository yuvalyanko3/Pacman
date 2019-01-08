package model;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import controller.Game;
import utils.Animation;
import utils.Consts;
import utils.IRunnable;
import utils.ObjectId;

public abstract class GameObject implements IRunnable {
	// object location
	protected int x, y;
	// type of object
	protected ObjectId id;
	// object movement speed
	protected int speed;
	// current game
	protected Game game;
	// object textures
	protected BufferedImage[] textures;
	// object animation
	protected Animation animDown, animUp, animLeft, animRight;
	// object current animation frame
	protected BufferedImage currentFrame;
	// object starting position (x axis)
	protected int startingX;
	// object starting position (y axis)
	protected int startinyY;
	protected boolean toRender;

	public GameObject(int x, int y, ObjectId id, Game game) {
		this.x = x;
		this.y = y;
		this.startingX = x;
		this.startinyY = y;
		this.id = id;
		this.game = game;
		toRender = true;
	}

	/**
	 * checks if the object can move the to the next tile. if the object can move,
	 * update his x and y coordinates else block the move.
	 */
	@Override
	public boolean move(int dir) {
		boolean toReturn = true;
		Rectangle rec = getBounds();
		// variables the represents the different vertexes of the object bounding box
		int xRight, xLeft, yUp, yDown;
		if (dir == Consts.DOWN) {
			// get the relevant vertex location for the check
			xLeft = rec.x / Consts.GRID_SIZE;
			xRight = (int) ((rec.x + rec.getWidth()) / Consts.GRID_SIZE);
			yDown = (rec.y + rec.height + speed) / Consts.GRID_SIZE;
			// check if the next step is allowed
			if (!checkCollision(xLeft, yDown, dir) && !checkCollision(xRight, yDown, dir)) {
				currentFrame = animDown.getCurrentFrame();
				y += speed;
			} else
				toReturn = false;
		} else if (dir == Consts.LEFT) {
			// get the relevant vertex location for the check
			xLeft = (rec.x - speed) / Consts.GRID_SIZE;
			yUp = ((rec.y) / Consts.GRID_SIZE);
			yDown = ((rec.y + rec.height) / Consts.GRID_SIZE);
			// check if the next step is allowed
			if (!checkCollision(xLeft, yUp, dir) && !checkCollision(xLeft, yDown, dir)) {
				currentFrame = animLeft.getCurrentFrame();
				x -= speed;
			} else
				toReturn = false;
		} else if (dir == Consts.RIGHT) {
			// get the relevant vertex location for the check
			xRight = (int) ((rec.x + rec.getWidth() + speed) / Consts.GRID_SIZE);
			yUp = ((rec.y) / Consts.GRID_SIZE);
			yDown = ((rec.y + rec.height) / Consts.GRID_SIZE);
			// check if the next step is allowed
			if (!checkCollision(xRight, yUp, dir) && !checkCollision(xRight, yDown, dir)) {
				currentFrame = animRight.getCurrentFrame();
				x += speed;
			} else
				toReturn = false;
		} else if (dir == Consts.UP) {
			// get the relevant vertex location for the check
			xLeft = (int) ((rec.x) / Consts.GRID_SIZE);
			xRight = (int) ((rec.x + rec.width) / Consts.GRID_SIZE);
			yUp = (int) ((rec.y - speed) / Consts.GRID_SIZE);
			// check if the next step is allowed
			if (!checkCollision(xLeft, yUp, dir) && !checkCollision(xRight, yUp, dir)) {
				currentFrame = animUp.getCurrentFrame();
				y -= speed;
			} else
				toReturn = false;
		}
		return toReturn;
	}

	/**
	 * checks if the object next move will be a wall
	 * @param x
	 * @param y
	 * @param dir
	 * @return true if the next step is a wall, false otherwise
	 */
	protected boolean checkCollision(int x, int y, int dir) {
		try {
			return game.getMaze().getMazeGrid()[y][x] == 1;
		} catch (Exception e) {
			if (this.id == ObjectId.Pacman) {
				if (dir == Consts.LEFT)// if pacman get out of bounds (via the open roads at the sides, change its
										// location)
				{
					setX(650);
					setY(288);
				} else {
					if (x < 22)
						return false;
					setX(-25);
					setY(288);
				}
			}
		}
		return true;
	}
	
	/**
	 * initialize animation textures
	 */
	public void initAnimation() {
		int i = 0;
		BufferedImage[] toAdd = new BufferedImage[2];
		toAdd[0] = textures[i++];
		toAdd[1] = textures[i++];
		animDown = new Animation(500, Arrays.copyOf(toAdd, toAdd.length));
		toAdd = new BufferedImage[2];
		toAdd[0] = textures[i++];
		toAdd[1] = textures[i++];
		animUp = new Animation(500, Arrays.copyOf(toAdd, toAdd.length));
		toAdd = new BufferedImage[2];
		toAdd[0] = textures[i++];
		toAdd[1] = textures[i++];
		animLeft = new Animation(500, Arrays.copyOf(toAdd, toAdd.length));
		toAdd = new BufferedImage[2];
		toAdd[0] = textures[i++];
		toAdd[1] = textures[i++];
		animRight = new Animation(500, Arrays.copyOf(toAdd, toAdd.length));
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getStartingX() {
		return startingX;
	}

	public int getStartinyY() {
		return startinyY;
	}

	public int getGridX() {
		return (int) x / Consts.GRID_SIZE;
	}

	public int getGridY() {
		return (int) y / Consts.GRID_SIZE;
	}

	/**
	 * updates the object in fixed time intervals
	 */
	public abstract void tick();

	/**
	 * repaint the object to the screen in fixed time intervals
	 * 
	 * @param g
	 */
	public abstract void render(Graphics g);

	public boolean isToRender() {
		return toRender;
	}

	public void setToRender(boolean toRender) {
		this.toRender = toRender;
	}

}
