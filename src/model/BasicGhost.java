package model;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

import controller.Game;
import utils.Animation;
import utils.ObjectId;

public class BasicGhost extends GameObject {
	
	private int dir;
	
	public BasicGhost(int x, int y, int speed, ObjectId id, Game game) {
		super(x, y, id, game);
		setSpeed(speed);
		dir = getRandomDirection();
	}

	@Override
	public void tick() {
		if(!game.getPacman().isDead())
		{
			collision();
			animDown.tick();
			animUp.tick();
			animLeft.tick();
			animRight.tick();
			//if the ghosts have run into a wall, get new direction
			if(!move(dir))
				dir = getRandomDirection();
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(currentFrame, x, y, null);
	}
	

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x+2, y+1, 28, 30);
	}

	@Override
	public void collision() {
	}
	
	/**
	 * get a random number that represents a specific direction (2, 4, 6, 8)
	 * @return
	 */
	private int getRandomDirection() {
		Random rand = new Random();
		return (rand.nextInt(4) + 1)  * 2;
	}
	
	public BufferedImage[] getTextures() {
		return textures;
	}
	
	public void setTextures(BufferedImage[] t) {
		this.textures = t;
	}
	
	public void setStartingAnimation() {
		this.currentFrame = animLeft.getCurrentFrame();
	}
}
