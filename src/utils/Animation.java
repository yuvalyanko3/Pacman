package utils;

import java.awt.image.BufferedImage;

public class Animation {
	
	private int animationSpeed, index;
	private long lastTime, timer;
	private BufferedImage[] frames;
	
	public Animation(int animationSpeed, BufferedImage[] frames) {
		this.animationSpeed = animationSpeed;
		this.frames = frames;
		index = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
	}
	
	/**
	 * change the animation in specific time intervals
	 */
	public void tick() {
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer > animationSpeed)
		{
			index++;
			timer = 0;
			if(index >= frames.length)
				index = 0;
		}
	}
	
	/**
	 * returns the current frame
	 * @return
	 */
	public BufferedImage getCurrentFrame() {
		return frames[index];
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}	
	
}
