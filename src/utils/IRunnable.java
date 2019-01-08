package utils;

import java.awt.Rectangle;

public interface IRunnable {
	/**
	 * creates a rectangle around the object
	 * @return Rectangle
	 */
	public Rectangle getBounds();
	/**
	 * checks if there was a collision between two objects,
	 * and define what steps should be taken next
	 */
	public void collision();
	/**
	 * define how the object should move
	 */
	public boolean move(int dir);
}
